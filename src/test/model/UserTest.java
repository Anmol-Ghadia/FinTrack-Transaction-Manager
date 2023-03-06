package model;

import exceptions.AccountNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/*
    Test class for user
*/
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
    Transaction t2;

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
        t2 = new Transaction(100,lon1,exp1,50,LocalDate.parse("2022-02-04"),
                "Transaction title 2", "Transaction description 2");
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
        user1.addExpense(lon1);
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
        user1.addIncome(lon1);
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
        user1.addLoan(acc1);
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

    @Test
    public void userTestAddTransactionComplete() {
        user1.addAccumulator(acc1);
        user1.addIncome(inc1);
        assertEquals(0,user1.getAccumulator().get(0).getTransaction().size());
        assertEquals(0,user1.getIncome().get(0).getTransaction().size());
        user1.addTransactionComplete(t1);
        assertEquals(t1,inc1.getTransaction(0));
        assertEquals(t1,acc1.getTransaction(0));
        assertEquals(t1,user1.getTransactionList().get(0));
    }

    @Test
    public void userTestRemoveAccumulator() {
        assertEquals(0,user1.getAccumulator().size());
        user1.addAccumulator(acc1);
        assertEquals(1,user1.getAccumulator().size());
        user1.addAccumulator(acc2);
        assertEquals(2,user1.getAccumulator().size());
        user1.removeAccumulator(acc1);
        assertEquals(1,user1.getAccumulator().size());
        user1.removeAccumulator(acc2);
        assertEquals(0,user1.getAccumulator().size());
    }

    @Test
    public void userTestRemoveIncome() {
        assertEquals(0,user1.getIncome().size());
        user1.addIncome(inc1);
        assertEquals(1,user1.getIncome().size());
        user1.addIncome(inc2);
        assertEquals(2,user1.getIncome().size());
        user1.removeIncome(inc1);
        assertEquals(1,user1.getIncome().size());
        user1.removeIncome(inc2);
        assertEquals(0,user1.getIncome().size());
    }

    @Test
    public void userTestRemoveExpense() {
        assertEquals(0,user1.getExpense().size());
        user1.addExpense(exp1);
        assertEquals(1,user1.getExpense().size());
        user1.addExpense(exp2);
        assertEquals(2,user1.getExpense().size());
        user1.removeExpense(exp1);
        assertEquals(1,user1.getExpense().size());
        user1.removeExpense(exp2);
        assertEquals(0,user1.getExpense().size());
    }

    @Test
    public void userTestRemoveLoan() {
        assertEquals(0,user1.getLoan().size());
        user1.addLoan(lon1);
        assertEquals(1,user1.getLoan().size());
        user1.addLoan(lon2);
        assertEquals(2,user1.getLoan().size());
        user1.removeLoan(lon1);
        assertEquals(1,user1.getLoan().size());
        user1.removeLoan(lon2);
        assertEquals(0,user1.getLoan().size());
    }

    @Test
    public void userTestRemoveTransaction() {
        user1.addTransaction(t1);
        assertTrue(user1.removeTransaction(t1));
        assertFalse(user1.removeTransaction(t1));
    }

    @Test
    public void userTestToJsonStringAccumulatorIncome() {
        user1.addIncome(inc1);
        user1.addAccumulator(acc1);
        user1.addTransactionComplete(t1);
        String check = user1.toJsonString();

        JSONObject expect = new JSONObject();
        JSONArray accNames = new JSONArray();
        accNames.put(acc1.getAccountName());
        expect.put("acc-names", accNames);
        JSONArray incNames = new JSONArray();
        incNames.put(inc1.getAccountName());
        expect.put("income-names", incNames);
        expect.put("expense-names", new JSONArray());
        expect.put("loan-names", new JSONArray());
        JSONArray accArray = new JSONArray();
        accArray.put(acc1.toJson());
        expect.put("acc", accArray);
        JSONArray incArray = new JSONArray();
        incArray.put(inc1.toJson());
        expect.put("income", incArray);
        expect.put("expense", new JSONArray());
        expect.put("loan", new JSONArray());
        JSONArray transArray = new JSONArray();
        transArray.put(t1.toJson());
        expect.put("transaction", transArray);

        assertEquals(expect.toString(4),check);
    }

    @Test
    public void userTestToJsonString() {
        user1.addLoan(lon1);
        user1.addExpense(exp1);
        user1.addTransactionComplete(t2);
        String check = user1.toJsonString();

        JSONObject expect = new JSONObject();
        JSONArray loanNames = new JSONArray();
        loanNames.put(lon1.getAccountName());
        expect.put("loan-names", loanNames);
        JSONArray expNames = new JSONArray();
        expNames.put(exp1.getAccountName());
        expect.put("expense-names", expNames);
        expect.put("acc-names", new JSONArray());
        expect.put("income-names", new JSONArray());
        JSONArray loanArray = new JSONArray();
        loanArray.put(lon1.toJson());
        expect.put("loan", loanArray);
        JSONArray expArray = new JSONArray();
        expArray.put(exp1.toJson());
        expect.put("expense", expArray);
        expect.put("acc", new JSONArray());
        expect.put("income", new JSONArray());
        JSONArray transArray = new JSONArray();
        transArray.put(t2.toJson());
        expect.put("transaction", transArray);

        assertEquals(expect.toString(4),check);
    }

    @Test
    public void userTestConstructorJSONObject() {
        user1.addAccumulator(acc1);
        user1.addIncome(inc1);
        user1.addExpense(exp1);
        user1.addLoan(lon1);
        user1.addTransactionComplete(t1);
        JSONObject userJSON = new JSONObject(user1.toJsonString());
        User userExpect = new User(userJSON);
        assertEquals(acc1.getAccountName(),userExpect.getAccumulator().get(0).getAccountName());
        assertEquals(inc1.getAccountName(),userExpect.getIncome().get(0).getAccountName());
        assertEquals(lon1.getAccountName(),userExpect.getLoan().get(0).getAccountName());
        assertEquals(exp1.getAccountName(),userExpect.getExpense().get(0).getAccountName());
        assertEquals(t1.getTitle(),userExpect.getTransactionList().get(0).getTitle());
    }

}
