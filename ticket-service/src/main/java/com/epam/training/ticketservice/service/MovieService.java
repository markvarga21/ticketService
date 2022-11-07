package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.MovieDto;
import com.epam.training.ticketservice.entity.Movie;
import com.epam.training.ticketservice.mapping.MovieMapper;
import com.epam.training.ticketservice.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Transactional
    public void saveMovie(final MovieDto movie) {
        Movie movieEntity = this.movieMapper.mapMovieDtoToEntity(movie);
        this.movieRepository.save(movieEntity);
    }

    public List<MovieDto> getMovies() {
        return this.movieRepository
                .findAll()
                .stream()
                .map(this.movieMapper::mapMovieToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public int deleteMovie(String title) {
        return this.movieRepository.deleteByTitle(title);
    }

    @Transactional
    public String updateMovie(final MovieDto movieDto) {
        var movieOptional = this.movieRepository.findById(movieDto.getTitle());

        if (movieOptional.isEmpty()) {
            return String.format("Cannot update, because movie '%s' not found!", movieDto.getTitle());
        }

        Movie movieToUpdate = movieOptional.get();
        movieToUpdate.setGenre(movieDto.getGenre());
        movieToUpdate.setLength(movieDto.getLength());
        this.movieRepository.save(movieToUpdate);

        return String.format("Movie '%s' updated successfully!", movieDto.getTitle());
    }

    public boolean movieExists(String movieName) {
        return this.movieRepository.existsById(movieName);
    }

    public Long getMovieLengthForTitle(String movieName) {
        return this.getMovies()
                .stream()
                .filter(movieDTO1 -> movieDTO1.getTitle().equals(movieName))
                .findFirst()
                .get()
                .getLength();
    }
}
