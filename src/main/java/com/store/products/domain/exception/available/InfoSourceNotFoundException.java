package com.store.products.domain.exception.available;

import com.store.products.domain.exception.ProductsException;

public class InfoSourceNotFoundException extends ProductsException {
    public InfoSourceNotFoundException(String message) {
        super(message);
    }
}
