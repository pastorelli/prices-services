package com.inditex.pricing.end2end;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import com.inditex.pricing.infrastructure.web.dto.PriceDetailResponse;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductPriceSearcherEnd2EndTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/api/v1/prices";
    private static final String PRODUCT_ID = "35455";
    private static final String BRAND_ID = "1";
    private static final String APPLICATION_DATE = "2020-06-14T10:00:00";

    @Test
    @DisplayName("Get price by productId, brandId and date -- Test 1")
    void findApplicablePriceByProductIdBrandIdAndDate() {
        String url = getUrl(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);

        var response = restTemplate.getForEntity(url, PriceDetailResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        var priceDetailResponse = response.getBody();
        assertNotNull(priceDetailResponse);
        assertEquals(35455, priceDetailResponse.productId());
        assertEquals(1, priceDetailResponse.brandId());
        assertEquals("2020-06-14T00:00:00", priceDetailResponse.startDate());
        assertEquals("2020-12-31T23:59:59", priceDetailResponse.endDate());
        assertEquals(1, priceDetailResponse.priceListId());
        assertEquals(new BigDecimal("35.50"), priceDetailResponse.price());
        assertEquals("EUR", priceDetailResponse.currencyCode());
    }

    @Test
    @DisplayName("Get price by productId, brandId and date -- Test 2")
    void findApplicablePriceByProductIdBrandIdAndDateTest2() {
        String url = getUrl(PRODUCT_ID, BRAND_ID, "2020-06-14T16:00:00");

        var response = restTemplate.getForEntity(url, PriceDetailResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        var priceDetailResponse = response.getBody();
        assertNotNull(priceDetailResponse);
        assertEquals(35455, priceDetailResponse.productId());
        assertEquals(1, priceDetailResponse.brandId());
        assertEquals("2020-06-14T15:00:00", priceDetailResponse.startDate());
        assertEquals("2020-06-14T18:30:00", priceDetailResponse.endDate());
        assertEquals(2, priceDetailResponse.priceListId());
        assertEquals(new BigDecimal("25.45"), priceDetailResponse.price());
        assertEquals("EUR", priceDetailResponse.currencyCode());
    }

    @Test
    @DisplayName("Get price by productId, brandId and date -- Test 3")
    void findApplicablePriceByProductIdBrandIdAndDateTest3() {
        String url = getUrl(PRODUCT_ID, BRAND_ID, "2020-06-14T21:00:00");

        var response = restTemplate.getForEntity(url, PriceDetailResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        var priceDetailResponse = response.getBody();
        assertNotNull(priceDetailResponse);
        assertEquals(35455, priceDetailResponse.productId());
        assertEquals(1, priceDetailResponse.brandId());
        assertEquals("2020-06-14T00:00:00", priceDetailResponse.startDate());
        assertEquals("2020-12-31T23:59:59", priceDetailResponse.endDate());
        assertEquals(1, priceDetailResponse.priceListId());
        assertEquals(new BigDecimal("35.50"), priceDetailResponse.price());
        assertEquals("EUR", priceDetailResponse.currencyCode());
    }

    @Test
    @DisplayName("Get price by productId, brandId and date -- Test 4")
    void findApplicablePriceByProductIdBrandIdAndDateTest4() {
        String url = getUrl(PRODUCT_ID, BRAND_ID, "2020-06-15T10:00:00");

        var response = restTemplate.getForEntity(url, PriceDetailResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        var priceDetailResponse = response.getBody();
        assertNotNull(priceDetailResponse);
        assertEquals(35455, priceDetailResponse.productId());
        assertEquals(1, priceDetailResponse.brandId());
        assertEquals("2020-06-15T00:00:00", priceDetailResponse.startDate());
        assertEquals("2020-06-15T11:00:00", priceDetailResponse.endDate());
        assertEquals(3, priceDetailResponse.priceListId());
        assertEquals(new BigDecimal("30.50"), priceDetailResponse.price());
        assertEquals("EUR", priceDetailResponse.currencyCode());
    }

    @Test
    @DisplayName("Get price by productId, brandId and date -- Test 5")
    void findApplicablePriceByProductIdBrandIdAndDateTest5() {
        String url = getUrl(PRODUCT_ID, BRAND_ID, "2020-06-16T21:00:00");

        var response = restTemplate.getForEntity(url, PriceDetailResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        var priceDetailResponse = response.getBody();
        assertNotNull(priceDetailResponse);
        assertEquals(35455, priceDetailResponse.productId());
        assertEquals(1, priceDetailResponse.brandId());
        assertEquals("2020-06-15T16:00:00", priceDetailResponse.startDate());
        assertEquals("2020-12-31T23:59:59", priceDetailResponse.endDate());
        assertEquals(4, priceDetailResponse.priceListId());
        assertEquals(new BigDecimal("38.95"), priceDetailResponse.price());
        assertEquals("EUR", priceDetailResponse.currencyCode());
    }

    @Test
    @DisplayName("Get price by productId, brandId and date -- Not Found")
    void findApplicablePriceByProductIdBrandIdAndDateNotFound() {
        String url = getUrl("123", "456", "2020-06-14T10:00:00");

        var response = restTemplate.getForEntity(url, PriceDetailResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        var priceDetailResponse = response.getBody();
        assertNotNull(priceDetailResponse);
    }

    private String getUrl(String productId, String brandId, String date) {
        return BASE_URL + "/priority?productId=" + productId + "&brandId=" + brandId
                + "&applicationDate=" + date;
    }
}
