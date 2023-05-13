package com.store.products.domain.exception.availability;

import com.store.products.domain.exception.ProductsException;

public class ProductsAvailableException extends ProductsException {

    public ProductsAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductsAvailableException(String message) {
        super(message);
    }
}
