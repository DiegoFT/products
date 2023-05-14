package com.store.products.infrastructure.file.csv.shared.mapper;

import com.store.products.domain.shared.entity.Size;
import com.store.products.infrastructure.file.csv.utils.mapper.CsvRecordMapper;
import org.apache.commons.csv.CSVRecord;

import static com.store.products.infrastructure.file.csv.shared.CsvSizeRepository.BACK_SOON;
import static com.store.products.infrastructure.file.csv.shared.CsvSizeRepository.PRODUCT_ID;
import static com.store.products.infrastructure.file.csv.shared.CsvSizeRepository.SIZE_ID;
import static com.store.products.infrastructure.file.csv.shared.CsvSizeRepository.SPECIAL;
import static java.lang.Boolean.parseBoolean;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;
import static org.apache.commons.lang3.StringUtils.trimToNull;

public class CsvSizeMapper implements CsvRecordMapper<Size> {

    @Override
    public Size mapRecord(CSVRecord csvRecord) {
        var sizeId = trimToNull(csvRecord.get(SIZE_ID));
        var productId = trimToNull(csvRecord.get(PRODUCT_ID));

        if (sizeId == null || productId == null) {
            return null;
        }

        return new Size(
            sizeId,
            productId,
            parseBoolean(trimToEmpty(csvRecord.get(BACK_SOON))),
            parseBoolean(trimToEmpty(csvRecord.get(SPECIAL)))
        );
    }
}
