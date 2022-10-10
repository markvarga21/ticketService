package com.epam.training.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeatCompositeKey implements Serializable {
    private String roomName;
    private Long seatRow;
    private Long seatColumn;
}
