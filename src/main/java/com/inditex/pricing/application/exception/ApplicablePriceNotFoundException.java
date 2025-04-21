package com.inditex.pricing.application.exception;

public class ApplicablePriceNotFoundException extends RuntimeException {

    public ApplicablePriceNotFoundException(Long productId, Long brandId,
            String applicationDateTime) {
        super(String.format(
                "Precio aplicable no encontrado para el producto %d, marca %d y fecha de aplicación %s",
                productId, brandId, applicationDateTime));
    }
}
