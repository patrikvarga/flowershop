package com.patrikvarga.flowershop.orders;

import com.patrikvarga.flowershop.catalog.Bundle;
import com.patrikvarga.flowershop.catalog.Flower;
import com.patrikvarga.flowershop.catalog.FlowerNotFoundException;
import com.patrikvarga.flowershop.catalog.Flowers;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OrdersTest {

    private static final BigDecimal SINGLE_PRICE = BigDecimal.ONE;
    private static final BigDecimal BUNDLE_3_PRICE = BigDecimal.valueOf(2L);
    private static final BigDecimal BUNDLE_4_PRICE = BigDecimal.valueOf(3L);
    private static final BigDecimal BUNDLE_7_PRICE = BigDecimal.valueOf(4L);

    private final Bundle bundleOfOne = new Bundle(1, SINGLE_PRICE);
    private final Bundle bundleOfThree = new Bundle(3, BUNDLE_3_PRICE);
    private final Bundle bundleOfFour = new Bundle(4, BUNDLE_4_PRICE);
    private final Bundle bundleOfSeven = new Bundle(7, BUNDLE_7_PRICE);

    private final Flower flowerWithoutBundles = new Flower("DF", "Dummy flower", emptyList());
    private final Flower singleFlower = new Flower("SF", "Single flower", asList(bundleOfOne));
    private final Flower bundledOnlyFlower = new Flower("SBF", "Bundled-only flower", asList(bundleOfThree));
    private final Flower bundledFlower = new Flower("BF", "Bundled flower", asList(bundleOfOne, bundleOfThree));
    private final Flower bundled347Flower = new Flower("BF347", "Bundled 3/4/7 flower", asList(bundleOfThree, bundleOfFour, bundleOfSeven));
    private final Flower bundled47Flower = new Flower("BF47", "Bundled 4/7 flower", asList(bundleOfFour, bundleOfSeven));

    private final Flowers mockFlowers = new Flowers(() -> emptyList());

    private final Orders orders = new Orders(mockFlowers);

    @Test(expected = NullPointerException.class)
    public void failOnNullOrder() {
        orders.bundle(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnEmptyOrder() {
        orders.bundle(new Order());
    }

    @Test(expected = FlowerNotFoundException.class)
    public void failOnUnknownFlower() {
        final Order order = new Order();
        order.addItem("unknown", 42);

        orders.bundle(order);
    }

    @Test(expected = NoMatchingBundlesException.class)
    public void failIfNoBundlesAreDefined() {
        mockFlowers.add(flowerWithoutBundles);

        final int amount = 1;
        final Order order = new Order();
        order.addItem(flowerWithoutBundles.code(), amount);

        orders.bundle(order);
    }

    @Test(expected = NoMatchingBundlesException.class)
    public void failIfCannotFulfillOrderWithSingleDefinedBundle() {
        mockFlowers.add(bundledOnlyFlower);

        final Order order = new Order();
        order.addItem(bundledOnlyFlower.code(), 1);

        orders.bundle(order);
    }

    @Test(expected = NoMatchingBundlesException.class)
    public void failIfCannotFulfillOrderWithMultipleDefinedBundles() {
        mockFlowers.add(bundled47Flower);

        final Order order = new Order();
        final int amount = 13;
        order.addItem(bundled47Flower.code(), amount);

        orders.bundle(order);
    }

    @Test
    public void fulfillSingleFlowerOrder() {
        mockFlowers.add(singleFlower);

        final int amount = 1;
        final Order order = new Order();
        order.addItem(singleFlower.code(), amount);

        final BundledOrder bundledOrder = orders.bundle(order);
        final BundlingDetails details = bundledOrder.detailsOf(singleFlower.code());
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
        mockFlowers.add(singleFlower);

        final Order order = new Order();
        final int amount = 3;
        order.addItem(singleFlower.code(), amount);

        final BundledOrder bundledOrder = orders.bundle(order);
        final BundlingDetails details = bundledOrder.detailsOf(singleFlower.code());
        final Map.Entry<Bundle, Integer> bundleEntry = details.bundles().entrySet().iterator().next();
        final Bundle expectedBundle = new Bundle(1, SINGLE_PRICE);
        final BigDecimal expectedTotalCost = SINGLE_PRICE.multiply(BigDecimal.valueOf(amount));

        assertThat(details.amount(), is(amount));
        assertThat(details.totalCost(), is(expectedTotalCost));
        assertThat(details.bundles().size(), is(1));
        assertThat(bundleEntry.getKey(), is(expectedBundle));
        assertThat(bundleEntry.getValue(), is(amount));
    }

    @Test
    public void fulfillOrderMatchingABundle() {
        mockFlowers.add(bundledOnlyFlower);

        final Order order = new Order();
        final int amount = 3;
        order.addItem(bundledOnlyFlower.code(), amount);

        final BundledOrder bundledOrder = orders.bundle(order);
        final BundlingDetails details = bundledOrder.detailsOf(bundledOnlyFlower.code());
        final Map.Entry<Bundle, Integer> bundleEntry = details.bundles().entrySet().iterator().next();
        final Bundle expectedBundle = new Bundle(3, BUNDLE_3_PRICE);

        assertThat(details.amount(), is(amount));
        assertThat(details.totalCost(), is(BUNDLE_3_PRICE));
        assertThat(details.bundles().size(), is(1));
        assertThat(bundleEntry.getKey(), is(expectedBundle));
        assertThat(bundleEntry.getValue(), is(1));
    }

    @Test
    public void fulfillOrderWithBiggerBundle() {
        mockFlowers.add(bundledFlower);

        final Order order = new Order();
        final int amount = 3;
        order.addItem(bundledFlower.code(), amount);

        final BundledOrder bundledOrder = orders.bundle(order);
        final BundlingDetails details = bundledOrder.detailsOf(bundledFlower.code());
        final Map.Entry<Bundle, Integer> bundleEntry = details.bundles().entrySet().iterator().next();
        final Bundle expectedBundle = new Bundle(3, BUNDLE_3_PRICE);

        assertThat(details.amount(), is(amount));
        assertThat(details.totalCost(), is(BUNDLE_3_PRICE));
        assertThat(details.bundles().size(), is(1));
        assertThat(bundleEntry.getKey(), is(expectedBundle));
        assertThat(bundleEntry.getValue(), is(1));
    }

    @Test
    public void fulfillOrderWithMixedBundles() {
        mockFlowers.add(bundledFlower);

        final Order order = new Order();
        final int amount = 4;
        order.addItem(bundledFlower.code(), amount);

        final BundledOrder bundledOrder = orders.bundle(order);
        final BundlingDetails details = bundledOrder.detailsOf(bundledFlower.code());
        final Iterator<Map.Entry<Bundle, Integer>> bundleIterator = details.bundles().entrySet().iterator();
        final Map.Entry<Bundle, Integer> bundleEntry3 = bundleIterator.next();
        final Map.Entry<Bundle, Integer> bundleEntry1 = bundleIterator.next();
        final Bundle expectedBundle3 = new Bundle(3, BUNDLE_3_PRICE);
        final Bundle expectedBundle1 = new Bundle(1, SINGLE_PRICE);
        final BigDecimal expectedTotalCost = SINGLE_PRICE.add(BUNDLE_3_PRICE);

        assertThat(details.amount(), is(amount));
        assertThat(details.totalCost(), is(expectedTotalCost));
        assertThat(details.bundles().size(), is(2));

        assertThat(bundleEntry3.getKey(), is(expectedBundle3));
        assertThat(bundleEntry3.getValue(), is(1));

        assertThat(bundleEntry1.getKey(), is(expectedBundle1));
        assertThat(bundleEntry1.getValue(), is(1));
    }

    @Test
    public void fulfillOrderWithMinimumNumberOfBundles() {
        mockFlowers.add(bundled347Flower);

        final Order order = new Order();
        final int amount = 7;
        order.addItem(bundled347Flower.code(), amount);

        final BundledOrder bundledOrder = orders.bundle(order);
        final BundlingDetails details = bundledOrder.detailsOf(bundled347Flower.code());
        final Map.Entry<Bundle, Integer> bundleEntry = details.bundles().entrySet().iterator().next();
        final Bundle expectedBundle = new Bundle(7, BUNDLE_7_PRICE);

        assertThat(details.amount(), is(amount));
        assertThat(details.totalCost(), is(BUNDLE_7_PRICE));
        assertThat(details.bundles().size(), is(1));
        assertThat(bundleEntry.getKey(), is(expectedBundle));
        assertThat(bundleEntry.getValue(), is(1));
    }

}
