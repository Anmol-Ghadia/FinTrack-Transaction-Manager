package model;

import java.util.ArrayList;

/*
    A specific type of account that represent expenses, for example:
            - travel expenses
            - food expenses
*/
public class Expense extends Account {
    public Expense(String accountName, String accountDesc) {
        this.accountType = "EXPENSE";
        this.accountName = accountName.toUpperCase();
        this.accountDesc = accountDesc;
        this.transactions = new ArrayList<>();
    }

    public Expense(String accountName) {
        this.accountType = "EXPENSE";
        this.accountName = accountName.toUpperCase();
    }
}