package com.inditex.pricing.infrastructure.web.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.inditex.pricing.application.exception.ApplicablePriceNotFoundException;
import com.inditex.pricing.domain.model.BrandId;
import com.inditex.pricing.domain.model.PriceDetails;
import com.inditex.pricing.domain.model.ProductId;
import com.inditex.pricing.domain.port.in.PriceQueryUseCase;

@WebMvcTest(PriceSearcherController.class)
public class PriceSearcherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PriceQueryUseCase priceQueryUseCase;

    private final static String PRICES_URL = "/api/v1/prices/priority";

    private String applicationDateString;
    private String productIdString;
    private String brandIdString;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;

    @BeforeEach
    public void setUp() {
        applicationDateString = "2020-06-14T10:00:00";
        productIdString = "35455";
        brandIdString = "1";
    }

    @Test
    @DisplayName("Get price by product id, brand id and application date")
    void getPriceByProductIdAndBrandIdAndApplicationDate() throws Exception {
        String url = String.format("%s?productId=%s&brandId=%s&applicationDate=%s", PRICES_URL,
                productIdString, brandIdString, applicationDateString);
        PriceDetails priceDetails = applicablePrice();
        when(priceQueryUseCase.findApplicablePrice(new ProductId(35455L), new BrandId(1L),
                LocalDateTime.parse(applicationDateString, dateTimeFormatter)))
                        .thenReturn(priceDetails);

        mockMvc.perform(get(url)).andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455L))
                .andExpect(jsonPath("$.brandId").value(1L))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.priceListId").value(1L))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currencyCode").value("EUR"));
    }

    @Test
    @DisplayName("Get price by product id, brand id and application date - Not Found")
    void getPriceByProductIdAndBrandIdAndApplicationDateNotFound() throws Exception {
        String url = String.format("%s?productId=%s&brandId=%s&applicationDate=%s", PRICES_URL,
                productIdString, brandIdString, applicationDateString);
        ProductId productId = new ProductId(35455L);
        BrandId brandId = new BrandId(1L);
        LocalDateTime applicationDate =
                LocalDateTime.parse(applicationDateString, dateTimeFormatter);
        when(priceQueryUseCase.findApplicablePrice(productId, brandId, applicationDate))
                .thenThrow(new ApplicablePriceNotFoundException(productId.value(), brandId.value(),
                        applicationDateString));


        mockMvc.perform(get(url)).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Precio aplicable no encontrado"))
                .andExpect(jsonPath("$.status").value(404)).andExpect(jsonPath("$.detail").value(
                        "Precio aplicable no encontrado para el producto 35455, marca 1 y fecha de aplicación 2020-06-14T10:00:00"));
    }

    @Test
    @DisplayName("Get price by product id, brand id and application date - Bad Request - Missing parameters")
    void getPriceByProductIdAndBrandIdAndApplicationDateBadRequestMissingParameters()
            throws Exception {
        String url = String.format("%s?productId=%s&brandId=%s", PRICES_URL, productIdString,
                brandIdString);
        mockMvc.perform(get(url)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Parámetro de solicitud faltante"))
                .andExpect(jsonPath("$.status").value(400)).andExpect(jsonPath("$.detail").value(
                        "El parámetro 'applicationDate' es obligatorio y no se ha proporcionado"));
    }

    @Test
    @DisplayName("Get price by product id, brand id and application date - Bad Request - Invalid date format")
    void getPriceByProductIdAndBrandIdAndApplicationDateBadRequest() throws Exception {
        String url = String.format("%s?productId=%s&brandId=%s&applicationDate=%s", PRICES_URL,
                productIdString, brandIdString, "invalid-date");
        mockMvc.perform(get(url)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Error de tipo de argumento del método"))
                .andExpect(jsonPath("$.status").value(400)).andExpect(jsonPath("$.detail").value(
                        "El parámetro 'applicationDate' debe ser de tipo 'LocalDateTime' pero se recibió 'invalid-date'"));
    }

    @Test
    @DisplayName("Get price by product id, brand id and application date - Internal Server Error")
    void getPriceByProductIdAndBrandIdAndApplicationDateInternalServerError() throws Exception {
        String url = String.format("%s?productId=%s&brandId=%s&applicationDate=%s", PRICES_URL,
                productIdString, brandIdString, applicationDateString);
        when(priceQueryUseCase.findApplicablePrice(new ProductId(35455L), new BrandId(1L),
                LocalDateTime.parse(applicationDateString, dateTimeFormatter)))
                        .thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(get(url)).andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.title").value("Error interno del servidor"))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.detail").value("Internal server error"));
    }

    private PriceDetails applicablePrice() {
        return new PriceDetails(new ProductId(35455L), new BrandId(1L), 1L,
                BigDecimal.valueOf(35.50), Currency.getInstance("EUR"),
                LocalDateTime.of(2020, 6, 14, 0, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59));
    }
}
