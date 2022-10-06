package com.epam.training.ticketservice.util;

import com.epam.training.ticketservice.dto.BookingDTO;
import com.epam.training.ticketservice.dto.SeatDTO;
import com.epam.training.ticketservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingValidator {
    private final RoomService roomService;

    public boolean isValidSeatForRoom(String roomName, SeatDTO seatToCheck) {
        return this.roomService
                .getRooms()
                .stream()
                .filter(roomDTO -> roomDTO.getName().equals(roomName))
                .anyMatch(roomDTO -> seatToCheck.getSeatColumn() <= roomDTO.getChairColumnsCount()
                            && seatToCheck.getSeatRow() <= roomDTO.getChairRowsCount());
    }

    public boolean isSeatFree(List<BookingDTO> bookingsForScreening, SeatDTO seatDTO) {
        return bookingsForScreening.stream()
                .noneMatch(bookingDTO -> bookingDTO.getBookedSeat().equals(seatDTO));
    }
}
