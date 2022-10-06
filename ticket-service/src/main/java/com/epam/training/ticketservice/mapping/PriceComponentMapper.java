package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.PriceComponentDto;
import com.epam.training.ticketservice.entity.PriceComponent;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceComponentMapper {
    private final ModelMapper modelMapper;

    public PriceComponent convertPriceComponentDtoToEntity(PriceComponentDto priceComponentDto) {
        return this.modelMapper.map(priceComponentDto, PriceComponent.class);
    }

    public PriceComponentDto convertPriceComponentEntityToDto(PriceComponent priceComponent) {
        return this.modelMapper.map(priceComponent, PriceComponentDto.class);
    }
}
