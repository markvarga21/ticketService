package com.epam.training.ticketservice.util;

import com.epam.training.ticketservice.dto.ScreeningDto;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.RoomService;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScreeningValidatorTest {
    @InjectMocks
    private ScreeningValidator underTest;
    @Mock
    private RoomService roomService;
    @Mock
    private ScreeningDateTimeConverter converter;
    @Mock
    private MovieService movieService;
    Logger log = Logger.getLogger(this.getClass().getName());

    @Test
    public void testIsValidReservationShouldReturnFalseWhenMovieNotExists() {
        // Given
        String movieName = "Avengers";
        String roomName = "nagyterem";
        String dateString = "2022-12-12 12:12";
        ScreeningDto screeningDto = new ScreeningDto(movieName, roomName, dateString);
        boolean expected = false;

        // When
        when(this.movieService.movieExists(movieName))
                .thenReturn(false);
        boolean actual = this.underTest.isValidReservation(screeningDto);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testIsValidReservationShouldReturnFalseWhenRoomNotExists() {
        // Given
        String movieName = "Avengers";
        String roomName = "nagyterem";
        String dateString = "2022-12-12 12:12";
        ScreeningDto screeningDto = new ScreeningDto(movieName, roomName, dateString);
        boolean expected = false;

        // When
        when(this.movieService.movieExists(movieName))
                .thenReturn(true);
        when(this.roomService.roomExists(roomName))
                .thenReturn(false);
        boolean actual = this.underTest.isValidReservation(screeningDto);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testIsValidReservationShouldReturnTrueWhenCorrect() {
        // Given
        String movieName = "Avengers";
        String roomName = "nagyterem";
        String dateString = "2022-12-12 12:12";
        ScreeningDto screeningDto = new ScreeningDto(movieName, roomName, dateString);
        boolean expected = true;

        // When
        when(this.movieService.movieExists(movieName))
                .thenReturn(true);
        when(this.roomService.roomExists(roomName))
                .thenReturn(true);
        boolean actual = this.underTest.isValidReservation(screeningDto);

        // Then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("isValidScreeningData")
    public void testIsValidScreenDateTime(LocalDateTime startA, Long lengthA, LocalDateTime startB, Long lengthB) {
        // Given
        String movieName = "dummy";
        String dateTime = "dummyDate";
        ScreeningDto screeningDto = new ScreeningDto();
        List<ScreeningDto> screeningDtoList = List.of(screeningDto, screeningDto, screeningDto, screeningDto);
        boolean expected = false;

        // When
        when(this.converter.convertScreeningTimeString(any()))
                .thenReturn(startA)
                .thenReturn(startB);
        when(this.movieService.getMovieLengthForTitle(any()))
                .thenReturn(lengthA)
                .thenReturn(lengthB);
        boolean actual = this.underTest.isValidScreenDateTime(movieName, dateTime, screeningDtoList);

        // Then
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> isValidScreeningData() {
        int year = 2022;
        int month = 12;
        int day = 12;
        LocalDateTime startA = LocalDateTime.of(year, month, day, 10, 0);
        Long lengthA = 120L;
        return Stream.of(
                Arguments.of(startA, lengthA,
                        LocalDateTime.of(year, month, day, 10, 10), 60L
                ),
                Arguments.of(startA, lengthA,
                        LocalDateTime.of(year, month, day, 9, 0), 240L
                ),
                Arguments.of(startA, lengthA,
                        LocalDateTime.of(year, month, day, 9, 0), 120L
                ),
                Arguments.of(startA, lengthA,
                        LocalDateTime.of(year, month, day, 11, 0), 120L
                )
        );
    }

    @Test
    void isPausePresent() {
    }
}