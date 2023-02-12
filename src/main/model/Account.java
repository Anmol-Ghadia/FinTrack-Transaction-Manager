package model;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Account {
    protected ArrayList<Transaction> transactions;
    protected String accountType;
    protected String accountName;
    protected String accountDesc;

    public ArrayList<Transaction> getTransaction() {
        return transactions;
    }

    public Transaction getTransaction(int index) {
        // STUB
        return new Transaction(0,
                "", "", 0, LocalDate.now(),"");
    }

    public Transaction getTransactionByID() {
        // STUB
        return new Transaction(0,
                "", "", 0, LocalDate.now(),"");
    }

    public Transaction getLastTransaction() {
        // STUB
        return new Transaction(0,
                "", "", 0, LocalDate.now(),"");
    }

    public int getBalance() {
        // STUB
        return -1;
    }

    public int getDebit() {
        // STUB
        return -1;
    }

    public int getCredit() {
        // STUB
        return -1;
    }

    public void addTransaction(Transaction transaction) {

    }

    public String getAccountType() {
        // STUB
        return accountType;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountDesc() {
        return accountDesc;
    }
}
