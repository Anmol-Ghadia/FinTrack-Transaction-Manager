package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccumulatorTest {
    Account acc1;

    @BeforeEach
    public void accumulatorTestBefore() {
        acc1 = new Accumulator("Person 1","Trustworthy person");
    }

    @Test
    public void accumulatorTestAccountType() {
        assertEquals("ACCUMULATOR",acc1.getAccountType());
    }
}
