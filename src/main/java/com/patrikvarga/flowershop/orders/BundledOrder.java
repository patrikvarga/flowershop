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
}
