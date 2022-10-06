package com.epam.training.ticketservice.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BasePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long basePrice;
}
