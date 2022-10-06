package com.epam.training.ticketservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "FILM_USER_SESSION")
public class UserSession {
    @Id
    private String userName;
}
