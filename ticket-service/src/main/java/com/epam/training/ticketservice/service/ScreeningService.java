package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.RoomDTO;
import com.epam.training.ticketservice.dto.ScreeningDTO;
import com.epam.training.ticketservice.entity.Room;
import com.epam.training.ticketservice.entity.Screening;
import com.epam.training.ticketservice.mapping.ScreeningMapper;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.util.CompositeKey;
import com.epam.training.ticketservice.util.ScreeningDateTimeConverter;
import com.epam.training.ticketservice.util.ScreeningValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final ScreeningMapper screeningMapper;
    private final ScreeningRepository screeningRepository;
    private final ScreeningValidator screeningValidator;
    private final ScreeningDateTimeConverter converter;

    @Transactional
    public String saveScreening(ScreeningDTO screeningToSave) {
        if (!this.screeningValidator.isValidReservation(screeningToSave)) {
            return "Screening invalid, because film or room does not exist!";
        }

        String movieName = screeningToSave.getMovieName();
        String roomName = screeningToSave.getRoomName();
        String timeOfScreening = screeningToSave.getTimeOfScreening();

        var screeningsForRoom = this.getScreeningsForRoom(roomName);
        if (!screeningsForRoom.isEmpty() &&
                !this.screeningValidator.isValidScreenDateTime(movieName, timeOfScreening, screeningsForRoom)) {
            return "There is an overlapping screening";
        }

        if (!screeningsForRoom.isEmpty() &&
                !this.screeningValidator.isPausePresent(movieName, roomName, timeOfScreening, screeningsForRoom)) {
            return "This would start in the break period after another screening in this room";
        }

        Screening screening = this.screeningMapper.mapScreeningDtoToEntity(screeningToSave);
        this.screeningRepository.save(screening);

        return String.format("Screening '%s' in room '%s' on '%s' saved!",
                movieName,
                roomName,
                timeOfScreening
        );
    }

    public List<ScreeningDTO> getScreenings() {
        return this.screeningRepository
            .findAll()
            .stream()
            .map(this.screeningMapper::mapScreeningToDto)
            .toList();
    }

    @Transactional
    public void deleteScreening(String movieName, String roomName, String timeOfScreening) {
        CompositeKey compositeKey = new CompositeKey(movieName, roomName, this.converter.convertScreeningTimeString(timeOfScreening));
        this.screeningRepository.deleteById(compositeKey);
    }

    public List<ScreeningDTO> getScreeningsForRoom(String roomName) {
        return this.getScreenings()
                .stream()
                .filter(screeningDTO -> screeningDTO.getRoomName().equals(roomName))
                .toList();
    }
}
