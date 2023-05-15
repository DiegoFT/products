package com.store.products.infrastructure.file.csv.shared;

import com.store.products.domain.shared.SizeRepository;
import com.store.products.domain.shared.entity.Size;
import com.store.products.infrastructure.Properties;
import com.store.products.infrastructure.file.csv.exception.CsvException;
import com.store.products.infrastructure.file.csv.shared.mapper.CsvSizeMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.store.products.infrastructure.file.csv.utils.CsvReaderUtils.readCsv;

@AllArgsConstructor
public class CsvSizeRepository implements SizeRepository {

    private final CsvSizeMapper mapper;

    private final Properties properties;

    @Override
    public Set<Size> findAll() {
        var filePath = properties.getSizeFilePath();

        try {
            String[] headers = {SIZE_ID, PRODUCT_ID, BACK_SOON, SPECIAL};
            LOGGER.info("Reading the Size CSV file: {}", filePath);

            return readCsv(filePath, headers).stream()
                .map(mapper::mapRecord)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        } catch (IOException e) {
            LOGGER.error("Error reading the Size file: {}", filePath);

            throw new CsvException("Error reading the Size file: " + filePath, e);
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvSizeRepository.class);
    public static final String SIZE_ID = "sizeId";
    public static final String PRODUCT_ID = "productId";
    public static final String BACK_SOON = "backSoon";
    public static final String SPECIAL = "special";
}
