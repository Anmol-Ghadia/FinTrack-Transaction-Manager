package model;

import java.util.ArrayList;

public class Income extends Account {
    public Income(String accountName, String accountDesc) {
        this.accountType = "INCOME";
        this.accountName = accountName.toUpperCase();
        this.accountDesc = accountDesc;
        this.transactions = new ArrayList<>();
    }
}