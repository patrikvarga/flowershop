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

    private final Orders orders = new Orders();

    private static BigDecimal toDollars(final long cents) {
        return BigDecimal.valueOf(cents, 2);
    }

    @Test
    public void demonstrateDocumentationExample() {
        final Order order = new Order();
        order.addItem("R12", 10);
        order.addItem("L09", 15);
        order.addItem("T58", 13);

        final BundledOrder bundledOrder = orders.bundle(order);
        final BundlingDetails rosesDetails = bundledOrder.detailsOf("R12");
        final BundlingDetails liliesDetails = bundledOrder.detailsOf("L09");
        final BundlingDetails tulipsDetails = bundledOrder.detailsOf("T58");

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
