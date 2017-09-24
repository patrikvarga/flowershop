package com.patrikvarga.flowershop.orders;

/**
 * This will be thrown when an order cannot be exactly fulfilled because no matching bundle combinations are possible.
 * <p>
 * For example, if there is a flower that is sold only in bundles of 3, then ordering 1, 2, or 4 flowers (or any other
 * amount that is not divisible by 3) will throw this exception.
 * </p>
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class NoMatchingBundlesException extends RuntimeException {

    private final int amountWanted;
    private final int amountLeft;

    public NoMatchingBundlesException(int amountWanted, int amountLeft) {
        super(amountWanted + " wanted but no mathcing bundles for the last " + amountLeft);
        this.amountWanted = amountWanted;
        this.amountLeft = amountLeft;
    }

    public int amountWanted() {
        return amountWanted;
    }

    public int amountLeft() {
        return amountLeft;
    }

}
