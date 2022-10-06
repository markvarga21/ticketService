package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.BasePriceDTO;
import com.epam.training.ticketservice.entity.BasePrice;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BasePriceMapper {
    private final ModelMapper modelMapper;

    public BasePrice convertBasePriceDtoToEntity(BasePriceDTO basePriceDTO) {
        return this.modelMapper.map(basePriceDTO, BasePrice.class);
    }

    public BasePriceDTO convertBasePriceEntityToDto(BasePrice basePrice) {
        return this.modelMapper.map(basePrice, BasePriceDTO.class);
    }
}
