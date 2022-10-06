package com.epam.training.ticketservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "FILM_USER")
public class User {
    @Id
    private String userName;
    private String password;
}
