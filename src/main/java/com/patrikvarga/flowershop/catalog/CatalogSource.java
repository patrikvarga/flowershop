package com.patrikvarga.flowershop.catalog;

import java.util.List;

/**
 * A catalog source can be used to initialize a flower catalog with all flower data.
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public interface CatalogSource {

    public List<Flower> read();

}
