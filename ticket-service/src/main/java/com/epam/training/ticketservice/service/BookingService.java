package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.BookingDTO;
import com.epam.training.ticketservice.dto.ScreeningDTO;
import com.epam.training.ticketservice.dto.SeatDTO;
import com.epam.training.ticketservice.entity.Booking;
import com.epam.training.ticketservice.entity.Screening;
import com.epam.training.ticketservice.entity.Seat;
import com.epam.training.ticketservice.mapping.BookingMapper;
import com.epam.training.ticketservice.mapping.ScreeningMapper;
import com.epam.training.ticketservice.mapping.SeatMapper;
import com.epam.training.ticketservice.repository.BookingRepository;
import com.epam.training.ticketservice.repository.SeatRepository;
import com.epam.training.ticketservice.util.BookingValidator;
import com.epam.training.ticketservice.util.PriceCalculator;
import com.epam.training.ticketservice.util.ScreeningDateTimeConverter;
import com.epam.training.ticketservice.util.SeatConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final SeatConverter seatConverter;
    private final SeatMapper seatMapper;
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final ScreeningDateTimeConverter dateTimeConverter;
    private final BookingMapper bookingMapper;
    private final ScreeningMapper screeningMapper;
    private final BookingValidator bookingValidator;
    private final PriceCalculator priceCalculator;

    public String saveBookings(String userName, String movieTitle, String roomName, String timeOfScreening, String seatsToBook) {
        List<SeatDTO> seats = this.seatConverter.convertSeatStringToList(seatsToBook, roomName);
        List<Booking> bookingsToSave = new ArrayList<>();
        List<BookingDTO> bookingsForScreening = this.getBookingsForScreening(new ScreeningDTO(movieTitle, roomName, timeOfScreening));
        String seatsString = String.join(", ", seats.stream().map(SeatDTO::toString).toList());
        for (SeatDTO seatDTO : seats) {
            Seat seat = this.seatMapper.convertSeatDtoToEntity(seatDTO);
            if (!this.bookingValidator.isValidSeatForRoom(roomName, seatDTO)) {
                return String.format("Seat %s does not exist in this room", seatDTO);
            }
            if (!this.bookingValidator.isSeatFree(bookingsForScreening, seatDTO)) {
                return String.format("Seat %s is already taken", seatDTO);
            }
            this.seatRepository.save(seat);
            LocalDateTime screeningTime = this.dateTimeConverter.convertScreeningTimeString(timeOfScreening);
            Booking bookingEntity = Booking.builder()
                    .bookedSeat(seat)
                    .userName(userName)
                    .screening(new Screening(movieTitle, roomName, screeningTime))
                    .build();
            bookingsToSave.add(bookingEntity);
        }
        this.bookingRepository.saveAll(bookingsToSave);
        Long bookingPrice = this.priceCalculator.calculatePricing(seats.size(), roomName, movieTitle, timeOfScreening);
        return String.format("Seats booked: %s; the price for this booking is %d HUF", seatsString, bookingPrice);
    }

    private List<BookingDTO> getBookingsForScreening(ScreeningDTO screeningDTO) {
        // TODO this throws an error because it's not flushed and nor transient
        return this.bookingRepository
                .getBookingsByScreening(this.screeningMapper.mapScreeningDtoToEntity(screeningDTO))
                .get()
                .stream()
                .map(this.bookingMapper::convertBookingEntityToDto)
                .toList();
    }

    public String getBookingsForUser(String userName) {
        var bookingEntitiesOptional = this.bookingRepository.getBookingsByUserName(userName);
        if (bookingEntitiesOptional.get().isEmpty()) {
            return "You have not booked any tickets yet";
        }
        var bookingEntities = bookingEntitiesOptional.get();
        var bookingDtoList = bookingEntities.stream()
                .map(this.bookingMapper::convertBookingEntityToDto)
                .toList();
        List<ScreeningDTO> bookedScreenings = bookingDtoList.stream().map(BookingDTO::getScreeningDTO).distinct().toList();

        return this.formatBookingListForScreenings(bookedScreenings);
    }

    private String formatBookingListForScreenings(List<ScreeningDTO> screenings) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Your previous bookings are").append("\n");
        for (int i = 0; i < screenings.size(); i++) {
            var screeningEntity = this.screeningMapper.mapScreeningDtoToEntity(screenings.get(i));
            var bookingsForScreening = this.bookingRepository.getBookingsByScreening(screeningEntity).get();
            var bookedSeats = bookingsForScreening.stream()
                    .map(this.bookingMapper::convertBookingEntityToDto)
                    .map(BookingDTO::getBookedSeat)
                    .map(SeatDTO::toString)
                    .toList();
            var bookedSeatsString = String.join(", ", bookedSeats);
            String bookRecord = String.format("Seats %s on %s in room %s starting at %s for %d HUF",
                    bookedSeatsString,
                    screenings.get(i).getMovieName(),
                    screenings.get(i).getRoomName(),
                    screenings.get(i).getTimeOfScreening(),
                    this.priceCalculator.calculatePricing(
                            bookedSeats.size(),
                            screenings.get(i).getRoomName(),
                            screenings.get(i).getMovieName(),
                            screenings.get(i).getTimeOfScreening())
                    );
            stringBuilder.append(bookRecord);
            if (i < screenings.size() - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
