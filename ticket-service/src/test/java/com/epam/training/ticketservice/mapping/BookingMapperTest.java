package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.BookingDto;
import com.epam.training.ticketservice.dto.ScreeningDto;
import com.epam.training.ticketservice.dto.SeatDto;
import com.epam.training.ticketservice.entity.Booking;
import com.epam.training.ticketservice.entity.Screening;
import com.epam.training.ticketservice.entity.Seat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingMapperTest {
    @InjectMocks
    private BookingMapper underTest;
    @Mock
    private ScreeningMapper screeningMapper;
    @Mock
    private SeatMapper seatMapper;

    @Test
    public void testConvertBookingEntityToDto() {
        // Given
        ScreeningDto screeningDto = new ScreeningDto("Avengers", "bigRoom", "2022-12-12 14:40");

        Screening screening = new Screening();
        screening.setMovieName("Avengers");
        screening.setRoomName("bigRoom");
        screening.setTimeOfScreening(LocalDateTime.of(2022, 12, 12, 14, 40));

        SeatDto seatDto = new SeatDto("bigRoom", 5L, 5L);
        Seat seat = new Seat("bigRoom", 5L, 5L);

        Booking booking = new Booking();
        booking.setScreening(screening);
        booking.setUserName("john");
        booking.setBookedSeat(seat);

        BookingDto expected = new BookingDto("john", screeningDto, seatDto);

        // When
        when(this.screeningMapper.mapScreeningToDto(any()))
                .thenReturn(screeningDto);
        when(this.seatMapper.convertSeatEntityToDto(any()))
                .thenReturn(seatDto);
        BookingDto actual = this.underTest.convertBookingEntityToDto(booking);

        // Then
        assertEquals(expected, actual);
    }
}