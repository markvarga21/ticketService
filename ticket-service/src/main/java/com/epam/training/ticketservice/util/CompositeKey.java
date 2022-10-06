package com.epam.training.ticketservice.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompositeKey implements Serializable {
    private String movieName;
    private String roomName;
    private LocalDateTime timeOfScreening;
}
