package com.store.products.infrastructure.rest.exception;

import com.store.products.domain.availability.exception.InfoSourceNotFoundException;
import com.store.products.domain.availability.exception.ProductsAvailableException;
import com.store.products.infrastructure.rest.exception.dto.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ProductsExceptionHandler {

    @ExceptionHandler(InfoSourceNotFoundException.class)
    protected ResponseEntity<ErrorMessage> infoSourceNotFoundException() {
        return ResponseEntity
            .status(NOT_FOUND)
            .body(new ErrorMessage(
                "product-info-source-not-found",
                "Products information not found"));
    }

    @ExceptionHandler(ProductsAvailableException.class)
    protected ResponseEntity<ErrorMessage> productsAvailableException() {
        return ResponseEntity
            .status(INTERNAL_SERVER_ERROR)
            .body(new ErrorMessage(
                "products-available-error",
                "Error getting the available products"));
    }
}
