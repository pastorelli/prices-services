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

        assertEquals(productId, price.productId());
        assertEquals(brandId, price.brandId());
        assertEquals(startDate, price.startDate());
        assertEquals(endDate, price.endDate());
        assertEquals(priceListId, price.priceListId());
        assertEquals(priority, price.priority());
        assertEquals(amount, price.amount());
        assertEquals(currency, price.currency());
    }
}
