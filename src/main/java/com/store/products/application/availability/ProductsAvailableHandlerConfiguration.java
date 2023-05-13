package com.store.products.application.availability;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    ProductsAvailableHandler.class
})
public class ProductsAvailableHandlerConfiguration {
}
