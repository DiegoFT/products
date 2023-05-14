package com.store.products.infrastructure.file.csv.utils.mapper;


import org.apache.commons.csv.CSVRecord;

public interface CsvRecordMapper<T> {
    T mapRecord(CSVRecord csvRecord);
}
