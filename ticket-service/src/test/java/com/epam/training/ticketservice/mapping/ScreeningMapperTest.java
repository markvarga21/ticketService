package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.ScreeningDto;
import com.epam.training.ticketservice.entity.Screening;
import com.epam.training.ticketservice.util.ScreeningDateTimeConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScreeningMapperTest {
    @InjectMocks
    private ScreeningMapper underTest;
    @Mock
    private ScreeningDateTimeConverter converter;

    @Test
    public void testMapScreeningToDto() {
        // Given
        String dateOfScreeningString = "2022-12-12 10:10";
        LocalDateTime dateOfScreening = LocalDateTime.of(2022, 12, 12, 10, 10);
        String movieName = "Avengers";
        String roomName = "bigRoom";
        Screening screening = new Screening(movieName, roomName, dateOfScreening);
        ScreeningDto expected = new ScreeningDto(movieName, roomName, dateOfScreeningString);

        // When
        when(this.converter.convertScreeningDateToString(any()))
                .thenReturn(dateOfScreeningString);
        ScreeningDto actual = this.underTest.mapScreeningToDto(screening);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testMapScreeningDtoToEntity() {
        // Given
        String dateOfScreeningString = "2022-12-12 10:10";
        LocalDateTime dateOfScreening = LocalDateTime.of(2022, 12, 12, 10, 10);
        String movieName = "Avengers";
        String roomName = "bigRoom";
        Screening expected = new Screening(movieName, roomName, dateOfScreening);
        ScreeningDto screeningDto = new ScreeningDto(movieName, roomName, dateOfScreeningString);

        // When
        when(this.converter.convertScreeningTimeString(any()))
                .thenReturn(dateOfScreening);
        Screening actual = this.underTest.mapScreeningDtoToEntity(screeningDto);

        // Then
        assertEquals(expected, actual);
    }
}