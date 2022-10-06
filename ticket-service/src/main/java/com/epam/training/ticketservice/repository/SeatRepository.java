package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entity.Seat;
import com.epam.training.ticketservice.util.SeatCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, SeatCompositeKey> {
}
