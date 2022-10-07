package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entity.BookingPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingPriceRepository extends JpaRepository<BookingPrice, Long> {
    Optional<BookingPrice> getBookingPriceByRoomNameAndMovieNameAndScreeningDate(String roomName,
                                                                               String movieName,
                                                                               String screeningDate);
}
