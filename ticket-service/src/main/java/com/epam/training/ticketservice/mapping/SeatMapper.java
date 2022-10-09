package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.SeatDto;
import com.epam.training.ticketservice.entity.Seat;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

    public Seat convertSeatDtoToEntity(SeatDto seatDto) {
        return new Seat(seatDto.getRoomName(), seatDto.getSeatRow(), seatDto.getSeatColumn());
    }

    public SeatDto convertSeatEntityToDto(Seat seat) {
        return new SeatDto(seat.getRoomName(), seat.getSeatRow(), seat.getSeatColumn());
    }
}
