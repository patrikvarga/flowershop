package com.patrikvarga.flowershop.orders;

import com.patrikvarga.flowershop.catalog.Bundle;
import com.patrikvarga.flowershop.catalog.Flower;
import com.patrikvarga.flowershop.catalog.Flowers;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Ordering related services of the flower shop.
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class Orders {

    private static final Logger LOGGER = LogManager.getLogger(Orders.class);

    private final Flowers flowers;

    public Orders(final Flowers flowers) {
        this.flowers = flowers;
    }

    public BundledOrder bundle(final Order order) {
        LOGGER.info("Bundling order: {}", order);

        validate(order);

        final BundledOrder bundledOrder = new BundledOrder();

        order.items().forEach((productCode, amount) -> {
            final BundlingDetails details = breakdown(productCode, amount);
            bundledOrder.addDetails(productCode, details);
        });

        LOGGER.info("Order successfully bundled as: {}", bundledOrder);
        return bundledOrder;
    }

    private BundlingDetails breakdown(final String produtCode, final int amount) {
        final Flower flower = flowers.find(produtCode);
        final List<Bundle> availableBundles = flower.bundles();
        if (availableBundles.isEmpty()) {
            throw new NoMatchingBundlesException(amount, amount);
        }

        final List<Bundle> usedBundles
                = CoinChangeSolver.minCange(
                        availableBundles.stream().toArray(Bundle[]::new),
                        Bundle::amount,
                        amount
                );

        return solutionAsBundlingDetails(amount, usedBundles);
    }

    private BundlingDetails solutionAsBundlingDetails(
            final int amount,
            final List<Bundle> usedBundles
    ) throws NoMatchingBundlesException {
        int amountLeft = amount;
        BigDecimal totalCost = BigDecimal.ZERO;
        final Map<Bundle, Integer> bundles = new HashMap<>();

        for (Bundle availableBundle : usedBundles) {
            amountLeft -= availableBundle.amount();
            totalCost = totalCost.add(availableBundle.price());
            LOGGER.trace("Amount left: {}, total cost so far: ", amountLeft, totalCost);
            bundles.compute(availableBundle, (b, a) -> a == null ? 1 : a + 1);
        }

        if (amountLeft != 0) {
            throw new NoMatchingBundlesException(amount, amountLeft);
        }

        final BundlingDetails bundling = new BundlingDetails(amount, totalCost, bundles);
        LOGGER.debug("Bundling details: {}", bundling);
        return bundling;
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
