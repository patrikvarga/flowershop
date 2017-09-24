package com.patrikvarga.flowershop.orders;

import com.patrikvarga.flowershop.catalog.Bundle;
import com.patrikvarga.flowershop.catalog.Flowers;
import com.patrikvarga.flowershop.catalog.JsonCatalogSource;
import org.junit.Test;

import static com.patrikvarga.flowershop.catalog.JsonCatalogSourceTest.DOLLARS_12_99;
import static com.patrikvarga.flowershop.catalog.JsonCatalogSourceTest.DOLLARS_16_95;
import static com.patrikvarga.flowershop.catalog.JsonCatalogSourceTest.DOLLARS_24_95;
import static com.patrikvarga.flowershop.catalog.JsonCatalogSourceTest.DOLLARS_25_85;
import static com.patrikvarga.flowershop.catalog.JsonCatalogSourceTest.DOLLARS_41_90;
import static com.patrikvarga.flowershop.catalog.JsonCatalogSourceTest.DOLLARS_5_95;
import static com.patrikvarga.flowershop.catalog.JsonCatalogSourceTest.DOLLARS_9_95;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * This is to demonstrate the example from the documentation using the real JSON catalog.
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class OrdersExampleTest {

    private static final String TULIPS_CODE = "T58";
    private static final String LILIES_CODE = "L09";
    private static final String ROSES_CODE = "R12";

    private final Orders orders = new Orders(new Flowers(new JsonCatalogSource()));

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
        assertThat(tulipsDetails.totalCost(), is(DOLLARS_25_85));
        assertThat(tulipsDetails.bundles().size(), is(2));
        assertThat(tulipsDetails.bundles().get(new Bundle(5, DOLLARS_9_95)), is(2));
        assertThat(tulipsDetails.bundles().get(new Bundle(3, DOLLARS_5_95)), is(1));
    }
}
