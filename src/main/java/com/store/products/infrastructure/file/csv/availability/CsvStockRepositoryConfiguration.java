package com.store.products.infrastructure.file.csv.availability;

import com.store.products.infrastructure.Properties;
import com.store.products.infrastructure.file.csv.availability.mapper.CsvStockMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    Properties.class,
    CsvStockMapper.class,
    CsvStockRepository.class
})
public class CsvStockRepositoryConfiguration {
}
