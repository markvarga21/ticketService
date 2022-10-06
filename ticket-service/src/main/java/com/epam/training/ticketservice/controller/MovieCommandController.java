package com.epam.training.ticketservice.controller;

import com.epam.training.ticketservice.dto.MovieDTO;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.SigningService;
import com.epam.training.ticketservice.util.Formatter;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class MovieCommandController {
    private final SigningService signingService;
    private final MovieService movieService;
    private final Formatter formatter;
    public Availability checkAdmin() {
        boolean isAdminLoggedIn = this.signingService.isAdminLoggedIn();
        return isAdminLoggedIn ?  Availability.available() : Availability.unavailable("Only admin has access for this command!");
    }

    public Availability anyone() {
        return Availability.available();
    }

    @ShellMethod(value = "Creating movies", key = "create movie")
    @ShellMethodAvailability("checkAdmin")
    public String createMovie(String name, String genre, Long length) {
        MovieDTO movieToSave = new MovieDTO(name, genre, length);
        this.movieService.saveMovie(movieToSave);
        return String.format("Movie '%s' with the length of %d created!", name, length);
    }

    @ShellMethod(value = "Listing movies.", key = "list movies")
    @ShellMethodAvailability("anyone")
    public String listMovies() {
        var movies = this.movieService.getMovies();
        return this.formatter.formatMovieList(movies);
    }

    @ShellMethod(value = "Deleting movies.", key = "delete movie")
    @ShellMethodAvailability("checkAdmin")
    public String deleteMovie(String title) {
        if (this.movieService.deleteMovie(title) == 1) {
            return String.format("Movie '%s' deleted successfully!", title);
        }
        return String.format("Something went wrong when deleting movie '%s'", title);
    }

    @ShellMethod(value = "Updating movies.", key = "update movie")
    @ShellMethodAvailability("checkAdmin")
    public String updateMovie(String title, String genre, Long length) {
        MovieDTO movieToSave = new MovieDTO(title, genre, length);
        return this.movieService.updateMovie(movieToSave);
    }
}
