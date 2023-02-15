package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
    Test class for expense Account
*/
public class ExpenseTest {
    Account exp1;

    @BeforeEach
    public void expenseTestBefore() {
        exp1 = new Expense("Person 1","Trustworthy person");
    }

    @Test
    public void incomeTestAccountType() {
        assertEquals("EXPENSE",exp1.getAccountType());
    }
}
