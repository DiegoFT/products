package com.store.products.domain.availability.entity;

public record Stock(String sizeId, Integer quantity) {
    public Stock(String sizeId, Integer quantity) {
        this.sizeId = sizeId.replace(" ", "");
        this.quantity = quantity;
    }
}
