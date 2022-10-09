package com.epam.training.ticketservice.util;

import com.epam.training.ticketservice.service.PricingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceCalculatorTest {
    @InjectMocks
    private PriceCalculator underTest;
    @Mock
    private PricingService pricingService;

    @Test
    public void testCalculatePricing() {
        // Given
        String roomName = "bigRoom";
        String movieName = "Avengers";
        String dateOfScreening = "2022-12-12 10:10";
        int numberOfSeats = 3;
        long basePrice = 1500L;
        long roomAttachmentPrice = 1000L;
        long movieAttachmentPrice = 1200L;
        long screeningAttachmentPrice = 900L;
        Long expected = numberOfSeats
                * (basePrice
                    + roomAttachmentPrice
                    + movieAttachmentPrice
                    + screeningAttachmentPrice);

        // When
        when(this.pricingService.getBasePrice())
                .thenReturn(basePrice);
        when(this.pricingService.getAttachmentForRoom(roomName))
                .thenReturn(roomAttachmentPrice);
        when(this.pricingService.getAttachmentForMovie(movieName))
                .thenReturn(movieAttachmentPrice);
        when(this.pricingService.getAttachmentForScreening(roomName, movieName, dateOfScreening))
                .thenReturn(screeningAttachmentPrice);
        Long actual = this.underTest.calculatePricing(numberOfSeats, roomName, movieName, dateOfScreening);

        // Then
        assertEquals(expected, actual);
    }
}