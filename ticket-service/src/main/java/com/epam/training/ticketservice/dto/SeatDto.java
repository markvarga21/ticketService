package com.epam.training.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatDto {
    private String roomName;
    private Long seatRow;
    private Long seatColumn;

    @Override
    public String toString() {
        return "(" + this.seatRow + "," + this.seatColumn + ")";
    }

    @Override
    public boolean equals(Object o) {
        SeatDto seatDto = (SeatDto) o;
        return seatDto.getRoomName().equals(this.roomName)
                && seatDto.getSeatRow().equals(this.seatRow)
                && seatDto.getSeatColumn().equals(this.seatColumn);
    }
}
