package com.store.products.infrastructure.file.csv.shared;

import com.store.products.infrastructure.Properties;
import com.store.products.infrastructure.file.csv.shared.mapper.CsvSizeMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    Properties.class,
    CsvSizeMapper.class,
    CsvSizeRepository.class
})
public class CsvSizeRepositoryConfiguration {
}
