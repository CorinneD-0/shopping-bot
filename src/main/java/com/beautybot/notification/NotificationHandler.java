package com.beautybot.notification;

import com.beautybot.domain.product.ProductComponent;

/**
 * Base class per il Chain of Responsibility.
 */
public abstract class NotificationHandler {
    protected NotificationHandler next;

    /** Imposta il prossimo handler della catena */
    public NotificationHandler setNext(NotificationHandler next) {
        this.next = next;
        return next;
    }

    /**
     * Gestisce il prodotto: se il criterio è soddisfatto,
     * passa al prossimo handler o notifica.
     */
    public void handle(ProductComponent product) {
        if (check(product)) {
            if (next != null) {
                next.handle(product);
            } else {
                // ultimo handler: notifica
                notify(product);
            }
        }
        // altrimenti scarta silenziosamente
    }

    /** Criterio di filtro: restituisce true se deve passare */
    protected abstract boolean check(ProductComponent product);

    /** Azione di notifica effettiva */
    protected abstract void notify(ProductComponent product);
}
