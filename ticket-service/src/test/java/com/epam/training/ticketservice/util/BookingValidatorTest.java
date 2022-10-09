package com.epam.training.ticketservice.util;

import com.epam.training.ticketservice.dto.BookingDto;
import com.epam.training.ticketservice.dto.RoomDto;
import com.epam.training.ticketservice.dto.ScreeningDto;
import com.epam.training.ticketservice.dto.SeatDto;
import com.epam.training.ticketservice.entity.Room;
import com.epam.training.ticketservice.entity.Screening;
import com.epam.training.ticketservice.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingValidatorTest {
    @InjectMocks
    private BookingValidator underTest;
    @Mock
    private RoomService roomService;

    @Test
    public void testIsValidSeatForRoomShouldReturnFalseWhenInvalidSeats() {
        // Given
        String roomName = "roomName";
        RoomDto roomDto = new RoomDto(roomName, 5L, 5L);
        List<RoomDto> roomDtoList = List.of(roomDto);
        SeatDto seatDto1 = new SeatDto(roomName, 1L, 10L);
        SeatDto seatDto2 = new SeatDto(roomName, 10L, 1L);

        // When
        when(this.roomService.getRooms())
                .thenReturn(roomDtoList);
        boolean actual1 = this.underTest.isValidSeatForRoom(roomName, seatDto1);
        boolean actual2 = this.underTest.isValidSeatForRoom(roomName, seatDto2);

        // Then
        assertAll(
                () -> assertFalse(actual1),
                () -> assertFalse(actual2)
        );
    }

    @Test
    public void testIsValidSeatForRoomShouldReturnTrueWhenValidSeats() {
        // Given
        String roomName = "roomName";
        RoomDto roomDto = new RoomDto(roomName, 5L, 5L);
        List<RoomDto> roomDtoList = List.of(roomDto);
        SeatDto seatDto = new SeatDto(roomName, 1L, 1L);
        boolean expected = true;

        // When
        when(this.roomService.getRooms())
                .thenReturn(roomDtoList);
        boolean actual = this.underTest.isValidSeatForRoom(roomName, seatDto);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testIsSeatFreeShouldReturnFalseWhenOneOfTheSeatsIsOccupied() {
        // Given
        String roomName = "room1";
        String userName = "john";
        SeatDto seatDto1 = new SeatDto(roomName, 1L, 1L);
        SeatDto seatDto2 = new SeatDto(roomName, 2L, 2L);
        ScreeningDto screening = new ScreeningDto();
        BookingDto bookingDto1 = new BookingDto(userName, screening, seatDto1);
        BookingDto bookingDto2 = new BookingDto(userName, screening, seatDto2);
        List<BookingDto> bookings = List.of(bookingDto1, bookingDto2);
        SeatDto seatToBook = new SeatDto(roomName, 1L, 1L);
        boolean expected = false;

        // When
        boolean actual = this.underTest.isSeatFree(bookings, seatToBook);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testIsSeatFreeShouldReturnTrueWhenBookedSeatIsFree() {
        // Given
        String roomName = "room1";
        String userName = "john";
        SeatDto seatDto1 = new SeatDto(roomName, 1L, 1L);
        SeatDto seatDto2 = new SeatDto(roomName, 2L, 2L);
        ScreeningDto screening = new ScreeningDto();
        BookingDto bookingDto1 = new BookingDto(userName, screening, seatDto1);
        BookingDto bookingDto2 = new BookingDto(userName, screening, seatDto2);
        List<BookingDto> bookings = List.of(bookingDto1, bookingDto2);
        SeatDto seatToBook = new SeatDto(roomName, 1L, 2L);
        boolean expected = true;

        // When
        boolean actual = this.underTest.isSeatFree(bookings, seatToBook);

        // Then
        assertEquals(expected, actual);
    }
}