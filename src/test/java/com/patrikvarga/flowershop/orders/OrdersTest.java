package com.patrikvarga.flowershop.orders;

import com.patrikvarga.flowershop.catalog.Bundle;
import com.patrikvarga.flowershop.catalog.Flower;
import com.patrikvarga.flowershop.catalog.Flowers;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OrdersTest {

    private static final BigDecimal SINGLE_PRICE = BigDecimal.ONE;
    private static final BigDecimal BUNDLE_PRICE = BigDecimal.valueOf(2L);

    private final Bundle bundleOfOne = new Bundle(1, SINGLE_PRICE);
    private final Bundle bundleOfThree = new Bundle(3, BUNDLE_PRICE);

    private final Flower flowerWithoutBundles = new Flower("DF", "Dummy flower", emptyList());
    private final Flower singleFlower = new Flower("SF", "Single flower", asList(bundleOfOne));
    private final Flower bundledOnlyFlower = new Flower("SBF", "Bundled-only flower", asList(bundleOfThree));
    private final Flower bundledFlower = new Flower("BF", "Bundled flower", asList(bundleOfOne, bundleOfThree));

    private List<Flower> mockFlowerList = new ArrayList<>();
    private final Flowers mockFlowers = new Flowers(() -> mockFlowerList);

    private final Orders orders = new Orders(mockFlowers);

    @Test(expected = NullPointerException.class)
    public void failOnNullOrder() {
        orders.bundle(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnEmptyOrder() {
        orders.bundle(new Order());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnUnknownFlower() {
        final Order order = new Order();
        order.addItem("unknown", 42);

        orders.bundle(order);
    }

    @Test(expected = NoMatchingBundlesException.class)
    public void failIfNoBundlesAreDefined() {
        mockFlowerList.add(flowerWithoutBundles);

        final int amount = 1;
        final Order order = new Order();
        order.addItem(flowerWithoutBundles.code, amount);

        orders.bundle(order);
    }

    @Test(expected = NoMatchingBundlesException.class)
    public void failIfCannotFulfillOrderWithDefinedBundles() {
        mockFlowerList.add(bundledOnlyFlower);

        final Order order = new Order();
        order.addItem(singleFlower.code, 1);

        orders.bundle(order);
    }

    @Test
    public void fulfillSingleFlowerOrder() {
        mockFlowerList.add(singleFlower);

        final int amount = 1;
        final Order order = new Order();
        order.addItem(singleFlower.code, amount);

        final BundledOrder bundledOrder = orders.bundle(order);
        final BundlingDetails details = bundledOrder.detailsOf(singleFlower.code);
        final Map.Entry<Bundle, Integer> bundleEntry = details.bundles().entrySet().iterator().next();
        final Bundle expectedBundle = new Bundle(amount, SINGLE_PRICE);

        assertThat(details.amount(), is(amount));
        assertThat(details.totalCost(), is(SINGLE_PRICE));
        assertThat(details.bundles().size(), is(amount));
        assertThat(bundleEntry.getKey(), is(expectedBundle));
        assertThat(bundleEntry.getValue(), is(amount));
    }

    @Test
    public void fulfillOrderWithBundlesOfOne() {
        mockFlowerList.add(singleFlower);

        final Order order = new Order();
        final int amount = 3;
        order.addItem(singleFlower.code, amount);

        final BundledOrder bundledOrder = orders.bundle(order);
        final BundlingDetails details = bundledOrder.detailsOf(singleFlower.code);
        final Map.Entry<Bundle, Integer> bundleEntry = details.bundles().entrySet().iterator().next();
        final Bundle expectedBundle = new Bundle(1, SINGLE_PRICE);
        final BigDecimal expectedTotalCost = SINGLE_PRICE.multiply(BigDecimal.valueOf(amount));

        assertThat(details.amount(), is(amount));
        assertThat(details.totalCost(), is(expectedTotalCost));
        assertThat(details.bundles().size(), is(amount));
        assertThat(bundleEntry.getKey(), is(expectedBundle));
        assertThat(bundleEntry.getValue(), is(amount));
    }

    @Test
    public void fulfillOrderMatchingABundle() {
        mockFlowerList.add(bundledOnlyFlower);

        final Order order = new Order();
        final int amount = 3;
        order.addItem(singleFlower.code, amount);

        final BundledOrder bundledOrder = orders.bundle(order);
        final BundlingDetails details = bundledOrder.detailsOf(singleFlower.code);
        final Map.Entry<Bundle, Integer> bundleEntry = details.bundles().entrySet().iterator().next();
        final Bundle expectedBundle = new Bundle(1, BUNDLE_PRICE);

        assertThat(details.amount(), is(amount));
        assertThat(details.totalCost(), is(BUNDLE_PRICE));
        assertThat(details.bundles().size(), is(amount));
        assertThat(bundleEntry.getKey(), is(expectedBundle));
        assertThat(bundleEntry.getValue(), is(amount));
    }

    @Test
    public void fulfillOrderWithBiggerBundle() {
        mockFlowerList.add(bundledFlower);

        final Order order = new Order();
        final int amount = 3;
        order.addItem(singleFlower.code, amount);

        final BundledOrder bundledOrder = orders.bundle(order);
        final BundlingDetails details = bundledOrder.detailsOf(singleFlower.code);
        final Map.Entry<Bundle, Integer> bundleEntry = details.bundles().entrySet().iterator().next();
        final Bundle expectedBundle = new Bundle(1, BUNDLE_PRICE);

        assertThat(details.amount(), is(amount));
        assertThat(details.totalCost(), is(BUNDLE_PRICE));
        assertThat(details.bundles().size(), is(amount));
        assertThat(bundleEntry.getKey(), is(expectedBundle));
        assertThat(bundleEntry.getValue(), is(amount));
    }

    @Test
    public void fulfillOrderWithMixedBundles() {
        mockFlowerList.add(bundledFlower);

        final Order order = new Order();
        final int amount = 4;
        order.addItem(singleFlower.code, amount);

        final BundledOrder bundledOrder = orders.bundle(order);
        final BundlingDetails details = bundledOrder.detailsOf(singleFlower.code);
        final Iterator<Map.Entry<Bundle, Integer>> bundleIterator = details.bundles().entrySet().iterator();
        final Map.Entry<Bundle, Integer> bundleEntry3 = bundleIterator.next();
        final Map.Entry<Bundle, Integer> bundleEntry1 = bundleIterator.next();
        final Bundle expectedBundle3 = new Bundle(3, BUNDLE_PRICE);
        final Bundle expectedBundle1 = new Bundle(1, BUNDLE_PRICE);
        final BigDecimal expectedTotalCost = SINGLE_PRICE.add(BUNDLE_PRICE);

        assertThat(details.amount(), is(amount));
        assertThat(details.totalCost(), is(expectedTotalCost));
        assertThat(details.bundles().size(), is(2));

        assertThat(bundleEntry3.getKey(), is(expectedBundle3));
        assertThat(bundleEntry3.getValue(), is(1));

        assertThat(bundleEntry1.getKey(), is(expectedBundle1));
        assertThat(bundleEntry1.getValue(), is(1));
    }

}
