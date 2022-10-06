package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.MovieDto;
import com.epam.training.ticketservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieMapper {
    private final ModelMapper modelMapper;

    public MovieDto mapMovieToDto(Movie movie) {
        return this.modelMapper.map(movie, MovieDto.class);
    }

    public Movie mapMovieDtoToEntity(MovieDto movieDto) {
        return this.modelMapper.map(movieDto, Movie.class);
    }
}
