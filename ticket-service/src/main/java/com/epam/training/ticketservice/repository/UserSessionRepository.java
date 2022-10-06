package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, String> {
    void deleteUserSessionByUserName(String userName);
}
