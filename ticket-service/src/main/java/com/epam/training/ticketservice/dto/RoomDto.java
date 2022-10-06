package com.epam.training.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private String name;
    private Long chairRowsCount;
    private Long chairColumnsCount;

    @Override
    public String toString() {
        return String.format("Room %s with %d seats, %d rows and %d columns",
                this.name,
                this.chairRowsCount * this.chairColumnsCount,
                this.chairRowsCount,
                this.chairColumnsCount);
    }
}
