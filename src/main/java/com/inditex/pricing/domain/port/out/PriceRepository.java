package com.inditex.pricing.domain.port.out;

import java.time.LocalDateTime;
import java.util.Optional;
import com.inditex.pricing.domain.model.BrandId;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.model.ProductId;

public interface PriceRepository {
    Optional<Price> findApplicablePriceWithHighestPriority(ProductId productId, BrandId brandId,
            LocalDateTime applicationDateTime);
}
