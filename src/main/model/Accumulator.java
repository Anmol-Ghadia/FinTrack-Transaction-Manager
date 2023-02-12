package model;

public class Accumulator extends Account {
    public Accumulator(String accountName, String accountDesc){
        this.accountType = "ACCUMULATOR";
        this.accountName = accountName;
        this.accountDesc = accountDesc;
    }
}
