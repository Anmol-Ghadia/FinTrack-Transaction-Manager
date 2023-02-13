package model;

import java.util.ArrayList;

public class Expense extends Account {
    public Expense(String accountName, String accountDesc) {
        this.accountType = "EXPENSE";
        this.accountName = accountName.toUpperCase();
        this.accountDesc = accountDesc;
        this.transactions = new ArrayList<>();
    }
}