package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.BasePriceDto;
import com.epam.training.ticketservice.entity.BasePrice;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BasePriceMapper {
    private final ModelMapper modelMapper;

    public BasePriceDto convertBasePriceEntityToDto(final BasePrice basePrice) {
        return modelMapper.map(basePrice, BasePriceDto.class);
    }
}
