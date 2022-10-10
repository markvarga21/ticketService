package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.mapping.BookingMapper;
import com.epam.training.ticketservice.mapping.ScreeningMapper;
import com.epam.training.ticketservice.mapping.SeatConverter;
import com.epam.training.ticketservice.mapping.SeatMapper;
import com.epam.training.ticketservice.repository.BookingPriceRepository;
import com.epam.training.ticketservice.repository.BookingRepository;
import com.epam.training.ticketservice.repository.SeatRepository;
import com.epam.training.ticketservice.util.BookingValidator;
import com.epam.training.ticketservice.util.PriceCalculator;
import com.epam.training.ticketservice.util.ScreeningDateTimeConverter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    private BookingService underTest;
    private SeatConverter seatConverter;
    private SeatMapper seatMapper;
    private BookingRepository bookingRepository;
    private SeatRepository seatRepository;
    private BookingPriceRepository bookingPriceRepository;
    private ScreeningDateTimeConverter dateTimeConverter;
    private BookingMapper bookingMapper;
    private ScreeningMapper screeningMapper;
    private BookingValidator bookingValidator;
    private PriceCalculator priceCalculator;

}