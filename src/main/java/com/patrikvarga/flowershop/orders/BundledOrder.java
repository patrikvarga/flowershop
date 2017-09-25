package com.patrikvarga.flowershop.orders;

import java.util.HashMap;
import java.util.Map;

/**
 * A complete breakdown of an order into bundling details per product.
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class BundledOrder {

    private final Map<String, BundlingDetails> detailsPerProduct = new HashMap<>();

    public BundlingDetails detailsOf(final String productCode) {
        return detailsPerProduct.get(productCode);
    }

    public void addDetails(final String productCode, final BundlingDetails details) {
        detailsPerProduct.put(productCode, details);
    }

    @Override
    public String toString() {
        return "BundledOrder{" + "detailsPerProduct=" + detailsPerProduct + '}';
    }

    public String output() {
        final StringBuilder sb = new StringBuilder();

        detailsPerProduct.forEach((productCode, details) -> {
            sb
                    .append("\n")
                    .append(details.amount())
                    .append(" ")
                    .append(productCode)
                    .append(" $")
                    .append(details.totalCost());

            details.bundles().forEach((bundle, amount) -> {
                sb
                        .append("\n\t")
                        .append(amount)
                        .append(" x ")
                        .append(bundle.amount())
                        .append(" $")
                        .append(bundle.price());
            });
        });

        return sb.toString();
    }

}
