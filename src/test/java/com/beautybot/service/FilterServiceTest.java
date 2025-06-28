package com.beautybot.service;

import com.beautybot.domain.product.LeafProduct;
import com.beautybot.domain.product.ProductComponent;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterServiceTest {

    @Test
    void filterByMaxPriceKeepsOnlyCheapItems() {
        List<ProductComponent> items = List.of(
            new LeafProduct("Cheap",  new BigDecimal("5.00")),
            new LeafProduct("Expensive", new BigDecimal("50.00"))
        );

        FilterService service = new FilterService();
        List<ProductComponent> filtered = service.filterByMaxPrice(
            items, new BigDecimal("10.00")
        );

        assertEquals(1, filtered.size(), "Dovrebbe restare solo un prodotto");
        assertEquals("Cheap", filtered.get(0).getName());
    }
}

