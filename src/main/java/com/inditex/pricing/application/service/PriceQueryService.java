package com.inditex.pricing.application.service;

import java.time.LocalDateTime;
import com.inditex.pricing.application.exception.ApplicablePriceNotFoundException;
import com.inditex.pricing.domain.model.BrandId;
import com.inditex.pricing.domain.model.PriceDetails;
import com.inditex.pricing.domain.model.ProductId;
import com.inditex.pricing.domain.port.in.PriceQueryUseCase;
import com.inditex.pricing.domain.port.out.PriceRepository;

public class PriceQueryService implements PriceQueryUseCase {

    private final PriceRepository priceRepository;

    public PriceQueryService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public PriceDetails findApplicablePrice(ProductId productId, BrandId brandId,
            LocalDateTime applicationDateTime) {
        return priceRepository
                .findApplicablePriceWithHighestPriority(productId, brandId, applicationDateTime)
                .map(PriceDetails::fromPrice)
                .orElseThrow(() -> new ApplicablePriceNotFoundException(productId.value(),
                        brandId.value(), applicationDateTime.toString()));
    }
}
