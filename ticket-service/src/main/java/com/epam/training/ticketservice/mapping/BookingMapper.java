package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.BookingDTO;
import com.epam.training.ticketservice.dto.ScreeningDTO;
import com.epam.training.ticketservice.entity.Booking;
import com.epam.training.ticketservice.util.ScreeningDateTimeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingMapper {
    private final ScreeningMapper screeningMapper;
    private final SeatMapper seatMapper;

    public BookingDTO convertBookingEntityToDto(Booking booking) {
        return BookingDTO.builder()
                .userName(booking.getUserName())
                .screeningDTO(this.screeningMapper.mapScreeningToDto(booking.getScreening()))
                .bookedSeat(this.seatMapper.convertSeatEntityToDto(booking.getBookedSeat()))
                .build();
    }
}
