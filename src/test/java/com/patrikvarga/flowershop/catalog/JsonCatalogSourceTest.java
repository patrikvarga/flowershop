package com.patrikvarga.flowershop.catalog;

import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JsonCatalogSourceTest {

    public static final BigDecimal DOLLARS_5_95 = toDollars(595);
    public static final BigDecimal DOLLARS_6_99 = toDollars(699);
    public static final BigDecimal DOLLARS_9_95 = toDollars(995);
    public static final BigDecimal DOLLARS_12_99 = toDollars(1299);
    public static final BigDecimal DOLLARS_16_95 = toDollars(1695);
    public static final BigDecimal DOLLARS_16_99 = toDollars(1699);
    public static final BigDecimal DOLLARS_24_95 = toDollars(2495);
    public static final BigDecimal DOLLARS_25_85 = toDollars(2585);
    public static final BigDecimal DOLLARS_41_90 = toDollars(4190);

    private final JsonCatalogSource catalog;

    public JsonCatalogSourceTest() {
        this.catalog = new JsonCatalogSource();
    }

    private static BigDecimal toDollars(final long cents) {
        return BigDecimal.valueOf(cents, 2);
    }

    @Test
    public void initializeCatalogFromJson() {
        final List<Flower> flowers = catalog.read();

        final Flower flower1 = flowers.get(0);
        final List<Bundle> bundles1 = flower1.bundles();
        final Flower flower2 = flowers.get(1);
        final List<Bundle> bundles2 = flower2.bundles();
        final Flower flower3 = flowers.get(2);
        final List<Bundle> bundles3 = flower3.bundles();

        assertThat(flowers.size(), is(3));

        assertThat(flower1.name, is("Lilies"));
        assertThat(bundles1.size(), is(3));
        assertThat(bundles1.get(0).amount, is(3));
        assertThat(bundles1.get(0).price, is(DOLLARS_9_95));
        assertThat(bundles1.get(1).amount, is(6));
        assertThat(bundles1.get(1).price, is(DOLLARS_16_95));
        assertThat(bundles1.get(2).amount, is(9));
        assertThat(bundles1.get(2).price, is(DOLLARS_24_95));

        assertThat(flower2.name, is("Roses"));
        assertThat(bundles2.size(), is(2));
        assertThat(bundles2.get(0).amount, is(5));
        assertThat(bundles2.get(0).price, is(DOLLARS_6_99));
        assertThat(bundles2.get(1).amount, is(10));
        assertThat(bundles2.get(1).price, is(DOLLARS_12_99));

        assertThat(flower3.name, is("Tulips"));
        assertThat(bundles3.size(), is(3));
        assertThat(bundles3.get(0).amount, is(3));
        assertThat(bundles3.get(0).price, is(DOLLARS_5_95));
        assertThat(bundles3.get(1).amount, is(5));
        assertThat(bundles3.get(1).price, is(DOLLARS_9_95));
        assertThat(bundles3.get(2).amount, is(9));
        assertThat(bundles3.get(2).price, is(DOLLARS_16_99));

    }

}
