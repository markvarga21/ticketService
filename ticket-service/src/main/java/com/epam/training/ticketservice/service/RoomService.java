package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.RoomDto;
import com.epam.training.ticketservice.entity.Room;
import com.epam.training.ticketservice.mapping.RoomMapper;
import com.epam.training.ticketservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Transactional
    public String saveRoom(RoomDto roomToSave) {
        Room roomEntity = this.roomMapper.mapRoomDtoToEntity(roomToSave);
        this.roomRepository.save(roomEntity);
        return String.format("Room '%s' created!", roomToSave.getName());
    }

    public List<RoomDto> getRooms() {
        return this.roomRepository
                .findAll()
                .stream()
                .map(this.roomMapper::mapRoomToDto)
                .toList();
    }

    @Transactional
    public int deleteRoom(String name) {
        return this.roomRepository.deleteByName(name);
    }

    @Transactional
    public String updateRoom(RoomDto roomToUpdate) {
        var roomOptional = this.roomRepository.findById(roomToUpdate.getName());

        if (roomOptional.isEmpty()) {
            return String.format("Cannot update, because room '%s' not found!", roomToUpdate.getName());
        }

        Room roomEntityToUpdate = roomOptional.get();
        roomEntityToUpdate.setChairRowsCount(roomToUpdate.getChairRowsCount());
        roomEntityToUpdate.setChairColumnsCount(roomToUpdate.getChairColumnsCount());
        this.roomRepository.save(roomEntityToUpdate);

        return String.format("Room '%s' updated successfully!", roomToUpdate.getName());
    }

    public boolean roomExists(String roomName) {
        return this.roomRepository.existsById(roomName);
    }
}
