package com.epam.training.ticketservice.util;

import com.epam.training.ticketservice.dto.ScreeningDTO;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class ScreeningValidator {
    private final MovieService movieService;
    private final RoomService roomService;
    private final ScreeningDateTimeConverter converter;
    final static long TIME_FOR_BREAK_BETWEEN_SCREENINGS = 10L;

    public boolean isValidReservation(ScreeningDTO screeningDTO) {
        String movieName = screeningDTO.getMovieName();
        String roomName = screeningDTO.getRoomName();
        return this.isValidMovie(movieName)
                && this.isValidRoom(roomName);
    }

    private boolean isValidRoom(String roomName) {
        return this.roomService.roomExists(roomName);
    }

    private boolean isValidMovie(String movieName) {
        return this.movieService.movieExists(movieName);
    }

    public boolean isValidScreenDateTime(String movieName, String dateTime, List<ScreeningDTO> screeningsInTheRoom) {
        var newScreeningStart = this.converter.convertScreeningTimeString(dateTime);
        var newScreeningEnd = this.calculateScreeningEndTime(movieName, newScreeningStart);

        for (var screening : screeningsInTheRoom) {
            var currentlyScreeningStart = this.converter.convertScreeningTimeString(screening.getTimeOfScreening());
            var currentlyScreeningEnd = this.calculateScreeningEndTime(screening.getMovieName(), currentlyScreeningStart);
            if (!this.isOverlapping(
                    newScreeningStart,
                    newScreeningEnd,
                    currentlyScreeningStart,
                    currentlyScreeningEnd)) {
                return true;
            }
        }
        return false;
    }

    private LocalDateTime calculateScreeningEndTime(String movieName, LocalDateTime startDateTime) {
        var movieLength = this.movieService.getMovieLengthForTitle(movieName);
        return startDateTime.plusMinutes(movieLength);
    }

    private boolean isOverlapping(LocalDateTime startA, LocalDateTime endA, LocalDateTime startB, LocalDateTime endB) {
        return startA.isBefore(startB) && endA.isAfter(startB) ||
                startA.isBefore(endB) && endA.isAfter(endB) ||
                startA.isBefore(startB) && endA.isAfter(endB) ||
                startA.isAfter(startB) && endA.isBefore(endB);
    }

    public boolean isPausePresent(String movieName, String roomName, String timeOfScreening, List<ScreeningDTO> screeningsInTheRoom) {
        var newScreeningStart = this.converter.convertScreeningTimeString(timeOfScreening);
        var newScreeningEnd = this.calculateScreeningEndTime(movieName, newScreeningStart);

        for (var screening : screeningsInTheRoom) {
            var currentlyScreeningStart = this.converter.convertScreeningTimeString(screening.getTimeOfScreening());
            var currentlyScreeningEnd = this.calculateScreeningEndTime(screening.getMovieName(), currentlyScreeningStart);

            if (!this.isPausePresentForDates(
                    newScreeningStart,
                    newScreeningEnd,
                    currentlyScreeningStart,
                    currentlyScreeningEnd)) {
                return false;
            }
        }
        return true;
    }

    private boolean isPausePresentForDates(LocalDateTime startA, LocalDateTime endA, LocalDateTime startB, LocalDateTime endB) {
        if (startA.isBefore(startB) && endA.isBefore(startB) && calculateMinutesBetweenTwoDates(endA, startB) <= TIME_FOR_BREAK_BETWEEN_SCREENINGS
                || startB.isBefore(startA) && endB.isBefore(startA) && calculateMinutesBetweenTwoDates(endB, startA) <= TIME_FOR_BREAK_BETWEEN_SCREENINGS
                || endA.equals(startB) || endB.equals(startA)) {
            return false;
        }
        return true;
    }

    private long calculateMinutesBetweenTwoDates(LocalDateTime fromDate, LocalDateTime toDate) {
        return ChronoUnit.MINUTES.between(fromDate, toDate);
    }
}
