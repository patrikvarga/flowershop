package com.patrikvarga.flowershop.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.util.stream.Collectors.toMap;

/**
 * The flower catalog, ordered by the flower's natural ordering.
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class Flowers {

    private static final Logger LOGGER = LogManager.getLogger(Flowers.class);

    private final Map<String, Flower> flowers;

    public Flowers(final CatalogSource source) {
        final List<Flower> flowerList = source.read();

        flowers = flowerList.stream().collect(toMap(Flower::code, Function.identity()));

        LOGGER.info("Flowers in catalog: {}", flowers);
    }

    public void add(final Flower flower) {
        flowers.putIfAbsent(flower.code(), flower);
        LOGGER.info("Flower added: {}", flower);
    }

    public void remove(final String productCode) {
        final Flower removed = flowers.remove(productCode);
        LOGGER.info("Flower removed: {}", removed);
    }

    public List<Flower> findAll() {
        return new ArrayList<>(flowers.values());
    }

    public Flower find(final String productCode) {
        final Flower foundFlower = flowers.get(productCode);
        if (foundFlower == null) {
            throw new FlowerNotFoundException(productCode);
        }
        return foundFlower;
    }
}
