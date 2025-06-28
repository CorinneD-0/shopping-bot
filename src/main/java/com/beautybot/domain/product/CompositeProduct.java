package com.beautybot.domain.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Nodo Composite del Composite Pattern.
 * Contiene zero o più {@link ProductComponent} figli.
 * Ricalcola il prezzo sommando ricorsivamente i prezzi dei figli.
 * È {@link Iterable} grazie a {@link ProductComponentIterator}.
 */
public class CompositeProduct implements ProductComponent, Iterable<ProductComponent> {
    private final String name;
    private final List<ProductComponent> children = new ArrayList<>();

    public CompositeProduct(String name) {
        this.name = name;
    }

    @Override public String getName() { return name; }

    @Override
    public BigDecimal getPrice() {
        return children.stream()
                       .map(ProductComponent::getPrice)
                       .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override public void add(ProductComponent c) {
        children.add(c);
    }
    @Override public void remove(ProductComponent c) {
        children.remove(c);
    }
    @Override public List<ProductComponent> getChildren() {
        return List.copyOf(children);
    }
    @Override
    public Iterator<ProductComponent> iterator() {
        return new ProductComponentIterator(this);
    }
}
