package model;

import java.util.ArrayList;

public class Loan extends Account {

    public Loan(String accountName, String accountDesc) {
        this.accountType = "LOAN";
        this.accountName = accountName.toUpperCase();
        this.accountDesc = accountDesc;
        this.transactions = new ArrayList<>();
    }
}
