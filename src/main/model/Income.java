package model;

import java.util.ArrayList;

/*
    A specific account that represents income sources, for example:
        - Job paychecks
        - Interest on Fixed deposits
        - Rent on property
*/
public class Income extends Account {

    // EFFECTS: Creates a new income account with given name and description
    public Income(String accountName, String accountDesc) {
        this.accountType = "INCOME";
        this.accountName = accountName.toUpperCase();
        this.accountDesc = accountDesc;
        this.transactions = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Created (Y)Income Account with name: " + this.accountName));
    }

    // EFFECTS: creates a new income account with given name
    public Income(String accountName) {
        this.accountType = "INCOME";
        this.accountName = accountName.toUpperCase();
        EventLog.getInstance().logEvent(new Event("Created (Y)Income Account with name: " + this.accountName));
    }
}