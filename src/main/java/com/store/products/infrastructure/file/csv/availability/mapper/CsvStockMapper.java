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
    public Stock mapRecord(CSVRecord csvRecord) {
        var sizeId = csvRecord.get(SIZE_ID);
        try {
            return Optional.ofNullable(sizeId)
                .filter(id -> !id.isEmpty())
                .map(id -> getStock(csvRecord, id))
                .orElse(null);
        } catch (NumberFormatException e) {
            LOGGER.warn("Format not valid for Stock ID: {}. Skipping row", sizeId);
            return null;
        }
    }

    private static Stock getStock(CSVRecord csvRecord, String id) {
        return new Stock(
            id,
            Optional.ofNullable(csvRecord.get(QUANTITY))
                .map(quantity -> parseInt(quantity.trim()))
                .orElse(MAX_VALUE)
        );
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvStockMapper.class);
}
