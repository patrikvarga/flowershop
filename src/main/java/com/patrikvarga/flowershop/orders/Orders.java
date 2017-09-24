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
        return null;
    }

}
