package com.store.products.infrastructure.rest.availability;

import com.store.products.infrastructure.rest.availability.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Products Available API")
public interface ProductsAvailableAPI {

    @Operation(
        summary = "Returns a list of available products",
        description = "Returns a list with the available products ordered by the sequence",
        responses = {
            @ApiResponse(responseCode = "200", description = "Products Available"),
            @ApiResponse(responseCode = "404", description = "Files Not Found"),
            @ApiResponse(responseCode = "500", description = "Error occurred while getting available products")
        }
    )
    Response invoke();
}
