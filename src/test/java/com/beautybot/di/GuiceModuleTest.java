package com.beautybot.di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.beautybot.service.ScraperService;
import com.beautybot.notification.NotificationService;
import com.beautybot.reminder.ReminderService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifica che Guice riesca a iniettare i service dal modulo.
 */
class GuiceModuleTest {

    @Test
    void guiceInjectionWorks() {
        // Creo un threshold di test
        BigDecimal threshold = new BigDecimal("10.00");

        // Costruisco l'injector con URL fittizio + soglia
        Injector injector = Guice.createInjector(
            new BeautyBotModule("http://dummy-url", threshold)
        );

        // Provo a estrarre i service e verifico non siano null
        ScraperService scraperService = injector.getInstance(ScraperService.class);
        assertNotNull(scraperService, "ScraperService deve essere iniettato");

        NotificationService notificationService = injector.getInstance(NotificationService.class);
        assertNotNull(notificationService, "NotificationService deve essere iniettato");

        ReminderService reminderService = injector.getInstance(ReminderService.class);
        assertNotNull(reminderService, "ReminderService deve essere iniettato");
    }
}
