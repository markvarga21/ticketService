package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.SeatDTO;
import com.epam.training.ticketservice.entity.Seat;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatMapper {
    private final ModelMapper modelMapper;

    public Seat convertSeatDtoToEntity(SeatDTO seatDTO) {
        return new Seat(seatDTO.getRoomName(), seatDTO.getSeatRow(), seatDTO.getSeatColumn());
    }

    public SeatDTO convertSeatEntityToDto(Seat seat) {
        return new SeatDTO(seat.getRoomName(), seat.getSeatRow(), seat.getSeatColumn());
    }
}
