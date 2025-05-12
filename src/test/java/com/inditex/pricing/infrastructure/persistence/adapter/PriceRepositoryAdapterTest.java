package com.inditex.pricing.infrastructure.persistence.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import com.inditex.pricing.domain.model.BrandId;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.model.ProductId;

@DataJpaTest
@Import(PriceRepositoryAdapter.class)
@Sql(scripts = {"classpath:db/data.sql"})
public class PriceRepositoryAdapterTest {

    @Autowired
    private PriceRepositoryAdapter priceRepositoryAdapter;

    private ProductId productId;
    private BrandId brandId;
    private LocalDateTime applicationDateTime;

    @BeforeEach
    void setUp() {
        productId = new ProductId(35455);
        brandId = new BrandId(1);
        applicationDateTime = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
    }

    @Test
    void testFindApplicablePriceWithHighestPriority() {
        Optional<Price> resultPrice = priceRepositoryAdapter
                .findApplicablePriceWithHighestPriority(productId, brandId, applicationDateTime);

        assertTrue(resultPrice.isPresent(), "Applicable price should be present");
        Price price = resultPrice.get();
        assertEquals(productId, price.productId());
        assertEquals(brandId, price.brandId());
        assertEquals(LocalDateTime.of(2020, 6, 14, 0, 0, 0), price.startDate());
        assertEquals(LocalDateTime.of(2020, 12, 31, 23, 59, 59), price.endDate());
        assertEquals(1L, price.priceListId());
        assertEquals(0, price.priority());
        assertEquals(new BigDecimal("35.50"), price.amount());
        assertEquals("EUR", price.currency().getCurrencyCode());
    }

    @Test
    void testFindApplicablePriceWithHighestPriorityAndPriorityOfOne() {
        applicationDateTime = LocalDateTime.of(2020, 6, 14, 16, 0, 0);
        Optional<Price> resultPrice = priceRepositoryAdapter
                .findApplicablePriceWithHighestPriority(productId, brandId, applicationDateTime);

        assertTrue(resultPrice.isPresent(), "Applicable price should be present");
        Price price = resultPrice.get();
        assertEquals(productId, price.productId());
        assertEquals(brandId, price.brandId());
        assertEquals(LocalDateTime.of(2020, 6, 14, 15, 0, 0), price.startDate());
        assertEquals(LocalDateTime.of(2020, 6, 14, 18, 30, 0), price.endDate());
        assertEquals(2L, price.priceListId());
        assertEquals(1, price.priority());
        assertEquals(new BigDecimal("25.45"), price.amount());
        assertEquals("EUR", price.currency().getCurrencyCode());
    }

    @Test
    void testFindApplicablePriceWithHighestPriorityExactlyStartDate() {
        applicationDateTime = LocalDateTime.of(2020, 6, 15, 0, 0, 0);
        Optional<Price> resultPrice = priceRepositoryAdapter
                .findApplicablePriceWithHighestPriority(productId, brandId, applicationDateTime);

        assertTrue(resultPrice.isPresent(), "Applicable price should be present");
        Price price = resultPrice.get();
        assertEquals(productId, price.productId());
        assertEquals(brandId, price.brandId());
        assertEquals(LocalDateTime.of(2020, 6, 15, 0, 0, 0), price.startDate());
        assertEquals(LocalDateTime.of(2020, 6, 15, 11, 0, 0), price.endDate());
        assertEquals(3L, price.priceListId());
        assertEquals(1, price.priority());
        assertEquals(new BigDecimal("30.50"), price.amount());
        assertEquals("EUR", price.currency().getCurrencyCode());
    }

    @Test
    void testFindApplicablePriceWithHighestPriorityExactlyEndDate() {
        applicationDateTime = LocalDateTime.of(2020, 6, 15, 11, 0, 0);
        Optional<Price> resultPrice = priceRepositoryAdapter
                .findApplicablePriceWithHighestPriority(productId, brandId, applicationDateTime);

        assertTrue(resultPrice.isPresent(), "Applicable price should be present");
        Price price = resultPrice.get();
        assertEquals(productId, price.productId());
        assertEquals(brandId, price.brandId());
        assertEquals(LocalDateTime.of(2020, 6, 15, 0, 0, 0), price.startDate());
        assertEquals(LocalDateTime.of(2020, 6, 15, 11, 0, 0), price.endDate());
        assertEquals(3L, price.priceListId());
        assertEquals(1, price.priority());
        assertEquals(new BigDecimal("30.50"), price.amount());
        assertEquals("EUR", price.currency().getCurrencyCode());
    }

    @Test
    void testFindApplicablePriceWithHighestPriorityNoResult() {
        applicationDateTime = LocalDateTime.of(2021, 6, 14, 21, 0, 0);
        Optional<Price> resultPrice = priceRepositoryAdapter
                .findApplicablePriceWithHighestPriority(productId, brandId, applicationDateTime);

        assertTrue(resultPrice.isEmpty(), "Applicable price should not be present");
    }

    @Test
    void testFindApplicablePriceWithHighestPriorityNoResultWithInvalidProductId() {
        ProductId differentProductId = new ProductId(123);
        Optional<Price> resultPrice = priceRepositoryAdapter.findApplicablePriceWithHighestPriority(
                differentProductId, brandId, applicationDateTime);

        assertTrue(resultPrice.isEmpty(), "Applicable price should not be present");
    }

    @Test
    void testFindApplicablePriceWithHighestPriorityNoResultWithInvalidBrandId() {
        BrandId differentBrandId = new BrandId(3);
        Optional<Price> resultPrice = priceRepositoryAdapter.findApplicablePriceWithHighestPriority(
                productId, differentBrandId, applicationDateTime);

        assertTrue(resultPrice.isEmpty(), "Applicable price should not be present");
    }

    @Test
    void testFindApplicablePriceWithHighestPriorityExistsForDifferentBrandId() {
        BrandId differentBrandId = new BrandId(2);
        Optional<Price> resultPrice = priceRepositoryAdapter.findApplicablePriceWithHighestPriority(
                productId, differentBrandId, applicationDateTime);

        assertTrue(resultPrice.isPresent(), "Applicable price should be present");
        Price price = resultPrice.get();
        assertEquals(productId, price.productId());
        assertEquals(differentBrandId, price.brandId());
        assertEquals(LocalDateTime.of(2020, 1, 1, 0, 0, 0), price.startDate());
        assertEquals(LocalDateTime.of(2020, 12, 31, 23, 59, 59), price.endDate());
        assertEquals(1L, price.priceListId());
        assertEquals(0, price.priority());
        assertEquals(new BigDecimal("100.50"), price.amount());
        assertEquals("EUR", price.currency().getCurrencyCode());
    }
}
