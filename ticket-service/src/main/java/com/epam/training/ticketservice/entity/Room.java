package com.epam.training.ticketservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Room {
    @Id
    private String name;
    private Long chairRowsCount;
    private Long chairColumnsCount;
}
