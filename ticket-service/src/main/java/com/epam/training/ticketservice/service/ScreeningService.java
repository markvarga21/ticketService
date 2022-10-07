package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.ScreeningDto;
import com.epam.training.ticketservice.entity.Screening;
import com.epam.training.ticketservice.mapping.ScreeningMapper;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.util.CompositeKey;
import com.epam.training.ticketservice.util.ScreeningDateTimeConverter;
import com.epam.training.ticketservice.util.ScreeningValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final ScreeningMapper screeningMapper;
    private final ScreeningRepository screeningRepository;
    private final ScreeningValidator screeningValidator;
    private final ScreeningDateTimeConverter converter;

    @Transactional
    public String saveScreening(ScreeningDto screeningToSave) {
        if (!this.screeningValidator.isValidReservation(screeningToSave)) {
            return "Screening invalid, because film or room does not exist!";
        }

        String movieName = screeningToSave.getMovieName();
        String roomName = screeningToSave.getRoomName();
        String timeOfScreening = screeningToSave.getTimeOfScreening();

        var screeningsForRoom = this.getScreeningsForRoom(roomName);
        if (!screeningsForRoom.isEmpty()
                && !this.screeningValidator.isValidScreenDateTime(movieName, timeOfScreening, screeningsForRoom)) {
            return "There is an overlapping screening";
        }

        if (!screeningsForRoom.isEmpty()
                && !this.screeningValidator.isPausePresent(movieName, timeOfScreening, screeningsForRoom)) {
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

    public List<ScreeningDto> getScreenings() {
        List<ScreeningDto> screenings = new ArrayList<>(this.screeningRepository
                .findAll()
                .stream()
                .map(this.screeningMapper::mapScreeningToDto)
                .toList());
        Collections.reverse(screenings);
        return screenings;
    }

    @Transactional
    public void deleteScreening(String movieName, String roomName, String timeOfScreening) {
        LocalDateTime timeOfScreeningDate = this.converter.convertScreeningTimeString(timeOfScreening);
        CompositeKey compositeKey = new CompositeKey(movieName, roomName, timeOfScreeningDate);
        this.screeningRepository.deleteById(compositeKey);
    }

    public List<ScreeningDto> getScreeningsForRoom(String roomName) {
        return this.getScreenings()
                .stream()
                .filter(screeningDTO -> screeningDTO.getRoomName().equals(roomName))
                .toList();
    }
}
