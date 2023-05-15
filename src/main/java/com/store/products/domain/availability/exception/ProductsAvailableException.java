package com.store.products.domain.availability.exception;

import com.store.products.domain.shared.exception.ProductsException;

public class ProductsAvailableException extends ProductsException {

    public ProductsAvailableException(String message) {
        super(message);
    }
}
