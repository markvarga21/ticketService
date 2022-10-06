package com.epam.training.ticketservice.util;

import com.epam.training.ticketservice.service.SigningService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AvailabilityManager {
    private final SigningService signingService;

    public Availability checkAdmin() {
        boolean isAdminLoggedIn = this.signingService.isAdminLoggedIn();
        return isAdminLoggedIn ?  Availability.available() : Availability.unavailable("Only admin has access for this command!");
    }

    public Availability checkUserLoggedIn() {
        boolean isSomeoneLoggedIn = this.signingService.isSomeoneLoggedIn();
        return isSomeoneLoggedIn ?  Availability.available() : Availability.unavailable("Please login before executing this command!");
    }

    public Availability anyone() {
        return Availability.available();
    }
}
