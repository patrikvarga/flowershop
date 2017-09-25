package com.patrikvarga.flowershop.orders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A source of orders
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class TextOrderParser {

    private static class Item {

        private final String code;
        private final int amount;

        public Item(String code, int amount) {
            this.code = code;
            this.amount = amount;
        }

    }

    public Order readOrder(final Path path) throws IOException {
        final Order order = new Order();
        Files.lines(path).map(this::lineToItem).forEach(item
                -> order.addItem(item.code, item.amount)
        );
        return order;
    }

    private Item lineToItem(final String line) {
        // error-handling will be needed...
        final String[] split = line.split("\\s+");
        final String code = split[1];
        final int amount = Integer.valueOf(split[0]);
        return new Item(code, amount);
    }
}
