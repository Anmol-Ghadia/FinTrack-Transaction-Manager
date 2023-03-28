package persistence;

import model.Account;
import model.Accumulator;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*
    Test class for Json Writer
*/
public class JsonWriterTest {
    JsonWriter writer;

    @BeforeEach
    public void jsonWriterTestBefore() {
        writer = new JsonWriter("./data/jsonWriterTest.json");
    }

    @Test
    public void jsonWriterTestDestination() {
        assertEquals("./data/jsonWriterTest.json",writer.getDestination());
    }

    @Test
    public void jsonWriterTestWrite() {
        User user = new User();
        Account acc1 = new Accumulator("CASH","CASH at home");
        user.addAccumulator(acc1);
        try {
            writer.write(user);
        } catch (FileNotFoundException e) {
            fail();
        }
        JsonReader reader = new JsonReader("./data/jsonWriterTest.json");
        try {
            User check = reader.read();
            assertEquals("CASH",check.getAccumulator().get(0).getAccountName());
            assertEquals("CASH at home",check.getAccumulator().get(0).getAccountDesc());
            assertEquals(0,check.getExpense().size());
        } catch (IOException e) {
            fail();
        }
    }
}