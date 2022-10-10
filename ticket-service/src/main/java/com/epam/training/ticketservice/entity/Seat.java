package com.epam.training.ticketservice.entity;

import com.epam.training.ticketservice.dto.SeatCompositeKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(SeatCompositeKey.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Seat {
    @Id
    private String roomName;
    @Id
    private Long seatRow;
    @Id
    private Long seatColumn;
}
