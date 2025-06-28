package com.beautybot;

import com.beautybot.di.BeautyBotModule;
import com.beautybot.domain.product.LeafProduct;
import com.beautybot.domain.product.ProductComponent;
import com.beautybot.notification.NotificationService;
import com.beautybot.reminder.ReminderService;
import com.beautybot.service.ScraperService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Entry point dell’app:
 *   1) shielding input CLI  
 *   2) DI con Guice  
 *   3) scraping → persistence → fallback → stampa → notifiche  
 *   4) avvio ReminderService  
 */
public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        // 1) parsing & validation
        if (args.length != 2) {
            logger.error("Usage: java -jar shopping-bot.jar <productsUrl> <minPrice>");
            System.err.println("Usage: java -jar shopping-bot.jar <productsUrl> <minPrice>");
            System.exit(1);
        }

        // Validazione URL
        URL url;
        try {
            url = new URL(args[0]);
        } catch (MalformedURLException e) {
            logger.error("Invalid URL: {}", args[0], e);
            System.err.println("Invalid URL: " + args[0]);
            System.exit(1);
            return;
        }

        // Validazione soglia
        BigDecimal minPrice;
        try {
            minPrice = new BigDecimal(args[1]);
            if (minPrice.compareTo(BigDecimal.ZERO) < 0) {
                throw new NumberFormatException("Negative threshold");
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid price threshold: {}", args[1], e);
            System.err.println("Invalid price threshold: " + args[1]);
            System.exit(1);
            return;
        }

        // 2) Dependency Injection con URL e minPrice
        Injector injector = Guice.createInjector(
            new BeautyBotModule(url.toString(), minPrice)
        );

        // 3) otteniamo i service
        ScraperService     scraperService      = injector.getInstance(ScraperService.class);
        NotificationService notificationService = injector.getInstance(NotificationService.class);
        ReminderService    reminderService     = injector.getInstance(ReminderService.class);

        try {
            // scraping + persistenza
            List<ProductComponent> items = scraperService.scrapeAndPersist();

            // fallback demo
            if (items.isEmpty()) {
                logger.warn("Empty product list; using demo data");
                items = List.of(
                    new LeafProduct("Demo Rossetto", new BigDecimal("12.50")),
                    new LeafProduct("Demo Crema",    new BigDecimal("8.00")),
                    new LeafProduct("Demo Siero",    new BigDecimal("18.00"))
                );
            }

            // stampa catalogo
            System.out.println("=== Prodotti correnti ===");
            items.forEach(p -> System.out.println(p.getName() + " → " + p.getPrice()));

            // notifiche
            System.out.println("\n=== Offerte ≥ " + minPrice + " ===");
            notificationService.notifyOffers(items);

            // 4) avvio reminder periodico
            logger.info("Starting ReminderService");
            reminderService.start();

            // manteniamo vivo il main thread
            Thread.currentThread().join();

        } catch (Exception e) {
            logger.error("Errore in esecuzione", e);
            System.err.println("Si è verificato un errore. Controlla i log.");
            System.exit(1);
        }
    }
}
