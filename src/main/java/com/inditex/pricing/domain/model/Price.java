package com.inditex.pricing.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public class Price {
    private ProductId productId;
    private BrandId brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long priceListId;
    private int priority;
    private BigDecimal amount;
    private Currency currency;

    public Price(ProductId productId, BrandId brandId, LocalDateTime startDate,
            LocalDateTime endDate, long priceListId, int priority, BigDecimal amount,
            Currency currency) {
        this.productId = productId;
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceListId = priceListId;
        this.priority = priority;
        this.amount = amount;
        this.currency = currency;
    }

    public ProductId getProductId() {
        return productId;
    }

    public BrandId getBrandId() {
        return brandId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public long getPriceListId() {
        return priceListId;
    }

    public int getPriority() {
        return priority;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Price{" + "productId=" + productId + ", brandId=" + brandId + ", startDate="
                + startDate + ", endDate=" + endDate + ", priceListId=" + priceListId
                + ", priority=" + priority + ", amount=" + String.format("%.2f", amount)
                + ", currency=" + currency + '}';
    }
}
