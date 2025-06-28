package com.beautybot.repository;

import com.beautybot.domain.product.ProductComponent;
import java.util.List;

/**
 * Repository generico per caricare e salvare un catalogo
 * di ProductComponent.
 */
public interface ProductRepository {

    /**
     * Carica il catalogo di prodotti.
     * @return lista di ProductComponent (leaf o composite)
     */
    List<ProductComponent> load();

    /**
     * Salva la lista di prodotti su file o database.
     * @param items lista di ProductComponent da persistere
     */
    void save(List<ProductComponent> items);
}