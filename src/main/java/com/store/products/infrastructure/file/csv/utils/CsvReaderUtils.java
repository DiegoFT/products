package com.store.products.infrastructure.file.csv.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CsvReaderUtils {
    public static Set<CSVRecord> readCsv(String filePath, String[] headers) throws IOException {
        try (var in = new FileReader(filePath)) {
            var csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(headers)
                .setSkipHeaderRecord(true)
                .build();
            var parser = new CSVParser(in, csvFormat);
            return new HashSet<>(parser.getRecords());
        }
    }

    private CsvReaderUtils() {
    }
}
