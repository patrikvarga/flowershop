package com.patrikvarga.flowershop.orders;

import com.patrikvarga.flowershop.catalog.Bundle;
import com.patrikvarga.flowershop.catalog.Flower;
import com.patrikvarga.flowershop.catalog.Flowers;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static java.util.Arrays.asList;

public class OrdersTest {

    private final Bundle bundleOfOne = new Bundle(1, BigDecimal.ONE);
    private final Bundle bundleOfThree = new Bundle(3, BigDecimal.valueOf(2L));

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

}
