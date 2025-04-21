package com.inditex.pricing.application.service;

import java.time.LocalDateTime;
import java.util.Optional;
import com.inditex.pricing.application.exception.ApplicablePriceNotFoundException;
import com.inditex.pricing.domain.model.BrandId;
import com.inditex.pricing.domain.model.Price;
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

        Optional<Price> priceOptional = priceRepository
                .findApplicablePriceWithHighestPriority(productId, brandId, applicationDateTime);
        if (priceOptional.isEmpty()) {
            throw new ApplicablePriceNotFoundException(productId.value(), brandId.value(),
                    applicationDateTime.toString());
        }

        return mapToPriceDetails(priceOptional.get());
    }

    private PriceDetails mapToPriceDetails(Price price) {
        return new PriceDetails(price.getProductId(), price.getBrandId(), price.getPriceListId(),
                price.getAmount(), price.getCurrency(), price.getStartDate(), price.getEndDate());
    }
}
