package com.store.products.infrastructure.rest.availability;

import com.store.products.application.availability.ProductsAvailableHandlerConfiguration;
import com.store.products.infrastructure.rest.availability.mapper.ResponseMapper;
import com.store.products.infrastructure.rest.exception.ProductsExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    ProductsExceptionHandler.class,
    ProductsAvailableHandlerConfiguration.class,
    ResponseMapper.class,
    ProductsAvailableController.class
})
public class ProductsAvailableControllerConfiguration {
}
