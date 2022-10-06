package com.epam.training.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningDto {
    private String movieName;
    private String roomName;
    private String timeOfScreening;
}
