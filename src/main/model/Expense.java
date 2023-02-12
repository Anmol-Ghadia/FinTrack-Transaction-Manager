package model;

public class Expense extends Account {
    public Expense(String accountName, String accountDesc){
        this.accountType = "EXPENSE";
        this.accountName = accountName;
        this.accountDesc = accountDesc;
    }
}