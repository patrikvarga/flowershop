package com.patrikvarga.flowershop.orders;

import com.patrikvarga.flowershop.catalog.Bundle;
import java.math.BigDecimal;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OrdersTest {

    private static final BigDecimal DOLLARS_12_99 = toDollars(1299);
    private static final BigDecimal DOLLARS_41_90 = toDollars(4190);
    private static final BigDecimal DOLLARS_24_95 = toDollars(2495);
    private static final BigDecimal DOLLARS_16_95 = toDollars(1695);
    private static final BigDecimal DOLLARS_2_585 = toDollars(2585);
    private static final BigDecimal DOLLARS_9_95 = toDollars(995);
    private static final BigDecimal DOLLARS_5_95 = toDollars(595);

    private static final String TULIPS_CODE = "T58";
    private static final String LILIES_CODE = "L09";
    private static final String ROSES_CODE = "R12";

    private final Orders orders = new Orders();

    private static BigDecimal toDollars(final long cents) {
        return BigDecimal.valueOf(cents, 2);
    }

    @Test
    public void demonstrateDocumentationExample() {
        final Order order = new Order();
        order.addItem(ROSES_CODE, 10);
        order.addItem(LILIES_CODE, 15);
        order.addItem(TULIPS_CODE, 13);

        final BundledOrder bundledOrder = orders.bundle(order);
        final BundlingDetails rosesDetails = bundledOrder.detailsOf(ROSES_CODE);
        final BundlingDetails liliesDetails = bundledOrder.detailsOf(LILIES_CODE);
        final BundlingDetails tulipsDetails = bundledOrder.detailsOf(TULIPS_CODE);

        assertThat(rosesDetails.amount(), is(10));
        assertThat(rosesDetails.totalCost(), is(DOLLARS_12_99));
        assertThat(rosesDetails.bundles().size(), is(1));
        assertThat(rosesDetails.bundles().get(new Bundle(10, DOLLARS_12_99)), is(1));

        assertThat(liliesDetails.amount(), is(15));
        assertThat(liliesDetails.totalCost(), is(DOLLARS_41_90));
        assertThat(liliesDetails.bundles().size(), is(2));
        assertThat(liliesDetails.bundles().get(new Bundle(9, DOLLARS_24_95)), is(1));
        assertThat(liliesDetails.bundles().get(new Bundle(6, DOLLARS_16_95)), is(1));

        assertThat(tulipsDetails.amount(), is(13));
        assertThat(tulipsDetails.totalCost(), is(DOLLARS_2_585));
        assertThat(tulipsDetails.bundles().size(), is(2));
        assertThat(tulipsDetails.bundles().get(new Bundle(5, DOLLARS_9_95)), is(2));
        assertThat(tulipsDetails.bundles().get(new Bundle(3, DOLLARS_5_95)), is(1));
    }
}
