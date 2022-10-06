package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entity.RoomPriceAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomPriceAttachmentRepository extends JpaRepository<RoomPriceAttachment, Long> {
}
