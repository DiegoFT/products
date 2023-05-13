package com.store.products.infrastructure.rest.available;

import com.store.products.application.available.ProductsAvailableHandler;
import com.store.products.domain.entity.Product;
import com.store.products.domain.exception.available.InfoSourceNotFoundException;
import com.store.products.domain.exception.available.ProductsAvailableException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.store.products.infrastructure.rest.utils.Fixtures.getFixtures;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = ProductsAvailableControllerConfiguration.class)
class ProductsAvailableControllerIT {

    @Test
    void given_api_user_when_invoke_get_available_products_then_a_list_of_products_ordered_by_sequence_are_returned() throws Exception {
        var productsWithoutOrder = List.of(
            A_PRODUCT,
            B_PRODUCT,
            C_PRODUCT
        );
        given(handler.handle()).willReturn(productsWithoutOrder);

        mockMvc.perform(get("/products/available"))
            .andExpect(status().isOk())
            .andExpect(content().json(getFixtures("fixtures/json/products-available-ok.json")));
    }

    @Test
    void given_handle_not_found_exception_when_invoke_get_available_products_then_expected_error_code_is_returned() throws Exception {
        given(handler.handle()).willThrow(new InfoSourceNotFoundException("Some error getting product files"));

        mockMvc.perform(get("/products/available"))
            .andExpect(status().isNotFound())
            .andExpect(content().json(getFixtures("fixtures/json/products-available-not-found.json")));
    }

    @Test
    void given_handle_internal_exception_when_invoke_get_available_products_then_expected_error_code_is_returned() throws Exception {
        given(handler.handle()).willThrow(new ProductsAvailableException("Other error getting product"));

        mockMvc.perform(get("/products/available"))
            .andExpect(status().isInternalServerError())
            .andExpect(content().json(getFixtures("fixtures/json/products-available-internal-error.json")));
    }

    @Autowired MockMvc mockMvc;
    @MockBean ProductsAvailableHandler handler;

    private static final Product A_PRODUCT = new Product("1", 10);
    private static final Product B_PRODUCT = new Product("5", 6);
    private static final Product C_PRODUCT = new Product("3", 15);

}
