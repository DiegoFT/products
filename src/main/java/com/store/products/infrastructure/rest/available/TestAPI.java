package com.store.products.infrastructure.rest.available;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Testing API")
public interface TestAPI {

    @Operation(
        summary = "some summary",
        description = "some description",
        responses = {
            @ApiResponse(responseCode = "200", description = "testing")
        }
    )
    String invoke();
}
