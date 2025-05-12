package com.inditex.pricing.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import com.inditex.pricing.domain.model.PriceDetails;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Precio aplicable")
public record PriceDetailResponse(
        @Schema(description = "Identificador del producto", example = "35455") long productId,
        @Schema(description = "Identificador de la marca", example = "1") long brandId,
        @Schema(description = "Fecha de inicio de la aplicacion",
                example = "2020-06-14T00:00:00") String startDate,
        @Schema(description = "Fecha de fin de la aplicación",
                        example = "2020-12-31T23:59:59") String endDate,
        @Schema(description = "Identificador de la lista de precios",
                example = "1") long priceListId,
        @Schema(description = "Importe", example = "35.50") BigDecimal price,
        @Schema(description = "Código ISO de la moneda", example = "EUR") String currencyCode) {


    public static PriceDetailResponse fromPriceDetails(PriceDetails priceDetails) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return new PriceDetailResponse(priceDetails.productId().value(),
                priceDetails.brandId().value(), priceDetails.startDate().format(formatter),
                priceDetails.endDate().format(formatter), priceDetails.priceListId(),
                priceDetails.finalPrice(), priceDetails.currency().getCurrencyCode());
    }
}
