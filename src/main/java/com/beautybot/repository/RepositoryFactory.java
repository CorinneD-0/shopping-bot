package com.beautybot.repository;

/**
 * Factory per creare istanze di ProductRepository.
 */
public class RepositoryFactory {

    /**
     * Restituisce un repository in base al tipo specificato.
     * @param type "json" per JsonProductRepository
     * @return ProductRepository istanziato
     */
    public static ProductRepository create(String type) {
        if ("json".equalsIgnoreCase(type)) {
            return new JsonProductRepository("products.json");
        }
        throw new IllegalArgumentException("Unknown repository type: " + type);
    }
}
