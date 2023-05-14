package com.store.products.application.availability;

import com.store.products.infrastructure.file.csv.availability.CsvStockRepositoryConfiguration;
import com.store.products.infrastructure.file.csv.shared.CsvProductRepositoryConfiguration;
import com.store.products.infrastructure.file.csv.shared.CsvSizeRepositoryConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    CsvProductRepositoryConfiguration.class,
    CsvSizeRepositoryConfiguration.class,
    CsvStockRepositoryConfiguration.class,
    ProductsAvailableHandler.class
})
public class ProductsAvailableHandlerConfiguration {
}
