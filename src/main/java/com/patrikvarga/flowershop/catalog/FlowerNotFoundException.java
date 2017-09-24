package com.patrikvarga.flowershop.catalog;

/**
 * Thrown when referencing a non-existent flower (i.e. non-existent product code).
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class FlowerNotFoundException extends RuntimeException {

    private final String productCode;

    public FlowerNotFoundException(String productCode) {
        super("Invalid product code:" + productCode);
        this.productCode = productCode;
    }

    public String productCode() {
        return productCode;
    }
}
