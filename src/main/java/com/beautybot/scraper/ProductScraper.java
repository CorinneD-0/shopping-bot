package com.beautybot.scraper;

import com.beautybot.domain.product.ProductComponent;
import java.util.List;

/**
 * Un scraper che estrae prodotti da una sorgente (es. pagina web).
 */
public interface ProductScraper {
    /**
     * Esegue lo scraping e restituisce una lista di ProductComponent.
     * @return lista di prodotti
     * @throws Exception su errori di rete/parsing
     */
    List<ProductComponent> scrape() throws Exception;
}
