package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entity.PriceComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceComponentRepository extends JpaRepository<PriceComponent, String> {
}
