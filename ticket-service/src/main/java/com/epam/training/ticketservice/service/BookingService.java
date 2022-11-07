package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.BookingDto;
import com.epam.training.ticketservice.dto.ScreeningDto;
import com.epam.training.ticketservice.dto.SeatDto;
import com.epam.training.ticketservice.entity.Booking;
import com.epam.training.ticketservice.entity.Screening;
import com.epam.training.ticketservice.entity.BookingPrice;
import com.epam.training.ticketservice.entity.Seat;
import com.epam.training.ticketservice.mapping.BookingMapper;
import com.epam.training.ticketservice.mapping.ScreeningMapper;
import com.epam.training.ticketservice.mapping.SeatMapper;
import com.epam.training.ticketservice.repository.BookingRepository;
import com.epam.training.ticketservice.repository.BookingPriceRepository;
import com.epam.training.ticketservice.repository.SeatRepository;
import com.epam.training.ticketservice.util.BookingValidator;
import com.epam.training.ticketservice.util.PriceCalculator;
import com.epam.training.ticketservice.util.ScreeningDateTimeConverter;
import com.epam.training.ticketservice.mapping.SeatConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final SeatConverter seatConverter;
    private final SeatMapper seatMapper;
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final BookingPriceRepository bookingPriceRepository;
    private final ScreeningDateTimeConverter dateTimeConverter;
    private final BookingMapper bookingMapper;
    private final ScreeningMapper screeningMapper;
    private final BookingValidator bookingValidator;
    private final PriceCalculator priceCalculator;

    public String saveBookings(String userName,
                               String movieTitle,
                               String roomName,
                               String timeOfScreening,
                               String seatsToBook) {
        List<SeatDto> seats = this.seatConverter.convertSeatStringToList(seatsToBook, roomName);
        List<Booking> bookingsToSave = new ArrayList<>();
        ScreeningDto screeningDto = new ScreeningDto(movieTitle, roomName, timeOfScreening);
        List<BookingDto> bookingsForScreening = this.getBookingsForScreening(screeningDto);
        final String seatsString = seats.stream().map(SeatDto::toString).collect(Collectors.joining(", "));
        for (SeatDto seatDto : seats) {
            Seat seat = this.seatMapper.convertSeatDtoToEntity(seatDto);
            if (!this.bookingValidator.isValidSeatForRoom(roomName, seatDto)) {
                return String.format("Seat %s does not exist in this room", seatDto);
            }
            if (!this.bookingValidator.isSeatFree(bookingsForScreening, seatDto)) {
                return String.format("Seat %s is already taken", seatDto);
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

        BookingPrice screeningBasePrice = new BookingPrice();
        screeningBasePrice.setRoomName(roomName);
        screeningBasePrice.setMovieName(movieTitle);
        screeningBasePrice.setScreeningDate(timeOfScreening);
        screeningBasePrice.setBasePrice(bookingPrice);

        this.bookingPriceRepository.save(screeningBasePrice);
        return String.format("Seats booked: %s; the price for this booking is %d HUF", seatsString, bookingPrice);
    }

    private List<BookingDto> getBookingsForScreening(ScreeningDto screeningDto) {
        return this.bookingRepository
                .getBookingsByScreening(this.screeningMapper.mapScreeningDtoToEntity(screeningDto))
                .get()
                .stream()
                .map(this.bookingMapper::convertBookingEntityToDto)
                .collect(Collectors.toList());
    }

    public String getBookingsForUser(String userName) {
        var bookingEntitiesOptional = this.bookingRepository.getBookingsByUserName(userName);
        if (bookingEntitiesOptional.get().isEmpty()) {
            return "You have not booked any tickets yet";
        }
        var bookingEntities = bookingEntitiesOptional.get();
        var bookingDtoList = bookingEntities
                .stream()
                .map(this.bookingMapper::convertBookingEntityToDto)
                .collect(Collectors.toList());
        List<ScreeningDto> bookedScreenings = bookingDtoList
                .stream()
                .map(BookingDto::getScreeningDto)
                .distinct()
                .collect(Collectors.toList());

        return this.formatBookingListForScreenings(bookedScreenings);
    }

    private String formatBookingListForScreenings(List<ScreeningDto> screenings) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Your previous bookings are")
                .append("\n");
        for (int i = 0; i < screenings.size(); i++) {
            var screeningEntity = this.screeningMapper.mapScreeningDtoToEntity(screenings.get(i));
            var bookingsForScreening = this.bookingRepository.getBookingsByScreening(screeningEntity).get();
            var bookedSeats = bookingsForScreening.stream()
                    .map(this.bookingMapper::convertBookingEntityToDto)
                    .map(BookingDto::getBookedSeat)
                    .map(SeatDto::toString)
                    .collect(Collectors.toList());
            String bookedSeatsString = String.join(", ", bookedSeats);
            var priceForBookingOptional = this.bookingPriceRepository
                    .getBookingPriceByRoomNameAndMovieNameAndScreeningDate(
                            screenings.get(i).getRoomName(),
                            screenings.get(i).getMovieName(),
                            screenings.get(i).getTimeOfScreening()
                    );
            long bookingPrice = priceForBookingOptional.get().getBasePrice();

            String bookRecord = String.format("Seats %s on %s in room %s starting at %s for %d HUF",
                    bookedSeatsString,
                    screenings.get(i).getMovieName(),
                    screenings.get(i).getRoomName(),
                    screenings.get(i).getTimeOfScreening(),
                    bookingPrice
            );
            stringBuilder.append(bookRecord);
            if (i < screenings.size() - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
