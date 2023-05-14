package com.store.products.infrastructure.file.csv.availability.mapper;

import com.store.products.domain.availability.entity.Stock;
import com.store.products.infrastructure.file.csv.utils.mapper.CsvRecordMapper;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.store.products.infrastructure.file.csv.availability.CsvStockRepository.QUANTITY;
import static com.store.products.infrastructure.file.csv.availability.CsvStockRepository.SIZE_ID;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.parseInt;

public class CsvStockMapper implements CsvRecordMapper<Stock> {
    @Override
    public Stock mapRecord(CSVRecord record) {
        var id = record.get(SIZE_ID);
        try {
            return new Stock(
                id.replace(" ", ""),
                Optional.ofNullable(record.get(QUANTITY))
                    .map(quantity -> parseInt(quantity.trim()))
                    .orElse(MAX_VALUE)
            );
        } catch (NumberFormatException e) {
            LOGGER.warn("Format not valid for Stock ID: {}. Skipping row", id);
            return null;
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvStockMapper.class);
}
