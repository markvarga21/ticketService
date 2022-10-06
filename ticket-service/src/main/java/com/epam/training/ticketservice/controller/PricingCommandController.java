package com.epam.training.ticketservice.controller;

import com.epam.training.ticketservice.service.PricingService;
import com.epam.training.ticketservice.service.SigningService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class PricingCommandController {
    private final PricingService pricingService;
    private final SigningService signingService;

    public Availability checkAdmin() {
        boolean isAdminLoggedIn = this.signingService.isAdminLoggedIn();
        return isAdminLoggedIn ?  Availability.available() : Availability.unavailable("Only admin has access for this command!");
    }

    @ShellMethod(value = "Updating base price.", key = "update base price")
    @ShellMethodAvailability("checkAdmin")
    public String updateBasePrice(Long newPrice) {
        return this.pricingService.updateBasePrice(newPrice);
    }

    @ShellMethod(value = "Creating price components.", key = "create price component")
    @ShellMethodAvailability("checkAdmin")
    public String createPriceComponentForRoom(String componentName, Long componentPrice) {
        return this.pricingService.addPricingComponent(componentName, componentPrice);
    }

    @ShellMethod(value = "Attaching price components to rooms.", key = "attach price component to room")
    @ShellMethodAvailability("checkAdmin")
    public String attachPriceComponentToRoom(String componentName, String roomName) {
        return this.pricingService.attachPriceComponentToRoom(componentName, roomName);
    }

    @ShellMethod(value = "Attaching price components to movies.", key = "attach price component to movie")
    @ShellMethodAvailability("checkAdmin")
    public String attachPriceComponentToMovie(String componentName, String movieName) {
        return this.pricingService.attachPriceComponentToMovie(componentName, movieName);
    }

    @ShellMethod(value = "Attaching price components to screenings.", key = "attach price component to screening")
    @ShellMethodAvailability("checkAdmin")
    public String attachPriceComponentToScreening(String componentName, String movieName, String roomName, String dateOfScreening) {
        return this.pricingService.attachPriceComponentToScreening(componentName, movieName, roomName, dateOfScreening);
    }

    @ShellMethod(value = "Getting price information about a booking.", key = "show price for")
    public String getInfoAboutBookingPrice(String movieTitle, String roomName, String screeningDate, String seats) {
        return this.pricingService.getInfoAboutBookingPrice(movieTitle, roomName, screeningDate, seats);
    }
}
