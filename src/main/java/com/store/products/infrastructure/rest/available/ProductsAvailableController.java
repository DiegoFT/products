package com.store.products.infrastructure.rest.available;

import com.store.products.application.available.ProductsAvailableHandler;
import com.store.products.infrastructure.rest.available.dto.Response;
import com.store.products.infrastructure.rest.available.mapper.ResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProductsAvailableController implements ProductsAvailableAPI {

    private final ProductsAvailableHandler handler;
    private final ResponseMapper responseMapper;

    @Override
    @GetMapping("/products/available")
    public Response invoke() {
        var products = handler.handle();

        return responseMapper.map(products);
    }

}
