package model;

public class Income extends Account {
    public Income(String accountName, String accountDesc) {
        this.accountType = "INCOME";
        this.accountName = accountName;
        this.accountDesc = accountDesc;
    }
}