package com.epam.training.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceComponentDto {
    private String componentName;
    private Long componentPrice;
}
