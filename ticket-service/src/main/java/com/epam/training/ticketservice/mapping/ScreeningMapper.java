package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.ScreeningDto;
import com.epam.training.ticketservice.entity.Screening;
import com.epam.training.ticketservice.util.ScreeningDateTimeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScreeningMapper {
    private final ScreeningDateTimeConverter converter;

    public ScreeningDto mapScreeningToDto(Screening screening) {
        return new ScreeningDto(
                screening.getMovieName(),
                screening.getRoomName(),
                this.converter.convertScreeningDateToString(screening.getTimeOfScreening())
        );
    }

    public Screening mapScreeningDtoToEntity(ScreeningDto screeningDto) {
        return new Screening(
                screeningDto.getMovieName(),
                screeningDto.getRoomName(),
                this.converter.convertScreeningTimeString(screeningDto.getTimeOfScreening())
        );
    }
}
