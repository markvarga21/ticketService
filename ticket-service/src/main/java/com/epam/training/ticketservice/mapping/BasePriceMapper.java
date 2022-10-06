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

    public BasePrice convertBasePriceDtoToEntity(BasePriceDto basePriceDto) {
        return this.modelMapper.map(basePriceDto, BasePrice.class);
    }

    public BasePriceDto convertBasePriceEntityToDto(BasePrice basePrice) {
        return this.modelMapper.map(basePrice, BasePriceDto.class);
    }
}
