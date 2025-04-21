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

        assertEquals(expectedPrice.getProductId(), priceDetails.productId());
        assertEquals(expectedPrice.getBrandId(), priceDetails.brandId());
        assertEquals(expectedPrice.getPriceListId(), priceDetails.priceListId());
        assertEquals(expectedPrice.getAmount(), priceDetails.finalPrice());
        assertEquals(expectedPrice.getCurrency(), priceDetails.currency());
        assertEquals(expectedPrice.getStartDate(), priceDetails.startDate());
        assertEquals(expectedPrice.getEndDate(), priceDetails.endDate());
    }

    @Test
    void shouldReturnApplicablePriceNotFoundException() {
        when(priceRepository.findApplicablePriceWithHighestPriority(productId, brandId,
                applicationDateTime)).thenReturn(Optional.empty());

        ApplicablePriceNotFoundException exception =
                assertThrows(ApplicablePriceNotFoundException.class, () -> {
            priceQueryService.findApplicablePrice(productId, brandId, applicationDateTime);
        });
                assertEquals(
                "No applicable price found for productId: 12345, brandId: 1, applicationDateTime: 2025-04-20T10:00",
                exception.getMessage());
    }

    private Price applicablePrice() {
        return new Price(productId, brandId, applicationDateTime.minusDays(1),
                applicationDateTime.plusDays(1), 1L, 1, BigDecimal.valueOf(100),
                Currency.getInstance("EUR"));
    }
}
