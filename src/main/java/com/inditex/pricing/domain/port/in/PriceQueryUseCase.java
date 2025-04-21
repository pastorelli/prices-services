package com.inditex.pricing.domain.port.in;

import java.time.LocalDateTime;
import com.inditex.pricing.domain.model.BrandId;
import com.inditex.pricing.domain.model.PriceDetails;
import com.inditex.pricing.domain.model.ProductId;

public interface PriceQueryUseCase {
    PriceDetails findApplicablePrice(ProductId productId, BrandId brandId,
                    LocalDateTime applicationDateTime);
}
