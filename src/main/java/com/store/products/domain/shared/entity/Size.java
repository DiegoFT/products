package com.store.products.domain.shared.entity;

public record Size(String sizeId, String productId, Boolean backSoon, Boolean special) {
    public Size(String sizeId, String productId, Boolean backSoon, Boolean special) {
        this.sizeId = sizeId.replace(" ", "");
        this.productId = productId.replace(" ", "");
        this.backSoon = backSoon;
        this.special = special;
    }
}
