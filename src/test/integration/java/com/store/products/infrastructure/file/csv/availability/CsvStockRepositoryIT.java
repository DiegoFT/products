package com.store.products.infrastructure.file.csv.availability;

import com.store.products.infrastructure.Properties;
import com.store.products.infrastructure.file.csv.availability.exception.CsvException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.store.products.infrastructure.rest.utils.Fixtures.getPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = CsvStockRepositoryConfiguration.class)
class CsvStockRepositoryIT {

    @Autowired CsvStockRepository repository;

    @MockBean Properties properties;

    @Test
    void stock_repository_test_blanks() {
        given(properties.getStockFilePath()).willReturn(getPath("csv/stock-blanks.csv"));

        var stockList = repository.findAll();

        assertThat(stockList)
            .hasSize(3)
            .satisfiesExactly(
                stock -> {
                    assertThat(stock.sizeId()).isEqualTo("11");
                    assertThat(stock.quantity()).isZero();
                },
                stock -> {
                    assertThat(stock.sizeId()).isEqualTo("12");
                    assertThat(stock.quantity()).isEqualTo(1);
                },
                stock -> {
                    assertThat(stock.sizeId()).isEqualTo("13");
                    assertThat(stock.quantity()).isEqualTo(1);
                });
    }

    @Test
    void stock_repository_test_invalid_row() {
        given(properties.getStockFilePath()).willReturn(getPath("csv/stock-invalid-row.csv"));

        var stockList = repository.findAll();

        assertThat(stockList)
            .hasSize(1)
            .satisfiesExactly(
                stock -> {
                    assertThat(stock.sizeId()).isEqualTo("11");
                    assertThat(stock.quantity()).isZero();
                });
    }

    @Test
    void stock_repository_test_invalid_file() {
        var filePath = getPath("some.file");
        given(properties.getStockFilePath()).willReturn(filePath);

        assertThatExceptionOfType(CsvException.class)
            .isThrownBy(() -> repository.findAll())
            .withMessage("Error reading the Stock file: " + filePath);
    }
}
