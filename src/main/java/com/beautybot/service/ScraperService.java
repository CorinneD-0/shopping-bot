package com.beautybot.service;

import com.beautybot.scraper.ProductScraper;
import com.beautybot.repository.ProductRepository;
import com.beautybot.domain.product.ProductComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 * Facade Service per lo scraping e la persistenza dei prodotti.
 * Applica il pattern Shielding: cattura eccezioni di rete e le rilancia come RuntimeException.
 * I suoi componenti sono iniettati tramite Guice (DI).
 */
public class ScraperService {
    private static final Logger logger = LogManager.getLogger(ScraperService.class);

    private final ProductScraper scraper;
    private final ProductRepository repository;

    @Inject
    public ScraperService(ProductScraper scraper, ProductRepository repository) {
        this.scraper = scraper;
        this.repository = repository;
    }

    /**
     * Esegue lo scraping dei prodotti, ne salva il catalogo e ritorna la lista.
     * In caso di errore rilancia una RuntimeException.
     */
    public List<ProductComponent> scrapeAndPersist() {
        logger.info("Starting scraping of products...");
        try {
            List<ProductComponent> items = scraper.scrape();
            logger.info("Scraped {} items", items.size());
            repository.save(items);
            logger.info("Saved {} items to repository", items.size());
            return items;
        } catch (Exception e) {
            logger.error("Error during scraping/persistence", e);
            throw new RuntimeException("Errore nello scraping dei prodotti", e);
        }
    }
}
