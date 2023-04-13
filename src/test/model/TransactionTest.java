package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 *   Test class for Transaction class
 */
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

    @Test
    public void transactionTestSetters() {
        t1.setFrom(travel);
        assertEquals(travel,t1.getFrom());
        t1.setTo(checking);
        assertEquals(checking,t1.getTo());
        t1.setAmount(120);
        assertEquals(120,t1.getAmount());
        LocalDate date2 = LocalDate.parse("2022-01-15");
        t1.setDate(date2);
        assertEquals(date2,t1.getDate());
        assertEquals(15,t1.getDay());
        assertEquals(1,t1.getMonth());
        assertEquals(2022,t1.getYear());
        t1.setTitle("Uber refund");
        assertEquals("Uber refund",t1.getTitle());
        t1.setDesc("Uber returned money for unsatisfactory ride");
        assertEquals("Uber returned money for unsatisfactory ride",t1.getDesc());
    }

    @Test
    public void transactionTestTemporary() {
        assertNull(checking.getTransactionByID(999));
    }

    @Test
    public void transactionTestToJson() {
        JSONObject expect = new JSONObject();
        JSONObject check = t1.toJson();
        expect.put("id",t1.getTransactionID());
        expect.put("from",t1.getFrom().getAccountName());
        expect.put("to",t1.getTo().getAccountName());
        expect.put("amount",t1.getAmount());
        expect.put("date",t1.getDate().toString()); // may cause errors when reading, make sure the format is readable
        expect.put("title",t1.getTitle());
        expect.put("desc",t1.getDesc());
        assertEquals(expect.toString(),check.toString());
    }
}
