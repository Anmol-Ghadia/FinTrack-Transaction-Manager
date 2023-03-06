package model;

import java.util.ArrayList;

/*
    A specific account that represents income sources, for example:
        - Job paychecks
        - Interest on Fixed deposits
        - Rent on property
*/
public class Income extends Account {
    public Income(String accountName, String accountDesc) {
        this.accountType = "INCOME";
        this.accountName = accountName.toUpperCase();
        this.accountDesc = accountDesc;
        this.transactions = new ArrayList<>();
    }

    public Income(String accountName) {
        this.accountType = "INCOME";
        this.accountName = accountName.toUpperCase();
    }

//    public Income(String accountName, String accountDesc,ArrayList<Transaction> transactions) {
//        this.accountType = "INCOME";
//        this.accountName = accountName.toUpperCase();
//        this.accountDesc = accountDesc;
//        this.transactions = transactions;
//    }
}