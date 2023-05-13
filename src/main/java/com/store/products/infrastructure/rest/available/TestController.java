package com.store.products.infrastructure.rest.available;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements TestAPI {

    @Override
    @GetMapping("/test")
    public String invoke() {
        return "Hello World";
    }

}
