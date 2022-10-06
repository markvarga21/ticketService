package com.epam.training.ticketservice.controller;

import com.epam.training.ticketservice.service.BookingService;
import com.epam.training.ticketservice.service.SigningService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class BookingCommandController {
    private final SigningService signingService;
    private final BookingService bookingService;

    public Availability checkUserLoggedIn() {
        boolean isSomeoneLoggedIn = this.signingService.isSomeoneLoggedIn();
        return isSomeoneLoggedIn
                ? Availability.available() :
                Availability.unavailable("Please login before executing this command!");
    }

    @ShellMethod(value = "Booking tickets.", key = "book")
    @ShellMethodAvailability("checkUserLoggedIn")
    public String addBooking(String movieTitle, String roomName, String timeOfScreening, String seatsToBook) {
        String userName = this.signingService.getLoggedInUser();
        return this.bookingService.saveBookings(userName, movieTitle, roomName, timeOfScreening, seatsToBook);
    }
}
