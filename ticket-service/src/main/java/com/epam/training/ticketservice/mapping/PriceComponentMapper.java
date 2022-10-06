package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.PriceComponentDTO;
import com.epam.training.ticketservice.entity.PriceComponent;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceComponentMapper {
    private final ModelMapper modelMapper;

    public PriceComponent convertPriceComponentDtoToEntity(PriceComponentDTO priceComponentDTO) {
        return this.modelMapper.map(priceComponentDTO, PriceComponent.class);
    }

    public PriceComponentDTO convertPriceComponentEntityToDto(PriceComponent priceComponent) {
        return this.modelMapper.map(priceComponent, PriceComponentDTO.class);
    }
}
