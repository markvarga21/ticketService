package com.epam.training.ticketservice.entity;

import com.epam.training.ticketservice.dto.CompositeKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CompositeKey.class)
public class Screening implements Serializable {
    @Id
    private String movieName;
    @Id
    private String roomName;
    @Id
    private LocalDateTime timeOfScreening;
}
