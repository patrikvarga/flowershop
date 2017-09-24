package com.patrikvarga.flowershop.catalog;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The flower catalog, ordered by the flower's natural ordering.
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class Flowers {

    private static final Logger LOGGER = LogManager.getLogger(Flowers.class);

    private final List<Flower> flowers;

    public Flowers(final CatalogSource source) {
        flowers = source.read();

        LOGGER.info("Flowers in catalog: {}", flowers);
    }

    public List<Flower> findAll() {
        return flowers;
    }

}
