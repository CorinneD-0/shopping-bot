package com.beautybot.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RepositoryFactoryTest {

    @Test
    void createJsonRepository() {
        ProductRepository repo = RepositoryFactory.create("json");
        assertNotNull(repo, "Repository non deve essere null");
        assertTrue(repo instanceof JsonProductRepository, "Dovrebbe essere JsonProductRepository");
    }

    @Test
    void unknownTypeThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> RepositoryFactory.create("csv"),
            "Tipo sconosciuto deve lanciare IllegalArgumentException");
    }
}
