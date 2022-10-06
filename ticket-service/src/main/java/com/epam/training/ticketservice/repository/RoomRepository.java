package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    int deleteByName(String name);
}
