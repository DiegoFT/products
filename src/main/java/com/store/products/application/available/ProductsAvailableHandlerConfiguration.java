package com.store.products.application.available;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    ProductsAvailableHandler.class
})
public class ProductsAvailableHandlerConfiguration {
}
