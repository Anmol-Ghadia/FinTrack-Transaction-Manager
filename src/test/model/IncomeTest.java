package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
    Test class for Income account
*/
public class IncomeTest {
    Account inc1;

    @BeforeEach
    public void incomeTestBefore() {
        inc1 = new Income("Person 1","Trustworthy person");
    }

    @Test
    public void incomeTestAccountType() {
        assertEquals("INCOME",inc1.getAccountType());
    }

    @Test
    public void incomeConstructorName() {
        Account acc = new Income("Job");
        assertEquals("JOB",acc.getAccountName());
        assertEquals("INCOME",acc.getAccountType());
    }
}
