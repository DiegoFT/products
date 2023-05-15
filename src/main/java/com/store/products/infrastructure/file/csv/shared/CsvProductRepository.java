package com.store.products.infrastructure.file.csv.shared;

import com.store.products.domain.shared.ProductRepository;
import com.store.products.domain.shared.entity.Product;
import com.store.products.infrastructure.Properties;
import com.store.products.infrastructure.file.csv.availability.exception.CsvException;
import com.store.products.infrastructure.file.csv.shared.mapper.CsvProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.store.products.infrastructure.file.csv.utils.CsvReaderUtils.readCsv;

public class CsvProductRepository implements ProductRepository {

    private final CsvProductMapper mapper;

    private final Properties properties;

    public CsvProductRepository(CsvProductMapper mapper, Properties properties) {
        this.mapper = mapper;
        this.properties = properties;
    }

    @Override
    public Set<Product> findAll() {
        var filePath = properties.getProductFilePath();

        try {
            String[] headers = {ID, SEQUENCE};
            LOGGER.info("Reading the Product CSV file: {}", filePath);

            return readCsv(filePath, headers).stream()
                .map(mapper::mapRecord)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        } catch (IOException e) {
            LOGGER.error("Error reading the Product file: {}", filePath);

            throw new CsvException("Error reading the Product file: " + filePath, e);
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvProductRepository.class);
    public static final String ID = "id";
    public static final String SEQUENCE = "sequence";
}
