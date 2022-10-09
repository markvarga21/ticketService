package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.RoomDto;
import com.epam.training.ticketservice.entity.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

class RoomMapperTest {
    private RoomMapper underTest;

    @BeforeEach
    public void init() {
        this.underTest = new RoomMapper(new ModelMapper());
    }

    @Test
    void testMapRoomToDto() {
        // Given
        String roomName = "bigRoom";
        Long chairCols = 5L;
        Long chairRows = 5L;
        Room room = new Room(roomName, chairRows, chairCols);
        RoomDto expected = new RoomDto(roomName, chairRows, chairCols);

        // When
        RoomDto actual = this.underTest.mapRoomToDto(room);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testMapRoomDtoToEntity() {
        // Given
        String roomName = "bigRoom";
        Long chairCols = 5L;
        Long chairRows = 5L;
        Room expected = new Room(roomName, chairRows, chairCols);
        RoomDto roomDto = new RoomDto(roomName, chairRows, chairCols);

        // When
        Room actual = this.underTest.mapRoomDtoToEntity(roomDto);

        // Then
        assertEquals(expected, actual);
    }
}