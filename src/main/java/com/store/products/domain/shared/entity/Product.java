package com.store.products.domain.shared.entity;

public record Product(String id, Integer priority) {
    public Product(String id, Integer priority) {
        this.id = id.replace(" ", "");
        this.priority = priority;
    }
}
