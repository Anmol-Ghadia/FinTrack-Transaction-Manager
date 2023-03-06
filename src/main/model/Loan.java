package model;

import java.util.ArrayList;

/*
    It is a specific type of account that represents, any entity that may receive money as a loan for a short period
    of time, for example:
        - paying on behalf of a friend
        - paying on behalf of a club
        - paying on behalf of company
*/
public class Loan extends Account {

    // EFFECTS: creates a new loan account with given name and description
    public Loan(String accountName, String accountDesc) {
        this.accountType = "LOAN";
        this.accountName = accountName.toUpperCase();
        this.accountDesc = accountDesc;
        this.transactions = new ArrayList<>();
    }

    // EFFECTS: creates a new loan account with given name
    public Loan(String accountName) {
        this.accountType = "LOAN";
        this.accountName = accountName.toUpperCase();
    }
}
