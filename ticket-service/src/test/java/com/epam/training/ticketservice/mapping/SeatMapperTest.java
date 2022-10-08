package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.SeatDto;
import com.epam.training.ticketservice.entity.Seat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeatMapperTest {
    private SeatMapper underTest;

    @BeforeEach
    public void init() {
        this.underTest = new SeatMapper();
    }

    @Test
    public void testConvertSeatDtoToEntity() {
        // Given
        String roomName = "bigRoom";
        Long seatRow = 5L;
        Long seatColumn = 5L;
        SeatDto seatDto = new SeatDto(roomName, seatRow, seatColumn);
        Seat expected = new Seat(roomName, seatRow, seatColumn);

        // When
        Seat actual = this.underTest.convertSeatDtoToEntity(seatDto);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testConvertSeatEntityToDto() {
        // Given
        String roomName = "bigRoom";
        Long seatRow = 5L;
        Long seatColumn = 5L;
        SeatDto expected = new SeatDto(roomName, seatRow, seatColumn);
        Seat seat = new Seat(roomName, seatRow, seatColumn);

        // When
        SeatDto actual = this.underTest.convertSeatEntityToDto(seat);

        // Then
        assertEquals(expected, actual);
    }
}