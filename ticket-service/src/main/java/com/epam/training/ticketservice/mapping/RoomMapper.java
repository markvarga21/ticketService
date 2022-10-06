package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.RoomDto;
import com.epam.training.ticketservice.entity.Room;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomMapper {
    private final ModelMapper modelMapper;

    public RoomDto mapRoomToDto(Room room) {
        return this.modelMapper.map(room, RoomDto.class);
    }

    public Room mapRoomDtoToEntity(RoomDto roomDto) {
        return this.modelMapper.map(roomDto, Room.class);
    }
}
