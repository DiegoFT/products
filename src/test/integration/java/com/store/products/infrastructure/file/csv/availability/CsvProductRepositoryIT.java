package com.store.products.infrastructure.file.csv.availability;

import com.store.products.infrastructure.Properties;
import com.store.products.infrastructure.file.csv.availability.exception.CsvException;
import com.store.products.infrastructure.file.csv.shared.CsvProductRepository;
import com.store.products.infrastructure.file.csv.shared.CsvProductRepositoryConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.store.products.infrastructure.rest.utils.Fixtures.getPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = CsvProductRepositoryConfiguration.class)
class CsvProductRepositoryIT {

    @Autowired CsvProductRepository repository;

    @MockBean Properties properties;

    @Test
    void product_repository_test_blanks() {
        given(properties.getProductFilePath()).willReturn(getPath("csv/product-blanks.csv"));

        var productSet = repository.findAll();

        assertThat(productSet).hasSize(2)
            .satisfiesExactly(
                product -> {
                    assertThat(product.id()).isEqualTo("12");
                    assertThat(product.priority()).isEqualTo(1);
                },
                product -> {
                    assertThat(product.id()).isEqualTo("13");
                    assertThat(product.priority()).isEqualTo(13);
                });
    }

    @Test
    void product_repository_test_invalid_row() {
        given(properties.getProductFilePath()).willReturn(getPath("csv/product-invalid-row.csv"));

        var productSet = repository.findAll();

        assertThat(productSet)
            .hasSize(1)
            .satisfiesExactly(
                product -> {
                    assertThat(product.id()).isEqualTo("13");
                    assertThat(product.priority()).isEqualTo(1);
                });
    }

    @Test
    void product_repository_test_invalid_file() {
        var filePath = getPath("some.file");
        given(properties.getProductFilePath()).willReturn(filePath);

        assertThatExceptionOfType(CsvException.class)
            .isThrownBy(() -> repository.findAll())
            .withMessage("Error reading the Product file: " + filePath);
    }
}
