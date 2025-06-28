package com.beautybot.domain.product;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

class ProductComponentTest {

    @Test
    void compositeCalculatesSumPriceAndChildrenCount() {
        CompositeProduct catalog = new CompositeProduct("Catalogo");
        LeafProduct lipstick = new LeafProduct("Rossetto", new BigDecimal("10.0"));
        LeafProduct cream   = new LeafProduct("Crema",    new BigDecimal("20.0"));

        catalog.add(lipstick);
        catalog.add(cream);

        assertEquals(2, catalog.getChildren().size());
        assertEquals(new BigDecimal("30.0"), catalog.getPrice());
    }
}

