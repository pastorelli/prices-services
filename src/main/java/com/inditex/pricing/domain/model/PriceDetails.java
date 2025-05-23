package com.inditex.pricing.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public record PriceDetails(ProductId productId, BrandId brandId, long priceListId,
        BigDecimal finalPrice, Currency currency, LocalDateTime startDate, LocalDateTime endDate) {

    public static PriceDetails fromPrice(Price price) {
        return new PriceDetails(price.productId(), price.brandId(), price.priceListId(),
                price.amount(), price.currency(), price.startDate(), price.endDate());
    }
}
