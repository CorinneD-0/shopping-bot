package com.beautybot.domain.product;

import java.math.BigDecimal;
import java.util.List;

/**
 * Componente base del Composite Pattern per prodotti.
 * Un ProductComponent può essere sia un {@link LeafProduct} sia un {@link CompositeProduct}.
 * Fornisce operazioni comuni:
 *  - {@link #getName()}  
 *  - {@link #getPrice()}  
 *  - add/remove figli (solo in Composite)
 *
 * Questo permette di trattare uniformemente foglie e insiemi di prodotti.
 */
public interface ProductComponent {
    String getName();
    BigDecimal getPrice();

    default void add(ProductComponent c) {
        throw new UnsupportedOperationException("Cannot add to leaf");
    }
    default void remove(ProductComponent c) {
        throw new UnsupportedOperationException("Cannot remove from leaf");
    }
    default List<ProductComponent> getChildren() {
        return List.of();
    }
}
