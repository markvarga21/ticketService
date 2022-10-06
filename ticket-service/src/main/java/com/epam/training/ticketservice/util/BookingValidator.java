package com.epam.training.ticketservice.util;

import com.epam.training.ticketservice.dto.BookingDto;
import com.epam.training.ticketservice.dto.SeatDto;
import com.epam.training.ticketservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingValidator {
    private final RoomService roomService;

    public boolean isValidSeatForRoom(String roomName, SeatDto seatToCheck) {
        return this.roomService
                .getRooms()
                .stream()
                .filter(roomDTO -> roomDTO.getName().equals(roomName))
                .anyMatch(roomDTO -> seatToCheck.getSeatColumn() <= roomDTO.getChairColumnsCount()
                            && seatToCheck.getSeatRow() <= roomDTO.getChairRowsCount());
    }

    public boolean isSeatFree(List<BookingDto> bookingsForScreening, SeatDto seatDto) {
        return bookingsForScreening.stream()
                .noneMatch(bookingDto -> bookingDto.getBookedSeat().equals(seatDto));
    }
}
