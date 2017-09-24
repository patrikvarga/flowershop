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
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;
import static java.util.stream.Collectors.toList;

/**
 * The flower catalog.
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class Flowers {

    private static final Logger LOGGER = LogManager.getLogger(Flowers.class);

    private static final String CATALOG_DIR = "flowers";

    private final List<Flower> flowers;
    private final ObjectMapper mapper;

    public Flowers() throws IOException, URISyntaxException {
        mapper = new ObjectMapper();
        mapper.registerModule(new ParameterNamesModule());
        mapper.setVisibility(FIELD, ANY);

        final URI flowerResourceDir = getClass().getClassLoader().getResource(CATALOG_DIR).toURI();

        flowers = Files.list(Paths.get(flowerResourceDir))
                .map(Path::toUri)
                .map(this::toUrl)
                .map(this::readFlowerDetails)
                .collect(toList());

        LOGGER.info("Flowers in catalog: {}", flowers);
    }

    private Flower readFlowerDetails(final URL url) {
        LOGGER.debug("Reading {}", url);
        try {
            return mapper.readValue(url, Flower.class);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private URL toUrl(final URI uri) {
        try {
            return uri.toURL();
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Flower> findAll() {
        return flowers;
    }

}
