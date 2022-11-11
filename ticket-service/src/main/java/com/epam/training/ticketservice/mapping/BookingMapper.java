package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.BookingDto;
import com.epam.training.ticketservice.dto.ScreeningDto;
import com.epam.training.ticketservice.dto.SeatDto;
import com.epam.training.ticketservice.entity.Booking;
import com.epam.training.ticketservice.entity.Screening;
import com.epam.training.ticketservice.entity.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingMapper {
    private final ScreeningMapper screeningMapper;
    private final SeatMapper seatMapper;

    public BookingDto convertBookingEntityToDto(final Booking booking) {
        final Screening screening = booking.getScreening();
        final ScreeningDto screeningDto = screeningMapper.mapScreeningToDto(screening);
        final Seat bookedSeat1 = booking.getBookedSeat();
        final SeatDto bookedSeat = seatMapper.convertSeatEntityToDto(bookedSeat1);
        final String userName = booking.getUserName();
        return BookingDto.builder()
                .userName(userName)
                .screeningDto(screeningDto)
                .bookedSeat(bookedSeat)
                .build();
    }
}
