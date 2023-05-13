package com.store.products.infrastructure.rest.availability.mapper;

import com.store.products.domain.shared.entity.Product;
import com.store.products.infrastructure.rest.availability.dto.Response;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ResponseMapper {

    public Response map(List<Product> products) {
        return new Response(
            products.stream()
                .sorted(Comparator.comparingInt(Product::priority))
                .map(Product::id)
                .filter(Objects::nonNull)
                .map(Integer::parseInt)
                .toList()
        );
    }
}
