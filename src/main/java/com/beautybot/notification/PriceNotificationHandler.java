package com.beautybot.notification;

import com.beautybot.domain.product.ProductComponent;

import java.math.BigDecimal;

/**
 * Notifica i prodotti il cui prezzo > soglia minima.
 */
public class PriceNotificationHandler extends NotificationHandler {
    private final BigDecimal minPrice;

    public PriceNotificationHandler(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    @Override
    protected boolean check(ProductComponent product) {
        // passa solo se prezzo >= minPrice
        return product.getPrice().compareTo(minPrice) >= 0;
    }

    @Override
    protected void notify(ProductComponent product) {
        System.out.println("[OFFER] " + product.getName()
            + " a " + product.getPrice());
    }
}
