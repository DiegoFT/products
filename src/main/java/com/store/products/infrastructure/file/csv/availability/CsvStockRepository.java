package com.store.products.infrastructure.file.csv.availability;

import com.store.products.domain.availability.StockRepository;
import com.store.products.domain.availability.entity.Stock;
import com.store.products.infrastructure.Properties;
import com.store.products.infrastructure.file.csv.availability.exception.CsvException;
import com.store.products.infrastructure.file.csv.availability.mapper.CsvStockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.store.products.infrastructure.file.csv.utils.CsvReaderUtils.readCsv;

public class CsvStockRepository implements StockRepository {

    private final CsvStockMapper mapper;

    private final Properties properties;

    public CsvStockRepository(CsvStockMapper mapper, Properties properties) {
        this.mapper = mapper;
        this.properties = properties;
    }

    @Override
    public List<Stock> findAll() {
        var filePath = properties.getStockFilePath();

        try {
            String[] headers = {SIZE_ID, QUANTITY};
            LOGGER.info("Reading the Stock CSV file: {}", filePath);

            return readCsv(filePath, headers).stream()
                .map(mapper::mapRecord)
                .filter(Objects::nonNull).toList();
        } catch (IOException e) {
            LOGGER.error("Error reading the Stock file: {}", filePath);

            throw new CsvException("Error reading the Stock file: " + filePath, e);
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvStockRepository.class);
    public static final String SIZE_ID = "sizeId";
    public static final String QUANTITY = "quantity";
}
