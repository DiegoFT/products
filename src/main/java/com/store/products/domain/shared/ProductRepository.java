package com.store.products.domain.shared;

import com.store.products.domain.shared.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
}
