package com.inditex.pricing.application.exception;

public class ApplicablePriceNotFoundException extends RuntimeException {

    public ApplicablePriceNotFoundException(Long productId, Long brandId,
            String applicationDateTime) {
        super(String.format(
                "No applicable price found for productId: %s, brandId: %s, applicationDateTime: %s",
                productId, brandId, applicationDateTime));
    }
}
