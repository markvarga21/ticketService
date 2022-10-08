package com.epam.training.ticketservice.util;

import com.epam.training.ticketservice.dto.MovieDto;
import com.epam.training.ticketservice.dto.RoomDto;
import com.epam.training.ticketservice.dto.ScreeningDto;
import com.epam.training.ticketservice.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FormatterTest {
    @InjectMocks
    private Formatter underTest;
    @Mock
    private MovieService movieService;

    @Test
    void testFormatMovieListWhenEmpty() {
        // Given
        String expected = "There are no movies at the moment";

        // When
        String actual = this.underTest.formatMovieList(List.of());

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testFormatMovieListShouldReturnMovieList() {
        // Given
        List<MovieDto> movies = List.of(
                new MovieDto("Avengers", "sci-fi", 120L),
                new MovieDto("Mission Impossible", "action", 100L)
        );
        String firstRow = "Avengers (sci-fi, 120 minutes)";
        String secondRow = "Mission Impossible (action, 100 minutes)";
        String expected = firstRow +
                "\n" +
                secondRow;

        // When
        String actual = this.underTest.formatMovieList(movies);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testFormatRoomListWhenEmpty() {
        // Given
        String expected = "There are no rooms at the moment";

        // When
        String actual = this.underTest.formatRoomList(List.of());

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testFormatRoomListShouldReturnRoomList() {
        // Given
        List<RoomDto> rooms = List.of(
                new RoomDto("room1", 5L, 5L),
                new RoomDto("room2", 10L, 10L)
        );
        String firstRow = "Room room1 with 25 seats, 5 rows and 5 columns";
        String secondRow = "Room room2 with 100 seats, 10 rows and 10 columns";
        String expected = firstRow +
                "\n" +
                secondRow;

        // When
        String actual = this.underTest.formatRoomList(rooms);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testFormatScreeningsWhenEmpty() {
        // Given
        String expected = "There are no screenings";

        // When
        String actual = this.underTest.formatScreenings(List.of());

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testFormatScreeningListShouldReturnScreeningList() {
        // Given
        MovieDto movie1 = new MovieDto("Avengers", "sci-fi", 120L);
        MovieDto movie2 = new MovieDto("Mission Impossible", "action", 100L);
        ScreeningDto screening1 = new ScreeningDto(movie1.getTitle(), "nagyterem", "2022-12-12 10:10");
        ScreeningDto screening2 = new ScreeningDto(movie2.getTitle(), "kisterem", "2022-12-12 10:10");
        List<ScreeningDto> screeningList = List.of(screening1, screening2);
        String firstRow = "Avengers (sci-fi, 120 minutes), screened in room nagyterem, at 2022-12-12 10:10";
        String secondRow = "Mission Impossible (action, 100 minutes), screened in room kisterem, at 2022-12-12 10:10";
        String expected = firstRow +
                "\n" +
                secondRow;

        // When
        when(this.movieService.getMovies())
                .thenReturn(List.of(movie1, movie2));
        String actual = this.underTest.formatScreenings(screeningList);

        // Then
        assertEquals(expected, actual);
    }
}