package com.inditex.pricing;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductPriceSearcherApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> ProductPriceSearcherApplication.main(new String[] {}));
    }

}
