package com.store.products.infrastructure.file.csv.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvReaderUtils {
    public static List<CSVRecord> readCsv(String filePath, String[] headers) throws IOException {
        var in = new FileReader(filePath);
        var csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(headers)
            .setSkipHeaderRecord(true)
            .build();
        var records = new CSVParser(in, csvFormat);

        return records.getRecords();
    }
}
