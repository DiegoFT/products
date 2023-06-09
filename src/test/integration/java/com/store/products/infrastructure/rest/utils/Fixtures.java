package com.store.products.infrastructure.rest.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface Fixtures {
    static String getFixtures(String fileName) throws IOException {
        var path = Paths.get("src/test/integration/resources/fixtures/" + fileName);

        return Files.readString(path);
    }

    static String getPath(String fileName) {
        return Paths.get("src/test/integration/resources/fixtures/" + fileName).toString();
    }
}
