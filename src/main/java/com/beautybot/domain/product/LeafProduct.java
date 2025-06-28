package com.beautybot.domain.product;

import java.math.BigDecimal;
import java.util.List;

/**
 * Foglia del Composite Pattern.
 * Rappresenta un singolo prodotto con nome e prezzo.
 */
public class LeafProduct implements ProductComponent {
    private final String name;
    private final BigDecimal price;

    public LeafProduct(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    @Override public String getName() { return name; }
    @Override public BigDecimal getPrice() { return price; }

    @Override public void add(ProductComponent c) {
        throw new UnsupportedOperationException("Leaf cannot contain children");
    }
    @Override public void remove(ProductComponent c) {
        throw new UnsupportedOperationException("Leaf cannot contain children");
    }
    @Override public List<ProductComponent> getChildren() {
        return List.of();
    }
}

