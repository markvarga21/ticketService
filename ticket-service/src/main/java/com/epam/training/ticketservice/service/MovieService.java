package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.MovieDTO;
import com.epam.training.ticketservice.entity.Movie;
import com.epam.training.ticketservice.mapping.MovieMapper;
import com.epam.training.ticketservice.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Transactional
    public void saveMovie(final MovieDTO movie) {
        Movie movieEntity = this.movieMapper.mapMovieDtoToEntity(movie);
        this.movieRepository.save(movieEntity);
    }

    public List<MovieDTO> getMovies() {
        return this.movieRepository
                .findAll()
                .stream()
                .map(this.movieMapper::mapMovieToDto)
                .toList();
    }

    @Transactional
    public int deleteMovie(String title) {
        return this.movieRepository.deleteByTitle(title);
    }

    @Transactional
    public String updateMovie(final MovieDTO movieDTO) {
        var movieOptional = this.movieRepository.findById(movieDTO.getTitle());

        if (movieOptional.isEmpty()) {
            return String.format("Cannot update, because movie '%s' not found!", movieDTO.getTitle());
        }

        Movie movieToUpdate = movieOptional.get();
        movieToUpdate.setGenre(movieDTO.getGenre());
        movieToUpdate.setLength(movieDTO.getLength());
        this.movieRepository.save(movieToUpdate);

        return String.format("Movie '%s' updated successfully!", movieDTO.getTitle());
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
