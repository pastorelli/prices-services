package com.inditex.pricing.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import org.junit.jupiter.api.Test;

public class PriceDetailsTest {

    @Test
    void testCreatePriceDetails() {
        ProductId productId = new ProductId(1L);
        BrandId brandId = new BrandId(1L);
        long priceList = 1L;
        BigDecimal finalPrice = BigDecimal.valueOf(100.0);
        Currency currency = Currency.getInstance("EUR");
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 12, 31, 23, 59);

        PriceDetails priceDetails = new PriceDetails(productId, brandId, priceList, finalPrice,
                currency, startDate, endDate);

        assertEquals(productId, priceDetails.productId());
        assertEquals(brandId, priceDetails.brandId());
        assertEquals(priceList, priceDetails.priceListId());
        assertEquals(finalPrice, priceDetails.finalPrice());
        assertEquals(currency, priceDetails.currency());
        assertEquals(startDate, priceDetails.startDate());
        assertEquals(endDate, priceDetails.endDate());
    }
}
