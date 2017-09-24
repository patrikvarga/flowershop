package com.patrikvarga.flowershop.orders;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A full order containing the ordered amount per product.
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class Order {

    private final Map<String, Integer> items = new HashMap<>();

    public Order() {
    }

    public Map<String, Integer> items() {
        return items;
    }

    public void addItem(final String productCode, final int amount) {
        items.put(productCode, amount);
    }

    public void removeItem(final String productCode) {
        items.remove(productCode);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.items);
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
        final Order other = (Order) obj;
        if (!Objects.equals(this.items, other.items)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Order{" + "items=" + items + '}';
    }

}
