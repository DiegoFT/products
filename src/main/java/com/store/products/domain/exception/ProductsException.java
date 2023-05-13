package com.store.products.domain.exception;

public class ProductsException extends RuntimeException {

    public ProductsException() {
        super();
    }

    public ProductsException(String message) {
        super(message);
    }

    public ProductsException(String message, Throwable cause) {
        super(message, cause);
    }
}
