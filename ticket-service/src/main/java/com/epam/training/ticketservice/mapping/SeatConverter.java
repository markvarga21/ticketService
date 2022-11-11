package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.SeatDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SeatConverter {
    public List<SeatDto> convertSeatStringToList(final String seats, final String roomName) {
        String[] split = seats.split(" ");
        return Arrays.stream(split)
                .map(s -> {
                    final Long seatRow = Long.valueOf(s.split(",")[0]);
                    Long seatColumn = Long.valueOf(s.split(",")[1]);
                    return new SeatDto(roomName, seatRow, seatColumn);
                })
                .collect(Collectors.toList());
    }

    public int getSeatNumberOfString(final String seats) {
        return seats.split(" ").length;
    }
}
