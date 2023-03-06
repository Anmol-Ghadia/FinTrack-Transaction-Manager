package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
    Test class for Loan Account
*/
public class LoanTest {
    Account loan1;

    @BeforeEach
    public void loanTestBefore() {
        loan1 = new Loan("Person 1","Trustworthy person");
    }

    @Test
    public void loanTestAccountType() {
        assertEquals("LOAN",loan1.getAccountType());
    }

    @Test
    public void loanConstructorName() {
        Account acc = new Loan("Rick");
        assertEquals("RICK",acc.getAccountName());
        assertEquals("LOAN",acc.getAccountType());
    }
}
