package com.inditex.pricing.infrastructure.persistence.adapter;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.inditex.pricing.domain.model.BrandId;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.model.ProductId;
import com.inditex.pricing.domain.port.out.PriceRepository;
import com.inditex.pricing.infrastructure.persistence.entity.PriceEntity;
import com.inditex.pricing.infrastructure.persistence.repository.H2PriceJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepository {

    private final H2PriceJpaRepository h2PriceJpaRepository;

    public Optional<Price> findApplicablePriceWithHighestPriority(ProductId productId,
            BrandId brandId, LocalDateTime applicationDateTime) {
        return h2PriceJpaRepository
                .findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        productId.value(), brandId.value(), applicationDateTime,
                        applicationDateTime)
                .map(PriceEntity::toPrice);
    }

}
