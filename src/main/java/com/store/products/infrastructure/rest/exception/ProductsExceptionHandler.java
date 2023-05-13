package com.store.products.infrastructure.rest.exception;

import com.store.products.domain.exception.available.InfoSourceNotFoundException;
import com.store.products.domain.exception.available.ProductsAvailableException;
import com.store.products.infrastructure.rest.exception.dto.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ProductsExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsExceptionHandler.class);

    @ExceptionHandler(InfoSourceNotFoundException.class)
    protected ResponseEntity<ErrorMessage> infoSourceNotFoundException() {
        LOGGER.error("Product information source not found");

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
