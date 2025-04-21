package com.inditex.pricing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "API Buscador de Precios", version = "1.0.0",
                description = "API para consultar precios aplicables de productos."),
        servers = {@Server(url = "http://localhost:8080", description = "Local server")})
public class ProductPriceSearcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductPriceSearcherApplication.class, args);
    }

}
