package com.patrikvarga.flowershop.orders;

import com.patrikvarga.flowershop.catalog.Bundle;
import com.patrikvarga.flowershop.catalog.Flower;
import com.patrikvarga.flowershop.catalog.Flowers;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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

        final BundledOrder bundledOrder = new BundledOrder();

        order.items().forEach((productCode, amount) -> {
            final BundlingDetails details = breakdown(productCode, amount);
            bundledOrder.addDetails(productCode, details);
        });

        return bundledOrder;
    }

    private BundlingDetails breakdown(final String produtCode, final int amount) {
        final Flower flower = flowers.find(produtCode);

        int amountLeft = amount;
        BigDecimal totalCost = BigDecimal.ZERO;
        final Map<Bundle, Integer> bundles = new HashMap<>();

        for (Bundle availableBundle : flower.bundles()) {
            while (availableBundle.amount() <= amountLeft) {
                amountLeft -= availableBundle.amount();
                totalCost = totalCost.add(availableBundle.price());
                bundles.compute(availableBundle, (b, a) -> a == null ? 1 : a + 1);
            }
        }
        if (amountLeft != 0) {
            throw new NoMatchingBundlesException(amount, amountLeft);
        }

        return new BundlingDetails(amount, totalCost, bundles);
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
