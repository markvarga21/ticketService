package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entity.Booking;
import com.epam.training.ticketservice.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<List<Booking>> getBookingsByUserName(String userName);

    Optional<List<Booking>> getBookingsByScreening(Screening screening);
}
