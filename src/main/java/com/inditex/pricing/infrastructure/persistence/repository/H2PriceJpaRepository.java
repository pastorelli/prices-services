package com.inditex.pricing.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.inditex.pricing.infrastructure.persistence.entity.PriceEntity;

public interface H2PriceJpaRepository extends JpaRepository<PriceEntity, Long> {
    Optional<PriceEntity> findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            long productId, long brandId, LocalDateTime startDate, LocalDateTime endDate);

    // We can also implement this method using a native query
    @Query("SELECT p FROM PriceEntity p WHERE p.productId = :productId "
            + "AND p.brandId = :brandId "
            + "AND :applicationDate BETWEEN p.startDate AND p.endDate "
            + "ORDER BY p.priority DESC")
    List<PriceEntity> findApplicablePricesOrderByPriorityDesc(@Param("productId") long productId,
            @Param("brandId") long brandId,
            @Param("applicationDate") LocalDateTime applicationDate);
}
