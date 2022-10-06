package com.epam.training.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {
    private String userName;
    private ScreeningDto screeningDto;
    private SeatDto bookedSeat;
}
