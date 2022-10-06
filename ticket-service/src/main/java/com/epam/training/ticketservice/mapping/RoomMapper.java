package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.RoomDTO;
import com.epam.training.ticketservice.entity.Room;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomMapper {
    private final ModelMapper modelMapper;

    public RoomDTO mapRoomToDto(Room room) {
        return this.modelMapper.map(room, RoomDTO.class);
    }

    public Room mapRoomDtoToEntity(RoomDTO roomDTO) {
        return this.modelMapper.map(roomDTO, Room.class);
    }
}
