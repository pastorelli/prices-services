# Busqueda de Precios de Productos
![Java](https://img.shields.io/badge/Java-21-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-green.svg)
![Maven](https://img.shields.io/badge/Maven-3.9.0-green.svg)
![Docker](https://img.shields.io/badge/Docker-blue.svg)

## Descripción

El servicio es una API RESTful que permite consultar el precio aplicable de un producto para una marca y fecha determinada.

## Funcionalidades

- **Consulta de precios**: Permite obtener el precio vigente de un producto para una marca y fecha/hora concreta.

## Arquitectura

- **Backend**: Implementado en Java con Spring Boot, siguiendo el patrón de arquitectura hexagonal (puertos y adaptadores)
- **Base de datos**: H2 en memoria, precargada con datos de ejemplo para pruebas.
- **Gestión de excepciones**: Manejo centralizado de errores.
- **Documentación**: Swagger UI disponible en `/swagger-ui.html` para explorar la API.
- **Testing**: Uso de JUnit y Mockito para pruebas unitarias y de integración, utilizando el patron de "test slices" para las componentes de persistencia y controladores, así como pruebas de integración end2end.
- **Docker**: Contenedor Docker para facilitar la ejecución y despliegue de la aplicación.

## Decisiones de diseño
- **Object Mappers**: dada la simplicidad del proyecto opté por realizar el mapeo de objetos manualmente, sin usar librerías como MapStruct o ModelMapper. En un proyecto más grande, estas librerías pueden ser útiles para reducir el código repetitivo y mejorar la mantenibilidad.
- **JPA**: Se optó por usar JPA para la persistencia de datos, lo que permite una fácil integración con bases de datos relacionales y un manejo sencillo de las entidades. También es posible usar SpringDataJdbc si las entidades y consultas son simples y no requieren de las características avanzadas de JPA.
- **Builder Pattern**: Dada la simplicidad de las entidades, no se implementó el patrón Builder. Sin embargo, en proyectos más grandes, este patrón puede ser útil para crear objetos complejos de manera más legible y mantenible. Además se podría utilizar Lombok para reducir el "boilerplate code".

## Ejemplo de uso

```
GET /api/v1/prices/priority?productId=35455&brandId=1&date=2020-06-14T10:00:00
```

Respuesta:
```json
{
  "productId": 35455,
  "brandId": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "priceListId": 1,
  "price": 35.5,
  "currencyCode": "EUR"
}
```

## Requisitos

- Java 21+
- Maven 3.9+

## Ejecución

```bash
mvn spring-boot:run
```

La API estará disponible en `http://localhost:8080`.

## Docker

Para ejecutar la aplicación en un contenedor Docker, puedes usar el siguiente comando:

```bash
docker build -t price-product-searcher .
docker run -p 8080:8080 price-product-searcher
```

La API estará disponible en `http://localhost:8080`.
---