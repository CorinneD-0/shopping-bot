package com.beautybot.domain.product;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifica che ProductComponentIterator faccia un depth-first
 * flattening di un albero di CompositeProduct e LeafProduct.
 */
class ProductComponentIteratorTest {

    @Test
    void depthFirstTraversal_shouldVisitAllNodesInRightOrder() {
        // 1) costruisco un albero:
        //    root
        //    ├─ leafA
        //    └─ compB
        //       ├─ leafB1
        //       └─ leafB2

        CompositeProduct root = new CompositeProduct("root");
        LeafProduct leafA = new LeafProduct("A", new BigDecimal("1.0"));
        CompositeProduct compB = new CompositeProduct("B");
        LeafProduct leafB1 = new LeafProduct("B1", new BigDecimal("2.0"));
        LeafProduct leafB2 = new LeafProduct("B2", new BigDecimal("3.0"));

        root.add(leafA);
        root.add(compB);
        compB.add(leafB1);
        compB.add(leafB2);

        // 2) ottengo l’iterator
        Iterator<ProductComponent> it = root.iterator();

        // 3) raccolgo i nomi nell’ordine visitato
        List<String> visited = new ArrayList<>();
        while (it.hasNext()) {
            visited.add(it.next().getName());
        }

        // 4) confronto con l’ordine DFS atteso
        List<String> expected = List.of("root", "A", "B", "B1", "B2");
        assertEquals(expected, visited,
            "L’iterator deve eseguire un depth-first in ordine corretto");
    }
}
