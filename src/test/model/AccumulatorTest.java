package model;

import exceptions.AccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/*
    Test class for Accumulator and abstract class: Account
*/
public class AccumulatorTest {
    Account checking;
    Account travel;
    Account food;
    Account savings;
    Transaction t1;
    Transaction t2;
    Transaction t3;

    @BeforeEach
    public void accumulatorTestBefore() {
        travel = new Expense("TRAVEL", "Travel expenses like gas");
        food = new Expense("FOOD","Expenses like groceries, meals");
        checking = new Accumulator("CHECKING","ScotiaBank Checking account *7492");
        savings = new Accumulator("SAVINGS","BMO Savings account *9876");
        t1 = new Transaction(0, checking,food,
                15, LocalDate.parse("2022-02-01"),
                "TimHortons","Tim Hortons for Breakfast");
        t2 = new Transaction(1, checking,travel,
                20, LocalDate.parse("2022-02-02"),
                "PetroCanada","Filled gas in BMW");
        t3 = new Transaction(2, savings,checking,
                100,LocalDate.parse("2022-02-02"),
                "Transferred from savings","Transferred $100 from savings due to low balance");
    }

    @Test
    public void accumulatorTestAccountType() {
        assertEquals("ACCUMULATOR", checking.getAccountType());
        assertEquals("CHECKING",checking.getAccountName());
        assertEquals("ScotiaBank Checking account *7492",checking.getAccountDesc());
    }

    @Test
    public void accumulatorTestAddTransaction() {
        assertEquals(0,checking.getTransaction().size());
        checking.addTransaction(t1);
        assertEquals(1,checking.getTransaction().size());
        assertEquals(t1,checking.getTransactionByID(0));
        assertEquals(t1,checking.getLastTransaction());
        assertEquals(t1,checking.getTransaction(0));
        checking.addTransaction(t2);
        assertEquals(2,checking.getTransaction().size());
        assertEquals(t1,checking.getTransactionByID(0));
        assertEquals(t2,checking.getTransactionByID(1));
        assertEquals(t2,checking.getLastTransaction());
        assertEquals(t1,checking.getTransaction(1));
        assertEquals(t2,checking.getTransaction(0));
        checking.addTransaction(t3);
        assertEquals(3,checking.getTransaction().size());
        assertEquals(t1,checking.getTransactionByID(0));
        assertEquals(t2,checking.getTransactionByID(1));
        assertEquals(t3,checking.getTransactionByID(2));
        assertEquals(t1,checking.getTransaction(2));
        assertEquals(t2,checking.getTransaction(1));
        assertEquals(t3,checking.getTransaction(0));
        assertEquals(t3,checking.getLastTransaction());
    }

    @Test
    public void accountTestDebit() {
        checking.addTransaction(t1);
        checking.addTransaction(t2);
        checking.addTransaction(t3);
        assertEquals(100, checking.getDebit());
    }

    @Test
    public void accountTestCredit() {
        checking.addTransaction(t1);
        checking.addTransaction(t2);
        checking.addTransaction(t3);
        assertEquals(15+20, checking.getCredit());
    }

    @Test
    public void accountTestBalance() {
        checking.addTransaction(t1);
        checking.addTransaction(t2);
        checking.addTransaction(t3);
        assertEquals(-15-20+100, checking.getBalance());
    }

    @Test
    public void accountTestDeleteTransaction() {
        checking.addTransaction(t1);
        checking.addTransaction(t2);
        assertFalse(checking.deleteTransaction(t3));
        assertTrue(checking.deleteTransaction(t2));
        checking.addTransaction(t2);
        assertTrue(checking.deleteTransaction(t1));
    }

    @Test
    public void accountTestSetName() {
        String newName = "CHECKING-BMO";
        checking.setName(newName);
        assertEquals(newName,checking.getAccountName());
    }

    @Test
    public void accountTestSetDesc() {
        String newDesc = "this is a new placeholder description";
        checking.setDesc(newDesc);
        assertEquals(newDesc,checking.getAccountDesc());
    }
}
