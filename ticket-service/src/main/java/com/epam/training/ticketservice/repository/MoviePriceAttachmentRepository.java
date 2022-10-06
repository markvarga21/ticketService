package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entity.MoviePriceAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviePriceAttachmentRepository extends JpaRepository<MoviePriceAttachment, Long> {
}
