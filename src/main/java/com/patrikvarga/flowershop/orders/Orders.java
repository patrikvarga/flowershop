package com.patrikvarga.flowershop.orders;

import com.patrikvarga.flowershop.catalog.Flowers;

/**
 * Ordering related services of the flower shop.
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class Orders {

    private final Flowers flowers;

    public Orders(final Flowers flowers) {
        this.flowers = flowers;
    }

    public BundledOrder bundle(final Order order) {
        validate(order);

        return null;
    }

    private void validate(final Order order) {
        if (order == null) {
            throw new NullPointerException("Order is null");
        }
        if (order.items().isEmpty()) {
            throw new IllegalArgumentException("Order is empty");
        }
    }

}
