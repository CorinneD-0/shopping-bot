package com.beautybot.service;

import com.beautybot.domain.product.ProductComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service per filtrare prodotti in base a criteri diversi.
 * Mostra l’uso combinato di Iterator e Stream API per restituire
 * solo i {@link ProductComponent} con prezzo ≤ maxPrice.
 */
public class FilterService {
    private static final Logger logger = LogManager.getLogger(FilterService.class);

    /**
     * Filtra i prodotti la cui price ≤ maxPrice.
     *
     * @param items    lista originale di ProductComponent
     * @param maxPrice soglia massima
     * @return sotto-lista di items con prezzo ≤ maxPrice
     */
    public List<ProductComponent> filterByMaxPrice(List<ProductComponent> items,
                                                   BigDecimal maxPrice) {
        logger.debug("Filtering {} items with maxPrice = {}", items.size(), maxPrice);

        // Costruisco una copia per sicurezza
        List<ProductComponent> temp = new ArrayList<>();
        Iterator<ProductComponent> it = items.iterator();
        while (it.hasNext()) {
            temp.add(it.next());
        }

        // Applico il filtro con Stream
        List<ProductComponent> filtered = temp.stream()
            .filter(p -> p.getPrice().compareTo(maxPrice) <= 0)
            .collect(Collectors.toList());

        logger.debug("Filtered down to {} items", filtered.size());
        return filtered;
    }
}
