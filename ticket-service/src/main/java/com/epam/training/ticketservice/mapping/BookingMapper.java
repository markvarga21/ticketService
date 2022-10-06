package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.BookingDto;
import com.epam.training.ticketservice.entity.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingMapper {
    private final ScreeningMapper screeningMapper;
    private final SeatMapper seatMapper;

    public BookingDto convertBookingEntityToDto(Booking booking) {
        return BookingDto.builder()
                .userName(booking.getUserName())
                .screeningDto(this.screeningMapper.mapScreeningToDto(booking.getScreening()))
                .bookedSeat(this.seatMapper.convertSeatEntityToDto(booking.getBookedSeat()))
                .build();
    }
}
