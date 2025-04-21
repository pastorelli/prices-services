package com.inditex.pricing.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public record PriceDetails(ProductId productId, BrandId brandId, long priceListId,
        BigDecimal finalPrice, Currency currency, LocalDateTime startDate, LocalDateTime endDate) {

}
