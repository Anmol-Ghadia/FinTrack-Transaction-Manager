package model;

import java.util.ArrayList;

/*
    A specific type of account that represents store of money, for example:
            - A savings account
            - A checking account
            - Cash at home
*/
public class Accumulator extends Account {
    public Accumulator(String accountName, String accountDesc) {
        this.accountType = "ACCUMULATOR";
        this.accountName = accountName.toUpperCase();
        this.accountDesc = accountDesc;
        this.transactions = new ArrayList<>();
    }
}
