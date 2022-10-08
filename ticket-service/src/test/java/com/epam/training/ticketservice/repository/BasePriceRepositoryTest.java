package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entity.BasePrice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BasePriceRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private BasePriceRepository basePriceRepository;

    @Test
    public void injectedComponentsAreNotNull(){
        assertThat(this.entityManager).isNotNull();
        assertThat(this.basePriceRepository).isNotNull();
    }

    @Test
    public void testFindAllShouldBeEmptyWhenCalled() {
        // Given

        // When
        List<BasePrice> basePrices = this.basePriceRepository.findAll();

        // Then
        assertThat(basePrices).isEmpty();
    }

    @Test
    public void testSave() {
        // Given
        BasePrice basePrice = new BasePrice();
        Long basePriceValue = 1500L;
        basePrice.setBasePrice(basePriceValue);

        // When
        BasePrice savedEntity = this.basePriceRepository.save(basePrice);

        // Then
        assertThat(savedEntity).hasFieldOrProperty("id");
        assertThat(savedEntity).hasFieldOrPropertyWithValue("basePrice", 1500L);
    }

    @Test
    public void testFindAllShouldReturnAllBasePricesWhenCalled() {
        // Given
        BasePrice basePrice1 = new BasePrice();
        basePrice1.setBasePrice(2000L);
        BasePrice basePrice2 = new BasePrice();
        basePrice1.setBasePrice(1000L);

        // When
        this.entityManager.persist(basePrice1);
        this.entityManager.persist(basePrice2);
        List<BasePrice> basePrices = this.basePriceRepository.findAll();

        // Then
        assertThat(basePrices)
                .hasSize(2)
                .contains(basePrice1, basePrice2);
    }

    @Test
    public void testFindByIdShouldReturnEntityWhenCalled() {
        // Given
        BasePrice basePrice1 = new BasePrice();
        basePrice1.setBasePrice(1500L);
        BasePrice basePrice2 = new BasePrice();
        basePrice2.setBasePrice(2000L);

        // When
        this.entityManager.persist(basePrice1);
        this.entityManager.persist(basePrice2);
        BasePrice foundBasePrice = this.basePriceRepository
                .findById(basePrice1.getId())
                .get();

        // Then
        assertThat(foundBasePrice)
                .isEqualTo(basePrice1);
    }
}
