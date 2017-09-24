package com.patrikvarga.flowershop.catalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;
import static java.util.stream.Collectors.toList;

/**
 * Catalog source of a JSON file included in the deployment.
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class JsonCatalogSource implements CatalogSource {

    private static final Logger LOGGER = LogManager.getLogger(JsonCatalogSource.class);

    private static final String CATALOG_DIR = "flowers";

    private final ObjectMapper mapper;

    public JsonCatalogSource() {
        mapper = new ObjectMapper();
        mapper.registerModule(new ParameterNamesModule());
        mapper.setVisibility(FIELD, ANY);
    }

    private Flower readFlowerDetails(final URL url) {
        LOGGER.debug("Reading {}", url);
        try {
            return mapper.readValue(url, Flower.class);
        } catch (IOException ex) {
            // Should not happen. If it does, pick a more appropriate runtime exception.
            throw new RuntimeException(ex);
        }
    }

    private URL toUrl(final URI uri) {
        try {
            return uri.toURL();
        } catch (MalformedURLException ex) {
            // Should not happen. If it does, pick a more appropriate runtime exception.
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Flower> read() {
        try {
            final URI flowerResourceDir = getClass().getClassLoader().getResource(CATALOG_DIR).toURI();

            final List<Flower> flowers = Files.list(Paths.get(flowerResourceDir))
                    .map(Path::toUri)
                    .map(this::toUrl)
                    .map(this::readFlowerDetails)
                    .collect(toList());

            Collections.sort(flowers);

            return flowers;
        } catch (IOException | URISyntaxException ex) {
            // Should not happen. If it does, pick a more appropriate runtime exception.
            throw new RuntimeException(ex);
        }
    }

}
