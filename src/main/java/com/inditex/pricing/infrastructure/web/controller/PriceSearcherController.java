package com.inditex.pricing.infrastructure.web.controller;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.inditex.pricing.domain.model.BrandId;
import com.inditex.pricing.domain.model.PriceDetails;
import com.inditex.pricing.domain.model.ProductId;
import com.inditex.pricing.domain.port.in.PriceQueryUseCase;
import com.inditex.pricing.infrastructure.web.dto.PriceDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/prices") // this can be handled by constants
@RequiredArgsConstructor
@Tag(name = "Price Searcher", description = "Price Searcher API")
public class PriceSearcherController {

    private final PriceQueryUseCase priceQueryUseCase;


    @Operation(summary = "Get price by product id, brand id and application date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Precio aplicable encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PriceDetailResponse.class))),
            @ApiResponse(responseCode = "404", description = "Precio aplicable no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "400", description = "Error en la peticion",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))})
    @GetMapping("/priority")
    public PriceDetailResponse getPriceByProductIdAndBrandIdAndApplicationDate(
            @Parameter(description = "Identificador del producto",
                    example = "35455") @RequestParam("productId") Long productId,
            @Parameter(description = "Identificador de la marca",
                    example = "1") @RequestParam("brandId") Long brandId,
            @Parameter(description = "Fecha de inicio de la aplicacion",
                    example = "2020-06-14T00:00:00") @RequestParam("applicationDate") @DateTimeFormat(
                            iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate) {
        PriceDetails priceDetails = priceQueryUseCase.findApplicablePrice(new ProductId(productId),
                new BrandId(brandId), applicationDate);

        return PriceDetailResponse.fromPriceDetails(priceDetails);
    }
}
