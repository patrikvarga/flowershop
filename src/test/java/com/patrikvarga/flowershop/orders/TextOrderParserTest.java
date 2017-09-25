package com.patrikvarga.flowershop.orders;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TextOrderParserTest {

    private static final String TULIPS_CODE = "T58";
    private static final String LILIES_CODE = "L09";
    private static final String ROSES_CODE = "R12";

    private final TextOrderParser parser = new TextOrderParser();

    @Test
    public void readValidExampleCorrectly() throws URISyntaxException, IOException {
        final URI exampleFile = getClass().getResource("/example_input.txt").toURI();
        final Order orderInFile = parser.readOrder(Paths.get(exampleFile));

        final Order expectedOrder = new Order();
        expectedOrder.addItem(ROSES_CODE, 10);
        expectedOrder.addItem(LILIES_CODE, 15);
        expectedOrder.addItem(TULIPS_CODE, 13);

        assertThat(orderInFile, is(expectedOrder));
    }

}
