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
public class NoMatchingBundlesException extends Exception {

}
