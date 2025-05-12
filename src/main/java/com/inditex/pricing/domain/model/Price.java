package com.inditex.pricing.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public record Price(ProductId productId, BrandId brandId, LocalDateTime startDate,
        LocalDateTime endDate, long priceListId, int priority, BigDecimal amount,
        Currency currency) {
}
