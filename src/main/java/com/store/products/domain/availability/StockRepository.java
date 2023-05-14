package com.store.products.domain.availability;

import com.store.products.domain.availability.entity.Stock;

import java.util.List;

public interface StockRepository {

    List<Stock> findAll();
}
