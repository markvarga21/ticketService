package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entity.ScreeningPriceAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningPriceAttachmentRepository extends JpaRepository<ScreeningPriceAttachment, Long> {
}
