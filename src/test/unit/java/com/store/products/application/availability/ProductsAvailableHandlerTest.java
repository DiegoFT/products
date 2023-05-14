package com.store.products.application.availability;

import com.store.products.domain.availability.entity.Stock;
import com.store.products.domain.shared.entity.Product;
import com.store.products.domain.shared.entity.Size;
import com.store.products.infrastructure.file.csv.availability.CsvStockRepository;
import com.store.products.infrastructure.file.csv.shared.CsvProductRepository;
import com.store.products.infrastructure.file.csv.shared.CsvSizeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class ProductsAvailableHandlerTest {
    @InjectMocks private ProductsAvailableHandler handler;

    @Test
    void text_example() {
        List<Product> productList = List.of(
            new Product("1", 10),
            new Product("2", 7),
            new Product("3", 15),
            new Product("4", 13),
            new Product("5", 6)
        );
        List<Size> sizeList = List.of(
            new Size("11", "1", true, false),
            new Size("12", "1", false, false),
            new Size("13", "1", true, false),
            new Size("21", "2", false, false),
            new Size("22", "2", false, false),
            new Size("23", "2", true, true),
            new Size("31", "3", true, false),
            new Size("32", "3", true, false),
            new Size("33", "3", false, false),
            new Size("41", "4", false, false),
            new Size("42", "4", false, false),
            new Size("43", "4", false, false),
            new Size("44", "4", true, true),
            new Size("51", "5", true, false),
            new Size("52", "5", false, false),
            new Size("53", "5", false, false),
            new Size("54", "5", true, true)
        );
        List<Stock> stockList = List.of(
            new Stock("11", 0),
            new Stock("12", 0),
            new Stock("13", 0),
            new Stock("22", 0),
            new Stock("31", 10),
            new Stock("32", 10),
            new Stock("33", 10),
            new Stock("41", 0),
            new Stock("42", 0),
            new Stock("43", 0),
            new Stock("44", 10),
            new Stock("51", 10),
            new Stock("52", 10),
            new Stock("53", 10),
            new Stock("54", 10)
        );

        givenLists(productList, sizeList, stockList);

        var products = handler.handle();

        assertThat(products).satisfiesExactly(
            result -> {
                assertThat(result.id()).isEqualTo("1");
                assertThat(result.priority()).isEqualTo(10);
            },
            result -> {
                assertThat(result.id()).isEqualTo("3");
                assertThat(result.priority()).isEqualTo(15);
            },
            result -> {
                assertThat(result.id()).isEqualTo("5");
                assertThat(result.priority()).isEqualTo(6);
            }
        );
    }

    @Test
    void test_products_without_size() {
        var productList = List.of(
            new Product("1", 10),
            new Product("2", 7));
        var sizeList = List.of(new Size("11", "4", false, false));
        var stockList = List.of(new Stock("11", 10));
        givenLists(productList, sizeList, stockList);

        var result = handler.handle();

        assertThat(result).isEmpty();
    }

    @Test
    void test_products_with_size_with_stock() {
        var productList = List.of(
            new Product("1", 10),
            new Product("2", 7));
        var sizeList = List.of(new Size("11", "2", false, false));
        var stockList = List.of(new Stock("11", 10));
        givenLists(productList, sizeList, stockList);

        var results = handler.handle();

        assertThat(results)
            .hasSize(1)
            .satisfiesExactly(
                result -> {
                    assertThat(result.id()).isEqualTo("2");
                    assertThat(result.priority()).isEqualTo(7);
                });
    }

    @Test
    void test_products_with_size_without_stock_not_coming_soon() {
        var productList = List.of(
            new Product("1", 10),
            new Product("2", 7));
        var sizeList = List.of(new Size("11", "2", false, false));
        var stockList = List.of(new Stock("11", 0));
        givenLists(productList, sizeList, stockList);

        var results = handler.handle();

        assertThat(results).isEmpty();
    }

    @Test
    void test_products_with_size_without_stock_and_coming_soon() {
        var productList = List.of(
            new Product("1", 10),
            new Product("2", 7));
        var sizeList = List.of(new Size("11", "2", true, false));
        var stockList = List.of(new Stock("11", 0));
        givenLists(productList, sizeList, stockList);

        var results = handler.handle();

        assertThat(results)
            .hasSize(1)
            .satisfiesExactly(
                result -> {
                    assertThat(result.id()).isEqualTo("2");
                    assertThat(result.priority()).isEqualTo(7);
                });
    }

    @Test
    void test_products_with_sizes_specials_with_stock_and_no_specials_with_stock() {
        var productList = List.of(
            new Product("3", 15),
            new Product("4", 20));
        var sizeList = List.of(
            new Size("11", "4", false, true),
            new Size("12", "4", false, false),
            new Size("13", "4", false, false));
        var stockList = List.of(
            new Stock("11", 10),
            new Stock("12", 10),
            new Stock("13", 0));
        givenLists(productList, sizeList, stockList);

        var results = handler.handle();

        assertThat(results)
            .hasSize(1)
            .satisfiesExactly(
                result -> {
                    assertThat(result.id()).isEqualTo("4");
                    assertThat(result.priority()).isEqualTo(20);
                });
    }

    @Test
    void test_products_with_sizes_specials_without_stock_and_no_specials_with_stock() {
        var productList = List.of(
            new Product("3", 15),
            new Product("4", 20));
        var sizeList = List.of(
            new Size("11", "4", false, true),
            new Size("12", "4", false, false),
            new Size("13", "4", false, false));
        var stockList = List.of(
            new Stock("11", 0),
            new Stock("12", 10),
            new Stock("13", 10));
        givenLists(productList, sizeList, stockList);

        var results = handler.handle();

        assertThat(results).isEmpty();
    }


    private void givenLists(List<Product> productList, List<Size> sizeList, List<Stock> stockList) {
        given(productRepository.findAll()).willReturn(productList);
        given(sizeRepository.findAll()).willReturn(sizeList);
        given(stockRepository.findAll()).willReturn(stockList);
    }

    @Mock private CsvProductRepository productRepository;
    @Mock private CsvStockRepository stockRepository;
    @Mock private CsvSizeRepository sizeRepository;
}
