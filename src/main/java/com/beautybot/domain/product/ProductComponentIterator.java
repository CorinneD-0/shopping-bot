package com.beautybot.domain.product;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Depth-First Iterator per {@link ProductComponent}.
 * Scansiona l’intero albero, visitando prima la radice e poi ricorsivamente i figli.
 */
public class ProductComponentIterator implements Iterator<ProductComponent> {

    private final Deque<Iterator<ProductComponent>> stack = new ArrayDeque<>();
    private ProductComponent next;

    public ProductComponentIterator(ProductComponent root) {
        // inizializziamo lo stack con l'iterator della radice
        stack.push(Collections.singletonList(root).iterator());
        advance();
    }

    private void advance() {
        next = null;
        while (!stack.isEmpty()) {
            Iterator<ProductComponent> it = stack.peek();
            if (it.hasNext()) {
                ProductComponent pc = it.next();
                // se è composite, aggiungiamo i suoi figli ALLO STACK in reverse order
                List<ProductComponent> children = pc.getChildren();
                for (int i = children.size() - 1; i >= 0; i--) {
                    stack.push(
                        Collections.singletonList(children.get(i)).iterator()
                    );
                }
                next = pc;
                return;
            } else {
                stack.pop();
            }
        }
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public ProductComponent next() {
        ProductComponent result = next;
        advance();
        return result;
    }
}
