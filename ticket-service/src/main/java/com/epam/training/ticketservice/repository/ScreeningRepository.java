package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entity.Screening;
import com.epam.training.ticketservice.util.CompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, CompositeKey> {
}
