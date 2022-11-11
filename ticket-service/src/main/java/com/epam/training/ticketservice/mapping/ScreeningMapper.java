package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.ScreeningDto;
import com.epam.training.ticketservice.entity.Screening;
import com.epam.training.ticketservice.util.ScreeningDateTimeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScreeningMapper {
    private final ScreeningDateTimeConverter converter;

    public ScreeningDto mapScreeningToDto(final Screening screening) {
        final String movieName = screening.getMovieName();
        final String roomName = screening.getRoomName();
        final LocalDateTime timeOfScreening1 = screening.getTimeOfScreening();
        final String timeOfScreening = converter.convertScreeningDateToString(timeOfScreening1);
        return new ScreeningDto(
                movieName,
                roomName,
                timeOfScreening
        );
    }

    public Screening mapScreeningDtoToEntity(final ScreeningDto screeningDto) {
        final String movieName = screeningDto.getMovieName();
        final String roomName = screeningDto.getRoomName();
        final String timeOfScreening1 = screeningDto.getTimeOfScreening();
        final LocalDateTime timeOfScreening = converter.convertScreeningTimeString(timeOfScreening1);
        return new Screening(
                movieName,
                roomName,
                timeOfScreening
        );
    }
}
