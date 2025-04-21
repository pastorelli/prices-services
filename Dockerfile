# Basic Dockerfile for a Spring Boot application using Maven
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /workspace

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN ./mvnw dependency:go-offline

COPY src src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /workspace/target/*.jar prices-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "prices-service.jar"]