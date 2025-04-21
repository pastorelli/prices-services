package com.inditex.pricing.infrastructure.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.inditex.pricing.application.exception.ApplicablePriceNotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ProductPriceSearcherRestControllerAdvice {

    @ExceptionHandler(ApplicablePriceNotFoundException.class)
    public ProblemDetail handleApplicablePriceNotFoundException(
            ApplicablePriceNotFoundException exception) {
        log.warn("Applicable price not found: {}", exception.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Precio aplicable no encontrado");
        problemDetail.setDetail(exception.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ProblemDetail handleMissingServletRequestParameterException(
            MissingServletRequestParameterException exception) {
        log.warn("Missing request parameter: {}", exception.getMessage());
        String errorMessage =
                String.format("El parámetro '%s' es obligatorio y no se ha proporcionado",
                        exception.getParameterName());
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Parámetro de solicitud faltante");
        problemDetail.setDetail(errorMessage);
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.warn("MethodArgumentTypeMismatchException handled: {}", ex.getMessage());
        String parameterName = ex.getParameter().getParameterName();
        String parameterType = ex.getParameter().getParameterType().getSimpleName();
        String errorMessage =
                String.format("El parámetro '%s' debe ser de tipo '%s' pero se recibió '%s'",
                        parameterName, parameterType, ex.getValue());
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Error de tipo de argumento del método");
        problemDetail.setDetail(errorMessage);
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception exception) {
        log.error("Internal server error: {}", exception.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Error interno del servidor");
        problemDetail.setDetail(exception.getMessage());
        return problemDetail;
    }
}
