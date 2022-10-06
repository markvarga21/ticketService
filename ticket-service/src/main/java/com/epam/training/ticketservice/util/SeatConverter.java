package com.epam.training.ticketservice.util;

import com.epam.training.ticketservice.dto.SeatDTO;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SeatConverter {
    public List<SeatDTO> convertSeatStringToList(String seats, String roomName) {
        return Arrays.stream(seats.split(" "))
                .map(s -> new SeatDTO(roomName, Long.valueOf(s.split(",")[0]), Long.valueOf(s.split(",")[1])))
                .toList();
    }

    public int getSeatNumberOfString(String seats) {
        return seats.split(" ").length;
    }
}
