package com.beautybot.notification;

import com.beautybot.domain.product.ProductComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.List;

/**
 * Service che costruisce la catena di responsabilità (Chain of Responsibility)
 * usando {@link NotificationHandler} e {@link PriceNotificationHandler}.
 * La soglia minima viene iniettata via Guice (DI).
 */
public class NotificationService {
    private static final Logger logger = LogManager.getLogger(NotificationService.class);
    
    private final NotificationHandler chain;

    /**
     * Costruisce il servizio di notifica.
     *
     * @param minPrice soglia minima per le offerte
     */
    @Inject
    public NotificationService(@Named("minPrice") BigDecimal minPrice) {
        this.chain = new PriceNotificationHandler(minPrice);
    }

    /**
     * Esegue la notifica per tutti i prodotti passati:
     * scorre la catena di handler.
     *
     * @param items lista di prodotti
     */
    public void notifyOffers(List<ProductComponent> items) {
        logger.info("Notifying offers for {} items", items.size());
        for (ProductComponent item : items) {
            chain.handle(item);
        }
    }
}
