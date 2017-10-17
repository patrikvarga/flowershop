package com.patrikvarga.flowershop.orders;

import com.patrikvarga.flowershop.catalog.Bundle;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * A breakdown of a single product order into a number of bundles.
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class BundlingDetails {

    private final int amount;
    private final BigDecimal totalCost;
    private final Map<Bundle, Integer> bundles;

    public BundlingDetails(
            final int amount,
            final BigDecimal totalCost,
            final Map<Bundle, Integer> bundles
    ) {
        this.amount = amount;
        this.totalCost = totalCost;
        this.bundles = bundles;
    }

    public BigDecimal totalCost() {
        return totalCost;
    }

    public int amount() {
        return amount;
    }

    public Map<Bundle, Integer> bundles() {
        return new HashMap<>(bundles);
    }

    @Override
    public String toString() {
        return "BundlingDetails{" + "amount=" + amount + ", totalCost=" + totalCost + ", bundles=" + bundles + '}';
    }

}
