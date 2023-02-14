package model;

import exceptions.AccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    User user1;
    Account acc1;
    Account exp1;
    Account inc1;
    Account lon1;
    Account acc2;
    Account exp2;
    Account inc2;
    Account lon2;
    Transaction t1;

    @BeforeEach
    public void userTestSetup() {
        user1 = new User();
        acc1 = new Accumulator("Accumulator 1", "A savings account");
        exp1 = new Expense("Expense 1","An expense account");
        inc1 = new Income("Income 1","An income source");
        lon1 = new Loan("Loan 1", "Lending money to a friend");
        acc2 = new Accumulator("Accumulator 2", "A checking account");
        exp2 = new Expense("Expense 2","Another expense account");
        inc2 = new Income("Income 2","An alternative income source");
        lon2 = new Loan("Loan 2", "Lending money to family member");
        t1 = new Transaction(99,acc1,inc1,200,LocalDate.parse("2022-02-03"),
                "Transaction title", "Transaction description");
    }

    @Test
    public void userTestAddAccumulatorMethod() {
        assertEquals(0, user1.getAccumulator().size());
        user1.addAccumulator(acc1);
        assertEquals(1, user1.getAccumulator().size());
        user1.addAccumulator(acc2);
        assertEquals(2, user1.getAccumulator().size());

    }

    @Test
    public void userTestAddExistingAccumulator() {
        assertEquals(0, user1.getAccumulator().size());
        user1.addAccumulator(acc1);
        assertEquals(1, user1.getAccumulator().size());
        user1.addAccumulator(acc1);
        assertEquals(1, user1.getAccumulator().size());
        user1.addAccumulator(lon1);
        assertEquals(1, user1.getAccumulator().size());
    }

    @Test
    public void userTestAddExpenseMethod() {
        assertEquals(0, user1.getExpense().size());
        user1.addExpense(exp1);
        assertEquals(1, user1.getExpense().size());
        user1.addExpense(exp2);
        assertEquals(2, user1.getExpense().size());
    }

    @Test
    public void userTestAddExistingExpense() {
        assertEquals(0, user1.getExpense().size());
        user1.addExpense(exp1);
        assertEquals(1, user1.getExpense().size());
        user1.addExpense(exp1);
        assertEquals(1, user1.getExpense().size());
        user1.addAccumulator(lon1);
        assertEquals(1, user1.getExpense().size());
    }

    @Test
    public void userTestAddIncomeMethod() {
        assertEquals(0, user1.getIncome().size());
        user1.addIncome(inc1);
        assertEquals(1, user1.getIncome().size());
        user1.addIncome(inc2);
        assertEquals(2, user1.getIncome().size());
    }

    @Test
    public void userTestAddExistingIncome() {
        assertEquals(0, user1.getIncome().size());
        user1.addIncome(inc1);
        assertEquals(1, user1.getIncome().size());
        user1.addIncome(inc1);
        assertEquals(1, user1.getIncome().size());
        user1.addAccumulator(lon1);
        assertEquals(1, user1.getIncome().size());
    }

    @Test
    public void userTestAddLoanMethod() {
        assertEquals(0, user1.getLoan().size());
        user1.addLoan(lon1);
        assertEquals(1, user1.getLoan().size());
        user1.addLoan(lon2);
        assertEquals(2, user1.getLoan().size());
    }

    @Test
    public void userTestAddExistingLoan() {
        assertEquals(0, user1.getLoan().size());
        user1.addLoan(lon1);
        assertEquals(1, user1.getLoan().size());
        user1.addLoan(lon1);
        assertEquals(1, user1.getLoan().size());
        user1.addAccumulator(acc1);
        assertEquals(1, user1.getLoan().size());
    }

    @Test
    public void userTestFindAccountFromStringAccumulator() {
        user1.addAccumulator(acc1);
        try {
            assertEquals(acc1,user1.findAccountFromString("ACCUMULATOR 1"));
        } catch (AccountNotFoundException e) {
            fail();
        }
        try {
            user1.findAccountFromString("INCOME 3");
            fail();
        } catch (AccountNotFoundException e) {
            // should be executed
        }
    }


    @Test
    public void userTestFindAccountFromStringExpense() {
        user1.addExpense(exp1);
        try {
            assertEquals(exp1,user1.findAccountFromString("expeNSE 1"));
        } catch (AccountNotFoundException e) {
            fail();
        }
        try {
            user1.findAccountFromString("INCOME 6");
            fail();
        } catch (AccountNotFoundException e) {
            // should be executed
        }
    }

    @Test
    public void userTestFindAccountFromStringIncome() {
        user1.addIncome(inc1);
        try {
            assertEquals(inc1,user1.findAccountFromString("income 1"));
        } catch (AccountNotFoundException e) {
            fail();
        }
        try {
            user1.findAccountFromString("loan 3");
            fail();
        } catch (AccountNotFoundException e) {
            // should be executed
        }
    }


    @Test
    public void userTestFindAccountFromStringLoan() {
        user1.addLoan(lon1);
        try {
            assertEquals(lon1,user1.findAccountFromString("loAN 1"));
        } catch (AccountNotFoundException e) {
            fail();
        }
        try {
            user1.findAccountFromString("inc 3");
            fail();
        } catch (AccountNotFoundException e) {
            // should be executed
        }
    }

    @Test
    public void userTestTransactionList() {
        assertEquals(0,user1.getTransactionList().size());
        user1.addTransaction(t1);
        assertEquals(1,user1.getTransactionList().size());
    }
}
