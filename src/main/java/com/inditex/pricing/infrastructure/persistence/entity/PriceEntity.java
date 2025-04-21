package com.inditex.pricing.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "PRICES")
@Data
public class PriceEntity {
    // We can use a serial id, an UUID or a composite key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;
    @Column(name = "BRAND_ID", nullable = false)
    private Long brandId;
    @Column(name = "PRICE_LIST", nullable = false)
    private Long priceListId;
    @Column(name = "PRICE", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @Column(name = "CURR", nullable = false, length = 3)
    private String currency;
    @Column(name = "PRIORITY", nullable = false)
    private Integer priority;
    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;
}
