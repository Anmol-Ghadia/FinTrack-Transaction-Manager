package model;

import java.time.LocalDate;
import java.util.ArrayList;

/*
* A general Account model for more specific accounts.
*/

public abstract class Account {
    // Transactions are added from the front(index 0)
    protected ArrayList<Transaction> transactions;
    protected String accountType;
    protected String accountName;
    protected String accountDesc;

    public ArrayList<Transaction> getTransaction() {
        return transactions;
    }

    public Transaction getTransaction(int index) {
        return transactions.get(index);
    }

    public Transaction getTransactionByID(int id) {
        for (Transaction t: transactions) {
            if (t.getTransactionID() == id) {
                return t;
            }
        }
        // should raise exception if not found
        return null;
    }

    public Transaction getLastTransaction() {
        // can return error if nothing exists in the array
        return transactions.get(0);
    }

    public int getBalance() {
        return getDebit() - getCredit();
    }

    public int getDebit() {
        int total = 0;
        for (Transaction t: transactions) {
            if (t.getTo().equals(this)) {
                total += t.getAmount();
            }
        }
        return total;
    }

    public int getCredit() {
        int total = 0;
        for (Transaction t: transactions) {
            if (t.getFrom().equals(this)) {
                total += t.getAmount();
            }
        }
        return total;
    }

    // MODIFIES: this
    // EFFECTS: Adds a new transaction to the account
    public void addTransaction(Transaction transaction) {
        transactions.add(0, transaction);
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
