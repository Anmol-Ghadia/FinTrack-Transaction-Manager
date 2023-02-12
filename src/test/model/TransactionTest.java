package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {
    Transaction t1;
    Account checking;
    Account travel;
    LocalDate dateNow;

    @BeforeEach
    public void transactionTestBefore() {
        checking = new Accumulator("Checking","Main checking account *9999");
        travel = new Expense("Travel","Traveling expenses like gas");
        dateNow = LocalDate.parse("2023-02-01");
        t1 = new Transaction(0,checking,travel,
                22, dateNow,
                "Uber","Uber to downtown");
    }

    @Test
    public void transactionTestBasic() {
        // Checks that the object is constructed properly
        assertEquals(0,t1.getTransactionID());
        assertEquals(checking,t1.getFrom());
        assertEquals(travel,t1.getTo());
        assertEquals(22,t1.getAmount());
        assertEquals(dateNow,t1.getDate());
        assertEquals(dateNow.getDayOfMonth(),t1.getDay());
        assertEquals(dateNow.getMonthValue(),t1.getMonth());
        assertEquals(dateNow.getYear(),t1.getYear());
        assertEquals("Uber",t1.getTitle());
        assertEquals("Uber to downtown",t1.getDesc());
    }
}
