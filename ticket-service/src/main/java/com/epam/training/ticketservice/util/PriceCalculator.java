package com.epam.training.ticketservice.util;

import com.epam.training.ticketservice.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceCalculator {
    private final PricingService pricingService;

    public Long calculatePricing(int numberOfSeatsToBook, String roomName, String movieName, String dateOfScreening) {
        return this.calculateAttachmentPricePerSeat(roomName, movieName, dateOfScreening) * numberOfSeatsToBook;
    }

    private Long calculateAttachmentPricePerSeat(String roomName, String movieName, String dateOfScreening) {
        return this.pricingService.getBasePrice() +
                this.priceAttachmentForRoom(roomName) +
                this.priceAttachmentForMovie(movieName) +
                this.priceAttachmentForScreening(roomName, movieName, dateOfScreening);
    }

    private Long priceAttachmentForRoom(String roomName) {
        return this.pricingService.getAttachmentForRoom(roomName);
    }

    private Long priceAttachmentForMovie(String movieName) {
        return this.pricingService.getAttachmentForMovie(movieName);
    }

    private Long priceAttachmentForScreening(String roomName, String movieName, String screeningDate) {
        return this.pricingService.getAttachmentForScreening(roomName, movieName, screeningDate);
    }
}
