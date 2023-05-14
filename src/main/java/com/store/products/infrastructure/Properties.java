package com.store.products.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;

@AllArgsConstructor
public class Properties {

    private final Environment env;

    public String getProductFilePath() {
        return env.getProperty("store.products.csv.productPath");
    }

    public String getSizeFilePath() {
        return env.getProperty("store.products.csv.sizePath");
    }

    public String getStockFilePath() {
        return env.getProperty("store.products.csv.stockPath");
    }
}
