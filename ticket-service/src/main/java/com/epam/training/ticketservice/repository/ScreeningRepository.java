package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entity.Screening;
import com.epam.training.ticketservice.dto.CompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, CompositeKey> {
}
