package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.BasePriceDto;
import com.epam.training.ticketservice.entity.BasePrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

class BasePriceMapperTest {
    private BasePriceMapper underTest;

    @BeforeEach
    public void init() {
        this.underTest = new BasePriceMapper(new ModelMapper());
    }

    @Test
    void testConvertBasePriceEntityToDto() {
        // Given
        Long basePriceValue = 1500L;
        BasePrice basePrice = new BasePrice();
        basePrice.setBasePrice(basePriceValue);
        BasePriceDto expected = new BasePriceDto();
        expected.setBasePrice(basePriceValue);

        // When
        BasePriceDto actual = this.underTest.convertBasePriceEntityToDto(basePrice);

        // Then
        assertEquals(expected, actual);
    }
}