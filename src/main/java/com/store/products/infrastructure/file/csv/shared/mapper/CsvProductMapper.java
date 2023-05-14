package com.store.products.infrastructure.file.csv.shared.mapper;

import com.store.products.domain.shared.entity.Product;
import com.store.products.infrastructure.file.csv.utils.mapper.CsvRecordMapper;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.store.products.infrastructure.file.csv.shared.CsvProductRepository.ID;
import static com.store.products.infrastructure.file.csv.shared.CsvProductRepository.SEQUENCE;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.trimToNull;

public class CsvProductMapper implements CsvRecordMapper<Product> {
    @Override
    public Product mapRecord(CSVRecord csvRecord) {
        var id = trimToNull(csvRecord.get(ID));

        if (id == null) {
            LOGGER.info("Format not valid productId: {}. Skipping row", id);
            return null;
        }

        try {
            return new Product(
                id,
                Optional.ofNullable(csvRecord.get(SEQUENCE))
                    .map(sequence -> parseInt(sequence.trim()))
                    .orElse(MAX_VALUE)
            );
        } catch (NumberFormatException e) {
            LOGGER.warn("Format not valid for Product ID: {}. Skipping row", id);
            return null;
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvProductMapper.class);
}
