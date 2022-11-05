package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.BookingDto;
import com.epam.training.ticketservice.dto.ScreeningDto;
import com.epam.training.ticketservice.dto.SeatDto;
import com.epam.training.ticketservice.entity.Booking;
import com.epam.training.ticketservice.entity.BookingPrice;
import com.epam.training.ticketservice.entity.Screening;
import com.epam.training.ticketservice.entity.Seat;
import com.epam.training.ticketservice.mapping.BookingMapper;
import com.epam.training.ticketservice.mapping.ScreeningMapper;
import com.epam.training.ticketservice.mapping.SeatConverter;
import com.epam.training.ticketservice.mapping.SeatMapper;
import com.epam.training.ticketservice.repository.BookingPriceRepository;
import com.epam.training.ticketservice.repository.BookingRepository;
import com.epam.training.ticketservice.repository.SeatRepository;
import com.epam.training.ticketservice.util.BookingValidator;
import com.epam.training.ticketservice.util.PriceCalculator;
import com.epam.training.ticketservice.util.ScreeningDateTimeConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @InjectMocks
    private BookingService underTest;
    @Mock
    private SeatConverter seatConverter;
    @Mock
    private SeatMapper seatMapper;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private BookingPriceRepository bookingPriceRepository;
    @Mock
    private ScreeningDateTimeConverter dateTimeConverter;
    @Mock
    private BookingMapper bookingMapper;
    @Mock
    private ScreeningMapper screeningMapper;
    @Mock
    private BookingValidator bookingValidator;
    @Mock
    private PriceCalculator priceCalculator;

    @Test
    public void testSaveBookingsWhenSeatNotExists() {
        // Given
        // Data to test
        String userNameToTest = "john";
        String movieTitleToTest = "Avengers";
        String timeOfScreeningToTest = "2022-12-12 10:10";
        String roomNameToTest = "bigRoom";
        String seatsToBookToTest = "1,1 2,2";
        Screening screening = new Screening(userNameToTest, roomNameToTest, LocalDateTime.of(2022, 12, 12, 10, 10));
        ScreeningDto screeningDto = new ScreeningDto(movieTitleToTest, roomNameToTest, timeOfScreeningToTest);
        // Existing bookings
        Optional<List<Booking>> existingBookings = Optional.of(List.of(
                Booking.builder()
                        .bookingId(1L)
                        .bookedSeat(new Seat(roomNameToTest, 1L, 1L))
                        .userName(userNameToTest)
                        .screening(screening)
                        .build()
        ));
        SeatDto seatDto1 = new SeatDto(roomNameToTest, 1L, 1L);
        Seat seatEntity1 = new Seat(roomNameToTest, 1L, 1L);
        SeatDto seatDto2 = new SeatDto(roomNameToTest, 2L, 2L);
        List<SeatDto> seats = List.of(
                seatDto1,
                seatDto2
        );
        BookingDto bookingDto = new BookingDto(userNameToTest, screeningDto, seats.get(0));
        String expected = "Seat (1,1) does not exist in this room";

        // When
        when(this.seatConverter.convertSeatStringToList(seatsToBookToTest, roomNameToTest))
                .thenReturn(seats);
        when(this.bookingRepository.getBookingsByScreening(any()))
                .thenReturn(existingBookings);
        when(this.bookingMapper.convertBookingEntityToDto(any()))
                .thenReturn(bookingDto);
        when(this.seatMapper.convertSeatDtoToEntity(any()))
                .thenReturn(seatEntity1);
        when(this.bookingValidator.isValidSeatForRoom(any(), any()))
                .thenReturn(false);
        String actual = this.underTest.saveBookings(userNameToTest, movieTitleToTest, roomNameToTest, timeOfScreeningToTest, seatsToBookToTest);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testSaveBookingsWhenSeatIsNotFree() {
        // Given
        // Data to test
        String userNameToTest = "john";
        String movieTitleToTest = "Avengers";
        String timeOfScreeningToTest = "2022-12-12 10:10";
        String roomNameToTest = "bigRoom";
        String seatsToBookToTest = "1,1 2,2";
        Screening screening = new Screening(userNameToTest, roomNameToTest, LocalDateTime.of(2022, 12, 12, 10, 10));
        ScreeningDto screeningDto = new ScreeningDto(movieTitleToTest, roomNameToTest, timeOfScreeningToTest);
        // Existing bookings
        Optional<List<Booking>> existingBookings = Optional.of(List.of(
                Booking.builder()
                        .bookingId(1L)
                        .bookedSeat(new Seat(roomNameToTest, 1L, 1L))
                        .userName(userNameToTest)
                        .screening(screening)
                        .build()
        ));
        SeatDto seatDto1 = new SeatDto(roomNameToTest, 1L, 1L);
        Seat seatEntity1 = new Seat(roomNameToTest, 1L, 1L);
        SeatDto seatDto2 = new SeatDto(roomNameToTest, 2L, 2L);
        List<SeatDto> seats = List.of(
                seatDto1,
                seatDto2
        );
        BookingDto bookingDto = new BookingDto(userNameToTest, screeningDto, seats.get(0));
        String expected = "Seat (1,1) is already taken";

        // When
        when(this.seatConverter.convertSeatStringToList(seatsToBookToTest, roomNameToTest))
                .thenReturn(seats);
        when(this.bookingRepository.getBookingsByScreening(any()))
                .thenReturn(existingBookings);
        when(this.bookingMapper.convertBookingEntityToDto(any()))
                .thenReturn(bookingDto);
        when(this.seatMapper.convertSeatDtoToEntity(any()))
                .thenReturn(seatEntity1);
        when(this.bookingValidator.isValidSeatForRoom(any(), any()))
                .thenReturn(true);
        when(this.bookingValidator.isSeatFree(any(), any()))
                .thenReturn(false);
        String actual = this.underTest.saveBookings(userNameToTest, movieTitleToTest, roomNameToTest, timeOfScreeningToTest, seatsToBookToTest);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testSaveBookingWhenEverythingIsValid() {
        // Given
        String userNameToTest = "john";
        String movieTitleToTest = "Avengers";
        String timeOfScreeningToTest = "2022-12-12 10:10";
        String roomNameToTest = "bigRoom";
        String seatsToBookToTest = "1,1 2,2";

        SeatDto seatDto1 = new SeatDto(roomNameToTest, 1L, 1L);
        SeatDto seatDto2 = new SeatDto(roomNameToTest, 2L, 2L);
        List<SeatDto> seats = List.of(
                seatDto1,
                seatDto2
        );
        Optional<List<Booking>> emptyBookingListOptional = Optional.of(
                List.of(new Booking(), new Booking())
        );
        String expected = "Seats booked: (1,1), (2,2); the price for this booking is 1500 HUF";

        // When
        when(this.bookingValidator.isValidSeatForRoom(any(), any()))
                .thenReturn(true);
        when(this.bookingValidator.isSeatFree(any(), any()))
                .thenReturn(true);
        when(this.seatConverter.convertSeatStringToList(seatsToBookToTest, roomNameToTest))
                .thenReturn(seats);
        when(this.bookingRepository.getBookingsByScreening(any()))
                .thenReturn(emptyBookingListOptional);
        when(this.screeningMapper.mapScreeningDtoToEntity(any()))
                .thenReturn(new Screening());
        when(this.bookingMapper.convertBookingEntityToDto(any()))
                .thenReturn(new BookingDto());
        when(this.priceCalculator.calculatePricing(anyInt(), anyString(), anyString(), anyString()))
                .thenReturn(1500L);
        String actual = this.underTest.saveBookings(userNameToTest, movieTitleToTest, roomNameToTest, timeOfScreeningToTest, seatsToBookToTest);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBookingsForUserWhenEmpty() {
        // Given
        String expected = "You have not booked any tickets yet";

        // When
        when(this.bookingRepository.getBookingsByUserName(anyString()))
                .thenReturn(Optional.of(List.of()));
        String actual = this.underTest.getBookingsForUser(anyString());

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBookingsForUser() {
        // Given
        String userName = "john";
        String movieName = "Avenger";
        String roomName = "bigRoom";
        Seat seat = new Seat(roomName, 1L, 1L);
        SeatDto seatDto = new SeatDto(roomName, 1L, 1L);
        LocalDateTime timeOfScreening = LocalDateTime.of(2022, 12, 12, 10, 10);
        String timeOfScreeningString = "2022-12-12 10:10";
        Long basePrice = 1500L;
        Screening screening = new Screening(movieName, roomName, timeOfScreening);
        ScreeningDto screeningDto = new ScreeningDto(movieName, roomName, timeOfScreeningString);
        Optional<List<Booking>> bookings = Optional.of(
                List.of(
                        new Booking(1L, screening, userName, seat)
                )
        );
        BookingDto bookingDto = new BookingDto(userName, screeningDto, seatDto);
        BookingPrice bookingPrice = new BookingPrice(1L, roomName, movieName, timeOfScreeningString, basePrice);
        Optional<BookingPrice> bookingPriceOptional = Optional.of(bookingPrice);
        String expected = "Your previous bookings are\nSeats (1,1) on Avenger in room bigRoom starting at 2022-12-12 10:10 for 1500 HUF";


        // When
        when(this.screeningMapper.mapScreeningDtoToEntity(any()))
                .thenReturn(screening);
        when(this.bookingRepository.getBookingsByScreening(any()))
                .thenReturn(bookings);
        when(this.bookingRepository.getBookingsByUserName(anyString()))
                .thenReturn(bookings);
        when(this.bookingMapper.convertBookingEntityToDto(any()))
                .thenReturn(bookingDto);
        when(this.bookingPriceRepository.getBookingPriceByRoomNameAndMovieNameAndScreeningDate(anyString(), anyString(), anyString()))
                .thenReturn(bookingPriceOptional);
        String actual = this.underTest.getBookingsForUser(userName);

        // Then
        assertEquals(expected, actual);
    }
}