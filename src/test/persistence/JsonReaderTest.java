package persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *   Test class for Json Reader
 */
public class JsonReaderTest {
    JsonReader reader;

    @BeforeEach
    public void jsonReaderTestBefore() {
        reader = new JsonReader("./data/jsonReaderTest.json");
    }

    @Test
    public void jsonReaderTestDestination() {
        assertEquals("./data/jsonReaderTest.json",reader.getDestination());
    }
}
