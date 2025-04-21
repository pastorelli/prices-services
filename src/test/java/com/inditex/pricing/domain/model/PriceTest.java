package com.inditex.pricing.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    void testPriceConstructor() {
        ProductId productId = new ProductId(1L);
        BrandId brandId = new BrandId(1L);
        BigDecimal amount = BigDecimal.valueOf(100);
        Currency currency = Currency.getInstance("EUR");
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 12, 31, 23, 59);
        int priceListId = 1;
        int priority = 0;

        Price price = new Price(productId, brandId, startDate, endDate, priceListId, priority,
                amount, currency);

        assertEquals(productId, price.getProductId());
        assertEquals(brandId, price.getBrandId());
        assertEquals(startDate, price.getStartDate());
        assertEquals(endDate, price.getEndDate());
        assertEquals(priceListId, price.getPriceListId());
        assertEquals(priority, price.getPriority());
        assertEquals(amount, price.getAmount());
        assertEquals(currency, price.getCurrency());
    }

    @Test
    void testToString() {
        ProductId productId = new ProductId(1L);
        BrandId brandId = new BrandId(1L);
        BigDecimal amount = BigDecimal.valueOf(100.5);
        Currency currency = Currency.getInstance("EUR");
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 12, 31, 23, 59);
        int priceListId = 1;
        int priority = 0;

        Price price = new Price(productId, brandId, startDate, endDate, priceListId, priority,
                amount, currency);

        String expectedString = "Price{productId=ProductId[value=1], brandId=BrandId[value=1], "
                + "startDate=2025-01-01T00:00, endDate=2025-12-31T23:59, priceListId=1, "
                + "priority=0, amount=100.50, currency=EUR}";
        assertEquals(expectedString, price.toString());
    }
}
