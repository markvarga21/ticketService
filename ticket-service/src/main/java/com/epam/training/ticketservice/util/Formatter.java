package com.epam.training.ticketservice.util;

import com.epam.training.ticketservice.dto.MovieDto;
import com.epam.training.ticketservice.dto.RoomDto;
import com.epam.training.ticketservice.dto.ScreeningDto;
import com.epam.training.ticketservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Formatter {
    private final MovieService movieService;

    public String formatMovieList(List<MovieDto> movies) {
        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        }
        return movies.stream()
                .map(MovieDto::toString)
                .collect(Collectors.joining("\n", "", ""));
    }

    public String formatRoomList(List<RoomDto> rooms) {
        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        }
        return rooms.stream()
                .map(RoomDto::toString)
                .collect(Collectors.joining("\n", "", ""));
    }

    public String formatScreenings(List<ScreeningDto> screenings) {
        if (screenings.isEmpty()) {
            return "There are no screenings";
        }
        StringBuilder stringBuilder = new StringBuilder();
        var movies = this.movieService.getMovies();
        for (int i = 0; i < screenings.size(); i++) {
            String movieTitle = screenings.get(i).getMovieName();
            String roomName = screenings.get(i).getRoomName();
            String date = screenings.get(i).getTimeOfScreening();

            MovieDto movie = movies.stream()
                    .filter(movieDTO -> movieDTO.getTitle().equals(movieTitle))
                    .findFirst()
                    .get();


            String record = String.format("%s, screened in room %s, at %s", movie, roomName, date);
            stringBuilder
                    .append(record);
            if (i < screenings.size() - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
