package com.patrikvarga.flowershop.catalog;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;

/**
 * A flower bundle used to fulfill orders.
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class Bundle implements Comparable<Bundle> {

    private static final Comparator<Bundle> AMOUNT_COMPARATOR = Comparator.comparingInt(Bundle::amount);

    private final int amount;
    private final BigDecimal price;

    private Bundle() {
        // for Jackson :(
        this.amount = 0;
        this.price = null;
    }

    public Bundle(final int amount, final BigDecimal price) {
        this.amount = amount;
        this.price = price;
    }

    public int amount() {
        return amount;
    }

    public BigDecimal price() {
        return price;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.amount;
        hash = 29 * hash + Objects.hashCode(this.price);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bundle other = (Bundle) obj;
        if (this.amount != other.amount) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bundle{" + "amount=" + amount + ", price=" + price + '}';
    }

    @Override
    public int compareTo(final Bundle other) {
        return AMOUNT_COMPARATOR.compare(this, other);
    }

}
