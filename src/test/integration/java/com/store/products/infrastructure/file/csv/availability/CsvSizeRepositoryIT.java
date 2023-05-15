package com.store.products.infrastructure.file.csv.availability;

import com.store.products.infrastructure.Properties;
import com.store.products.infrastructure.file.csv.exception.CsvException;
import com.store.products.infrastructure.file.csv.shared.CsvSizeRepository;
import com.store.products.infrastructure.file.csv.shared.CsvSizeRepositoryConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.store.products.infrastructure.rest.utils.Fixtures.getPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = CsvSizeRepositoryConfiguration.class)
class CsvSizeRepositoryIT {

    @Autowired CsvSizeRepository repository;

    @MockBean Properties properties;

    @Test
    void size_repository_test_blanks() {
        given(properties.getSizeFilePath()).willReturn(getPath("csv/size-blanks.csv"));

        var sizeSet = repository.findAll();

        assertThat(sizeSet).hasSize(3)
            .satisfiesExactlyInAnyOrder(
                size -> {
                    assertThat(size.sizeId()).isEqualTo("23");
                    assertThat(size.productId()).isEqualTo("2");
                    assertThat(size.backSoon()).isTrue();
                    assertThat(size.special()).isTrue();
                },
                size -> {
                    assertThat(size.sizeId()).isEqualTo("11");
                    assertThat(size.productId()).isEqualTo("1");
                    assertThat(size.backSoon()).isTrue();
                    assertThat(size.special()).isFalse();
                },
                size -> {
                    assertThat(size.sizeId()).isEqualTo("42");
                    assertThat(size.productId()).isEqualTo("4");
                    assertThat(size.backSoon()).isFalse();
                    assertThat(size.special()).isTrue();
                });
    }

    @Test
    void size_repository_test_invalid_row() {
        given(properties.getSizeFilePath()).willReturn(getPath("csv/size-invalid-row.csv"));

        var sizeSet = repository.findAll();

        assertThat(sizeSet)
            .hasSize(1)
            .satisfiesExactly(
                size -> {
                    assertThat(size.sizeId()).isEqualTo("23");
                    assertThat(size.productId()).isEqualTo("2");
                    assertThat(size.backSoon()).isTrue();
                    assertThat(size.special()).isFalse();
                });
    }

    @Test
    void size_repository_test_invalid_file() {
        var filePath = getPath("some.file");
        given(properties.getSizeFilePath()).willReturn(filePath);

        assertThatExceptionOfType(CsvException.class)
            .isThrownBy(() -> repository.findAll())
            .withMessage("Error reading the Size file: " + filePath);
    }
}
