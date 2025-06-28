package com.beautybot.di;

import com.beautybot.notification.NotificationService;
import com.beautybot.reminder.ReminderService;
import com.beautybot.repository.ProductRepository;
import com.beautybot.repository.RepositoryFactory;
import com.beautybot.scraper.JsoupProductScraper;
import com.beautybot.scraper.ProductScraper;
import com.beautybot.service.ScraperService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Modulo Guice per:
 * - ProductScraper e ScraperService
 * - repository JSON
 * - NotificationService con soglia @Named("minPrice")
 * - ReminderService periodico
 */
public class BeautyBotModule extends AbstractModule {

    private final String url;
    private final BigDecimal minPrice;

    public BeautyBotModule(String url, BigDecimal minPrice) {
        this.url = url;
        this.minPrice = minPrice;
    }

    @Override
    protected void configure() {
        // solo il repository qui
        bind(ProductRepository.class)
            .toInstance(RepositoryFactory.create("json"));
    }

    @Provides @Singleton
    ProductScraper provideScraper() {
        return new JsoupProductScraper(url, ".product-item", ".name", ".price");
    }

    @Provides @Singleton
    ScraperService provideScraperService(ProductScraper scraper, ProductRepository repo) {
        return new ScraperService(scraper, repo);
    }

    @Provides @Singleton @Named("minPrice")
    BigDecimal provideMinPrice() {
        return minPrice;
    }

    @Provides @Singleton
    NotificationService provideNotificationService(@Named("minPrice") BigDecimal threshold) {
        return new NotificationService(threshold);
    }

    @Provides @Singleton
    ScheduledExecutorService provideScheduler() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Provides @Singleton
    ReminderService provideReminderService(ScraperService scraperService,
                                           NotificationService notificationService,
                                           ScheduledExecutorService scheduler) {
        return new ReminderService(scraperService, notificationService, 0, 1, TimeUnit.HOURS);
    }
}
