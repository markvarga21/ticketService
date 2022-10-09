package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.MovieDto;
import com.epam.training.ticketservice.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

class MovieMapperTest {
    private MovieMapper underTest;

    @BeforeEach
    public void init() {
        this.underTest = new MovieMapper(new ModelMapper());
    }

    @Test
    public void testMapMovieToDto() {
        // Given
        String movieTitle = "Avengers";
        String movieGenre = "sci-fi";
        Long movieLength = 120L;
        Movie movie = new Movie(movieTitle, movieGenre, movieLength);
        MovieDto expected = new MovieDto(movieTitle, movieGenre, movieLength);

        // When
        MovieDto actual = this.underTest.mapMovieToDto(movie);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testMapMovieDtoToEntity() {
        // Given
        String movieTitle = "Avengers";
        String movieGenre = "sci-fi";
        Long movieLength = 120L;
        Movie expected = new Movie(movieTitle, movieGenre, movieLength);
        MovieDto movieDto = new MovieDto(movieTitle, movieGenre, movieLength);

        // When
        Movie actual = this.underTest.mapMovieDtoToEntity(movieDto);

        // Then
        assertEquals(expected, actual);
    }
}