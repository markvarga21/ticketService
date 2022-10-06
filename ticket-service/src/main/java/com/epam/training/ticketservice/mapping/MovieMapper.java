package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.MovieDTO;
import com.epam.training.ticketservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieMapper {
    private final ModelMapper modelMapper;
    public MovieDTO mapMovieToDto(Movie movie) {
        return this.modelMapper.map(movie, MovieDTO.class);
    }

    public Movie mapMovieDtoToEntity(MovieDTO movieDTO) {
        return this.modelMapper.map(movieDTO, Movie.class);
    }
}
