package com.store.products.domain.availability;

import com.store.products.domain.availability.entity.Stock;

import java.util.Set;

public interface StockRepository {

    Set<Stock> findAll();
}
