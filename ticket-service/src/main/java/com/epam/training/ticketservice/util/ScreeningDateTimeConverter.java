package com.epam.training.ticketservice.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScreeningDateTimeConverter {
    final String dateTimePattern = "yyyy-MM-dd HH:mm";

    public LocalDateTime convertScreeningTimeString(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, this.getTimeFormat());
    }

    public String convertScreeningDateToString(LocalDateTime date) {
        return date.format(this.getTimeFormat());
    }

    private DateTimeFormatter getTimeFormat() {
        return DateTimeFormatter.ofPattern(dateTimePattern);
    }
}
