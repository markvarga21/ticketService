package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.entity.BasePrice;
import com.epam.training.ticketservice.entity.MoviePriceAttachment;
import com.epam.training.ticketservice.entity.RoomPriceAttachment;
import com.epam.training.ticketservice.entity.PriceComponent;
import com.epam.training.ticketservice.entity.ScreeningPriceAttachment;

import com.epam.training.ticketservice.mapping.BasePriceMapper;

import com.epam.training.ticketservice.repository.BasePriceRepository;
import com.epam.training.ticketservice.repository.PriceComponentRepository;
import com.epam.training.ticketservice.repository.RoomPriceAttachmentRepository;
import com.epam.training.ticketservice.repository.MoviePriceAttachmentRepository;
import com.epam.training.ticketservice.repository.ScreeningPriceAttachmentRepository;

import com.epam.training.ticketservice.mapping.SeatConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class PricingService {
    private final BasePriceRepository basePriceRepository;
    private final PriceComponentRepository priceComponentRepository;
    private final RoomPriceAttachmentRepository roomPriceAttachmentRepository;
    private final MoviePriceAttachmentRepository moviePriceAttachmentRepository;
    private final ScreeningPriceAttachmentRepository screeningPriceAttachmentRepository;
    private final BasePriceMapper basePriceMapper;
    private final SeatConverter seatConverter;

    @PostConstruct
    private void postConstruct() {
        BasePrice basePrice = new BasePrice();
        basePrice.setBasePrice(1500L);
        this.basePriceRepository.save(basePrice);
    }

    public String updateBasePrice(Long newPrice) {
        var oldBasePriceEntity = this.basePriceRepository.findAll().get(0);
        var oldBasePriceDto = this.basePriceMapper.convertBasePriceEntityToDto(oldBasePriceEntity);
        oldBasePriceEntity.setBasePrice(newPrice);
        this.basePriceRepository.save(oldBasePriceEntity);
        return String.format("Old base price %d update to %d", oldBasePriceDto.getBasePrice(), newPrice);
    }

    public Long getBasePrice() {
        return this.basePriceRepository
                .findAll()
                .get(0)
                .getBasePrice();
    }

    public Long getAttachmentForRoom(String roomName) {
        var componentNameOptional = this.roomPriceAttachmentRepository
                .findAll()
                .stream()
                .filter(roomPriceAttachment -> roomPriceAttachment.getRoomName().equals(roomName))
                .map(RoomPriceAttachment::getComponentName)
                .findFirst();
        if (componentNameOptional.isEmpty()) {
            return 0L;
        }
        String componentName = componentNameOptional.get();
        return this.getPriceForComponent(componentName);
    }

    public Long getAttachmentForMovie(String movieName) {
        var componentNameOptional = this.moviePriceAttachmentRepository
                .findAll()
                .stream()
                .filter(moviePriceAttachment -> moviePriceAttachment.getMovieName().equals(movieName))
                .map(MoviePriceAttachment::getComponentName)
                .findFirst();
        if (componentNameOptional.isEmpty()) {
            return 0L;
        }
        String componentName = componentNameOptional.get();
        return this.getPriceForComponent(componentName);
    }

    public Long getAttachmentForScreening(String roomName, String movieName, String screeningDate) {
        var componentNameOptional = this.screeningPriceAttachmentRepository
                .findAll()
                .stream()
                .filter(screeningPriceAttachment ->
                        screeningPriceAttachment.getRoomName().equals(roomName)
                                && screeningPriceAttachment.getMovieName().equals(movieName)
                                && screeningPriceAttachment.getScreeningDate().equals(screeningDate)
                )
                .map(ScreeningPriceAttachment::getComponentName)
                .findFirst();
        if (componentNameOptional.isEmpty()) {
            return 0L;
        }
        String componentName = componentNameOptional.get();
        return this.getPriceForComponent(componentName);
    }

    private Long getPriceForComponent(String componentName) {
        var componentPriceOptional = this.priceComponentRepository
                .findAll()
                .stream()
                .filter(priceComponent -> priceComponent.getComponentName().equals(componentName))
                .map(PriceComponent::getComponentPrice)
                .findFirst();
        if (componentPriceOptional.isEmpty()) {
            return 0L;
        }
        return componentPriceOptional.get();
    }

    public String addPricingComponent(String componentName, Long componentPrice) {
        PriceComponent priceComponentToSave = new PriceComponent();
        priceComponentToSave.setComponentName(componentName);
        priceComponentToSave.setComponentPrice(componentPrice);
        this.priceComponentRepository.save(priceComponentToSave);
        return String.format("Price component with name %s and price %d saved successfully!",
                componentName,
                componentPrice);
    }

    public String attachPriceComponentToRoom(String componentName, String roomName) {
        RoomPriceAttachment roomPriceAttachment = new RoomPriceAttachment();
        roomPriceAttachment.setComponentName(componentName);
        roomPriceAttachment.setRoomName(roomName);
        this.roomPriceAttachmentRepository.save(roomPriceAttachment);
        return String.format("Component with name %s attached to room %s", componentName, roomName);
    }

    public String attachPriceComponentToMovie(String componentName, String movieName) {
        MoviePriceAttachment moviePriceAttachment = new MoviePriceAttachment();
        moviePriceAttachment.setComponentName(componentName);
        moviePriceAttachment.setMovieName(movieName);
        this.moviePriceAttachmentRepository.save(moviePriceAttachment);
        return String.format("Component with name %s attached to movie %s", componentName, movieName);
    }

    public String attachPriceComponentToScreening(String componentName,
                                                  String movieName,
                                                  String roomName,
                                                  String dateOfScreening) {
        ScreeningPriceAttachment screeningPriceAttachment = new ScreeningPriceAttachment();
        screeningPriceAttachment.setComponentName(componentName);
        screeningPriceAttachment.setMovieName(movieName);
        screeningPriceAttachment.setRoomName(roomName);
        screeningPriceAttachment.setScreeningDate(dateOfScreening);
        this.screeningPriceAttachmentRepository.save((screeningPriceAttachment));
        return String.format("Component with name %s attached to screening %s, %s, %s",
                componentName,
                movieName,
                roomName,
                dateOfScreening);
    }

    public String getInfoAboutBookingPrice(String movieTitle, String roomName, String screeningDate, String seats) {
        int numberOfSeats = this.seatConverter.getSeatNumberOfString(seats);
        Long roomPrice = this.getAttachmentForRoom(roomName);
        Long moviePrice = this.getAttachmentForMovie(movieTitle);
        Long screeningPrice = this.getAttachmentForScreening(roomName, movieTitle, screeningDate);
        Long basePrice = this.getBasePrice();

        Long totalPrice = (roomPrice + moviePrice + screeningPrice + basePrice) * numberOfSeats;
        return String.format("The price for this booking would be %d HUF", totalPrice);
    }
}
