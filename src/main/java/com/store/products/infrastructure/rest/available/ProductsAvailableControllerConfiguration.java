package com.store.products.infrastructure.rest.available;

import com.store.products.application.available.ProductsAvailableHandlerConfiguration;
import com.store.products.infrastructure.rest.available.mapper.ResponseMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    ProductsAvailableHandlerConfiguration.class,
    ResponseMapper.class,
    ProductsAvailableController.class
})
public class ProductsAvailableControllerConfiguration {
}
