package com.store.products.domain.availability.exception;

import com.store.products.domain.shared.exception.ProductsException;

public class InfoSourceNotFoundException extends ProductsException {
    public InfoSourceNotFoundException(String message) {
        super(message);
    }
}
