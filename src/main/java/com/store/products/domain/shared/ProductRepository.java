package com.store.products.domain.shared;

import com.store.products.domain.shared.entity.Product;

import java.util.Set;

public interface ProductRepository {
    Set<Product> findAll();
}
