package com.inditex.pricing.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.inditex.pricing.application.exception.ApplicablePriceNotFoundException;
import com.inditex.pricing.domain.model.BrandId;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.model.PriceDetails;
import com.inditex.pricing.domain.model.ProductId;
import com.inditex.pricing.domain.port.out.PriceRepository;

@ExtendWith(MockitoExtension.class)
public class PriceQueryServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceQueryService priceQueryService;

    private ProductId productId;
    private BrandId brandId;
    private LocalDateTime applicationDateTime;

    @BeforeEach
    public void setUp() {
        productId = new ProductId(12345L);
        brandId = new BrandId(1L);
        applicationDateTime = LocalDateTime.of(2025, 4, 20, 10, 0, 0);
    }

    @Test
    void shouldFindApplicablePrice() {
        Price expectedPrice = applicablePrice();
        when(priceRepository.findApplicablePriceWithHighestPriority(productId, brandId,
                applicationDateTime)).thenReturn(Optional.of(expectedPrice));

        PriceDetails priceDetails =
                priceQueryService.findApplicablePrice(productId, brandId, applicationDateTime);

        assertEquals(expectedPrice.productId(), priceDetails.productId());
        assertEquals(expectedPrice.brandId(), priceDetails.brandId());
        assertEquals(expectedPrice.priceListId(), priceDetails.priceListId());
        assertEquals(expectedPrice.amount(), priceDetails.finalPrice());
        assertEquals(expectedPrice.currency(), priceDetails.currency());
        assertEquals(expectedPrice.startDate(), priceDetails.startDate());
        assertEquals(expectedPrice.endDate(), priceDetails.endDate());
    }

    @Test
    void shouldReturnApplicablePriceNotFoundException() {
        when(priceRepository.findApplicablePriceWithHighestPriority(productId, brandId,
                applicationDateTime)).thenReturn(Optional.empty());

        ApplicablePriceNotFoundException exception =
                assertThrows(ApplicablePriceNotFoundException.class, () -> {
                    priceQueryService.findApplicablePrice(productId, brandId, applicationDateTime);
                });
        assertEquals(String.format(
                "Precio aplicable no encontrado para el producto %d, marca %d y fecha de aplicación %s",
                productId.value(), brandId.value(), applicationDateTime), exception.getMessage());
    }

    private Price applicablePrice() {
        return new Price(productId, brandId, applicationDateTime.minusDays(1),
                applicationDateTime.plusDays(1), 1L, 1, BigDecimal.valueOf(100),
                Currency.getInstance("EUR"));
    }
}
