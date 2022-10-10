package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.BasePriceDto;
import com.epam.training.ticketservice.entity.*;
import com.epam.training.ticketservice.mapping.BasePriceMapper;
import com.epam.training.ticketservice.mapping.SeatConverter;
import com.epam.training.ticketservice.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PricingServiceTest {
    @InjectMocks
    private PricingService underTest;
    @Mock
    private BasePriceRepository basePriceRepository;
    @Mock
    private PriceComponentRepository priceComponentRepository;
    @Mock
    private RoomPriceAttachmentRepository roomPriceAttachmentRepository;
    @Mock
    private MoviePriceAttachmentRepository moviePriceAttachmentRepository;
    @Mock
    private ScreeningPriceAttachmentRepository screeningPriceAttachmentRepository;
    @Mock
    private BasePriceMapper basePriceMapper;
    @Mock
    private SeatConverter seatConverter;

    @Test
    public void testUpdateBasePrice() {
        // Given
        Long newPrice = 2000L;
        String expected = "Old base price 1500 update to 2000";
        BasePrice basePrice = new BasePrice(1L, 1500L);
        BasePriceDto basePriceDto = new BasePriceDto(1500L);
        List<BasePrice> basePriceList = List.of(basePrice);

        // When
        when(this.basePriceRepository.findAll())
                .thenReturn(basePriceList);
        when(this.basePriceMapper.convertBasePriceEntityToDto(basePrice))
                .thenReturn(basePriceDto);
        String actual = this.underTest.updateBasePrice(newPrice);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBasePriceShouldThrowExceptionWhenEmpty() {
        // Given
        List<BasePrice> basePriceList = List.of();

        // When
        when(this.basePriceRepository.findAll())
                .thenReturn(basePriceList);
        // Then
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> this.underTest.getBasePrice()
        );
    }

    @Test
    public void testGetBasePrice() {
        // Given
        BasePrice basePrice = new BasePrice(1L, 1500L);
        List<BasePrice> basePriceList = List.of(basePrice);
        Long expected = 1500L;

        // When
        when(this.basePriceRepository.findAll())
                .thenReturn(basePriceList);
        Long actual = this.underTest.getBasePrice();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAttachmentForMovieShouldReturnZeroWhenNotExists() {
        // Given
        String movieName = "Avengers";
        Long expected = 0L;

        // When
        when(this.moviePriceAttachmentRepository.findAll())
                .thenReturn(List.of());
        Long actual = this.underTest.getAttachmentForMovie(movieName);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAttachmentForMovieWhenComponentNotFound() {
        // Given
        String movieName = "Avengers";
        Long expected = 0L;
        String componentName = "extra";
        MoviePriceAttachment moviePriceAttachment = new MoviePriceAttachment(1L, componentName, movieName);

        // When
        when(this.moviePriceAttachmentRepository.findAll())
                .thenReturn(List.of(moviePriceAttachment));
        when(this.priceComponentRepository.findAll())
                .thenReturn(List.of());
        Long actual = this.underTest.getAttachmentForMovie(movieName);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAttachmentForRoomWhenComponentNotFound() {
        // Given
        Long expected = 0L;
        String roomName = "bigRoom";

        // When
        when(this.roomPriceAttachmentRepository.findAll())
                .thenReturn(List.of());
        Long actual = this.underTest.getAttachmentForRoom(roomName);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAttachmentForScreeningWhenComponentNotFound() {
        // Given
        String movieName = "Avengers";
        Long expected = 0L;
        String roomName = "bigRoom";
        String screeningDate = "2022-12-12 10:10";

        // When
        when(this.screeningPriceAttachmentRepository.findAll())
                .thenReturn(List.of());
        Long actual = this.underTest.getAttachmentForScreening(roomName, movieName, screeningDate);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAttachmentForMovie() {
        // Given
        String movieName = "Avengers";
        Long expected = 2500L;
        String componentName = "extra";
        MoviePriceAttachment moviePriceAttachment = new MoviePriceAttachment(1L, componentName, movieName);
        PriceComponent priceComponent = new PriceComponent(componentName, expected);

        // When
        when(this.moviePriceAttachmentRepository.findAll())
                .thenReturn(List.of(moviePriceAttachment));
        when(this.priceComponentRepository.findAll())
                .thenReturn(List.of(priceComponent));
        Long actual = this.underTest.getAttachmentForMovie(movieName);

        // Then
        assertEquals(expected, actual);
    }


    @Test
    public void testGetAttachmentForScreening() {
        String movieName = "Avengers";
        Long expected = 2500L;
        String componentName = "extra";
        String roomName = "bigRoom";
        String screeningDate = "2022-12-12 10:10";
        ScreeningPriceAttachment screeningPriceAttachment = new ScreeningPriceAttachment(1L, componentName, movieName, roomName, screeningDate);
        PriceComponent priceComponent = new PriceComponent(componentName, expected);

        // When
        when(this.screeningPriceAttachmentRepository.findAll())
                .thenReturn(List.of(screeningPriceAttachment));
        when(this.priceComponentRepository.findAll())
                .thenReturn(List.of(priceComponent));
        Long actual = this.underTest.getAttachmentForScreening(roomName, movieName, screeningDate);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAttachmentForRoom() {
        Long expected = 2500L;
        String componentName = "extra";
        String roomName = "bigRoom";
        RoomPriceAttachment roomPriceAttachment = new RoomPriceAttachment(1L, componentName, roomName);
        PriceComponent priceComponent = new PriceComponent(componentName, expected);

        // When
        when(this.roomPriceAttachmentRepository.findAll())
                .thenReturn(List.of(roomPriceAttachment));
        when(this.priceComponentRepository.findAll())
                .thenReturn(List.of(priceComponent));
        Long actual = this.underTest.getAttachmentForRoom(roomName);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testAddPricingComponent() {
        // Given
        String componentName = "extra";
        Long componentPrice = 2500L;
        String expected = "Price component with name extra and price 2500 saved successfully!";

        // When
        String actual = this.underTest.addPricingComponent(componentName, componentPrice);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testAttachPriceComponent() {
        // Given
        String movieName = "Avengers";
        String componentName = "extra";
        String roomName = "bigRoom";
        String screeningDate = "2022-12-12 10:10";
        String roomExpected = "Component with name extra attached to room bigRoom";
        String movieExpected = "Component with name extra attached to movie Avengers";
        String screeningExpected = "Component with name extra attached to screening Avengers, bigRoom, 2022-12-12 10:10";

        // When
        String roomActual = this.underTest.attachPriceComponentToRoom(componentName, roomName);
        String movieActual = this.underTest.attachPriceComponentToMovie(componentName, movieName);
        String screeningActual = this.underTest.attachPriceComponentToScreening(componentName, movieName, roomName, screeningDate);

        // Then
        assertAll(
                () -> assertEquals(roomExpected, roomActual),
                () -> assertEquals(movieExpected, movieActual),
                () -> assertEquals(screeningExpected, screeningActual)
        );
    }

    @Test
    public void testGetInfoAboutBookingPrice() {
        // Given
        String movieName = "Avengers";
        String componentName = "extra";
        Long componentPrice = 1000L;
        String roomName = "bigRoom";
        String screeningDate = "2022-12-12 10:10";
        String seats = "1,1 1,2";
        BasePrice basePrice = new BasePrice(1L, 1500L);
        RoomPriceAttachment roomPriceAttachment = new RoomPriceAttachment(1L, componentName, roomName);
        ScreeningPriceAttachment screeningPriceAttachment = new ScreeningPriceAttachment(1L, componentName, movieName, roomName, screeningDate);
        MoviePriceAttachment moviePriceAttachment = new MoviePriceAttachment(1L, componentName, movieName);
        PriceComponent priceComponent = new PriceComponent(componentName, componentPrice);
        String expected = "The price for this booking would be 9000 HUF";

        // When
        when(this.seatConverter.getSeatNumberOfString(seats))
                .thenReturn(2);
        when(this.screeningPriceAttachmentRepository.findAll())
                .thenReturn(List.of(screeningPriceAttachment));
        when(this.priceComponentRepository.findAll())
                .thenReturn(List.of(priceComponent));
        when(this.roomPriceAttachmentRepository.findAll())
                .thenReturn(List.of(roomPriceAttachment));
        when(this.priceComponentRepository.findAll())
                .thenReturn(List.of(priceComponent));
        when(this.moviePriceAttachmentRepository.findAll())
                .thenReturn(List.of(moviePriceAttachment));
        when(this.priceComponentRepository.findAll())
                .thenReturn(List.of(priceComponent));
        when(this.basePriceRepository.findAll())
                .thenReturn(List.of(basePrice));
        String actual = this.underTest.getInfoAboutBookingPrice(movieName, roomName, screeningDate, seats);

        // Then
        assertEquals(expected, actual);
    }
}