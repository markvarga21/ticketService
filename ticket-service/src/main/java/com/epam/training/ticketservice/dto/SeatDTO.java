package com.epam.training.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {
    private String roomName;
    private Long seatRow;
    private Long seatColumn;

    @Override
    public String toString() {
        return "(" + this.seatRow + "," + this.seatColumn + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SeatDTO seatDTO) {
            return seatDTO.getRoomName().equals(this.roomName)
                    && seatDTO.getSeatRow().equals(this.seatRow)
                    && seatDTO.getSeatColumn().equals(this.seatColumn);
        }
        return false;
    }
}
