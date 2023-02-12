package model;

public class Loan extends Account {

    public Loan(String accountName, String accountDesc) {
        this.accountType = "LOAN";
        this.accountName = accountName;
        this.accountDesc = accountDesc;
    }
}
