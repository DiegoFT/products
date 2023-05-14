package com.store.products.infrastructure.file.csv.shared;

import com.store.products.infrastructure.Properties;
import com.store.products.infrastructure.file.csv.shared.mapper.CsvProductMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    Properties.class,
    CsvProductMapper.class,
    CsvProductRepository.class
})
public class CsvProductRepositoryConfiguration {
}
