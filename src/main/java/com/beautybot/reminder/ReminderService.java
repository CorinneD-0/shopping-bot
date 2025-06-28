package com.beautybot.reminder;

import com.beautybot.service.ScraperService;
import com.beautybot.notification.NotificationService;
import com.beautybot.domain.product.ProductComponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Service asincrono basato su {@link ScheduledExecutorService}.
 * Pianifica un task ricorrente che esegue scraping e invoca notifiche.
 */
public class ReminderService {
    private static final Logger logger = LogManager.getLogger(ReminderService.class);

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final ScraperService scraperService;
    private final NotificationService notificationService;
    private final long initialDelay;
    private final long period;
    private final TimeUnit unit;
    private ScheduledFuture<?> task;

    /**
     * @param scraperService      il servizio di scraping
     * @param notificationService il servizio di notifiche
     * @param initialDelay        ritardo iniziale prima della prima esecuzione
     * @param period              intervallo fra le esecuzioni
     * @param unit                unità di tempo per initialDelay e period
     */
    public ReminderService(ScraperService scraperService,
                           NotificationService notificationService,
                           long initialDelay,
                           long period,
                           TimeUnit unit) {
        this.scraperService = scraperService;
        this.notificationService = notificationService;
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
    }

    /** Avvia il task periodico */
    public void start() {
        logger.info("Scheduling reminder: first run in {} {}, then every {} {}",
                    initialDelay, unit, period, unit);
        task = scheduler.scheduleAtFixedRate(() -> {
            logger.info("ReminderService running scheduled task");
            try {
                List<ProductComponent> items = scraperService.scrapeAndPersist();
                notificationService.notifyOffers(items);
            } catch (Exception e) {
                logger.error("Error during scheduled task", e);
            }
        }, initialDelay, period, unit);
    }

    /** Ferma il task e chiude lo scheduler */
    public void stop() {
        if (task != null) {
            task.cancel(false);
        }
        scheduler.shutdownNow();
        logger.info("ReminderService stopped");
    }
}
