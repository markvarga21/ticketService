package com.epam.training.ticketservice.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningDateTimeConverterTest {
    private ScreeningDateTimeConverter underTest;

    @BeforeEach
    public void init() {
        this.underTest = new ScreeningDateTimeConverter();
    }

    @Test
    public void testConvertScreeningTimeString() {
        // Given
        String dateString = "2022-12-12 10:10";
        LocalDateTime expected = LocalDateTime.of(2022, 12, 12, 10, 10);

        // When
        LocalDateTime actual = this.underTest.convertScreeningTimeString(dateString);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testConvertScreeningDateToString() {
        // Given
        LocalDateTime dateTime = LocalDateTime.of(2022, 12, 12, 10, 10);
        String expected = "2022-12-12 10:10";

        // When
        String actual = this.underTest.convertScreeningDateToString(dateTime);

        // Then
        assertEquals(expected, actual);
    }
}