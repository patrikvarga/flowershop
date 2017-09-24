package com.patrikvarga.flowershop.orders;

import com.patrikvarga.flowershop.catalog.Bundle;
import com.patrikvarga.flowershop.catalog.Flower;
import com.patrikvarga.flowershop.catalog.Flowers;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

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

}
