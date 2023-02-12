package model;

public class Accumulator extends Account {
    public Accumulator(String accountName, String accountDesc){
        this.accountType = "ACCUMULATORS";
        this.accountName = accountName;
        this.accountDesc = accountDesc;
    }
}
