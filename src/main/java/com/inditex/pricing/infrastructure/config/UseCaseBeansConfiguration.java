package com.inditex.pricing.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.inditex.pricing.application.service.PriceQueryService;
import com.inditex.pricing.domain.port.in.PriceQueryUseCase;
import com.inditex.pricing.domain.port.out.PriceRepository;

@Configuration
public class UseCaseBeansConfiguration {

    @Bean
    public PriceQueryUseCase priceQueryUseCase(PriceRepository priceRepository) {
        return new PriceQueryService(priceRepository);
    }
}
