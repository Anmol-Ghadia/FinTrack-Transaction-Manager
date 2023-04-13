package model;

import java.util.ArrayList;

/**
 *    A specific type of account that represents store of money, for example:
 *           - A savings account
 *          - A checking account
 *          - Cash at home
 */
public class Accumulator extends Account {

    // EFFECTS: Create a new Accumulator type account with given name and description
    public Accumulator(String accountName, String accountDesc) {
        this.accountType = "ACCUMULATOR";
        this.accountName = accountName.toUpperCase();
        this.accountDesc = accountDesc;
        this.transactions = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Created (Y)Accumulator Account with name: " + this.accountName));
    }

    // EFFECTS: Create a new Accumulator type account with given name
    public Accumulator(String accountName) {
        this.accountType = "ACCUMULATOR";
        this.accountName = accountName.toUpperCase();
        EventLog.getInstance().logEvent(new Event("Created (Y)Accumulator Account with name: " + this.accountName));
    }
}
