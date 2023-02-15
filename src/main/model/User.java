package model;

import exceptions.AccountNotFoundException;

import java.util.ArrayList;

/*
    Represents a user, should be instantiated only once. contains different transactions and accounts
    that a user may need
*/
public class User {
    private ArrayList<Account> accumulator;
    private ArrayList<Account> expense;
    private ArrayList<Account> income;
    private ArrayList<Account> loan;
    private ArrayList<Transaction> transactionList;

    public User() {
        transactionList = new ArrayList<>();
        accumulator = new ArrayList<>();
        expense = new ArrayList<>();
        loan = new ArrayList<>();
        income = new ArrayList<>();
    }

    // EFFECTS: returns the accumulator account ArrayList
    public ArrayList<Account> getAccumulator() {
        return accumulator;
    }

    // EFFECTS: returns the expense account ArrayList
    public ArrayList<Account> getExpense() {
        return expense;
    }

    // EFFECTS: returns the income account ArrayList
    public ArrayList<Account> getIncome() {
        return income;
    }

    // EFFECTS: returns the loan account ArrayList
    public ArrayList<Account> getLoan() {
        return loan;
    }

    // EFFECTS: returns the transaction list
    public ArrayList<Transaction> getTransactionList() {
        return transactionList;
    }

    // MODIFIES: this
    // EFFECTS: adds accumulator if not already in list
    public void addAccumulator(Account acc) {
        if (!accumulator.contains(acc) && acc.getAccountType().equals("ACCUMULATOR")) {
            accumulator.add(acc);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds expense if not already in list
    public void addExpense(Account acc) {
        if (!expense.contains(acc) && acc.getAccountType().equals("EXPENSE")) {
            expense.add(acc);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds income if not already in list
    public void addIncome(Account acc) {
        if (!income.contains(acc) && acc.getAccountType().equals("INCOME")) {
            income.add(acc);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds loan if not already in list
    public void addLoan(Account acc) {
        if (!loan.contains(acc) && acc.getAccountType().equals("LOAN")) {
            loan.add(acc);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds transaction to the user's transaction list
    public void addTransaction(Transaction t) {
        transactionList.add(t);
    }

    // MODIFIES: this
    // EFFECTS: adds transaction to the user's transaction list, and also in appropriate accounts
    public void addTransactionComplete(Transaction t) {
        t.getFrom().addTransaction(t);
        t.getTo().addTransaction(t);
        transactionList.add(t);
    }

    // MODIFIES: this
    // EFFECTS: returns true if accumulator account is removed from user
    public boolean removeAccumulator(Account acc) {
        return accumulator.remove(acc);
    }

    // MODIFIES: this
    // EFFECTS: returns true if income account is removed from user
    public boolean removeIncome(Account acc) {
        return  income.remove(acc);
    }

    // MODIFIES: this
    // EFFECTS: returns true if expense account is removed from user
    public boolean removeExpense(Account acc) {
        return expense.remove(acc);
    }

    // MODIFIES: this
    // EFFECTS: returns true if loan account is removed from user
    public boolean removeLoan(Account acc) {
        return loan.remove(acc);
    }

    // MODIFIES: this
    // EFFECTS: returns true if transaction is removed from user
    public boolean removeTransaction(Transaction t) {
        return transactionList.remove(t);
    }

    // EFFECTS: finds an account with the given name and returns it
    public Account findAccountFromString(String accountName) throws AccountNotFoundException {
        for (Account acc: getAccumulator()) {
            if (acc.getAccountName().equalsIgnoreCase(accountName)) {
                return acc;
            }
        }
        for (Account acc: getExpense()) {
            if (acc.getAccountName().equalsIgnoreCase(accountName)) {
                return acc;
            }
        }
        for (Account acc: getIncome()) {
            if (acc.getAccountName().equalsIgnoreCase(accountName)) {
                return acc;
            }
        }
        for (Account acc: getLoan()) {
            if (acc.getAccountName().equalsIgnoreCase(accountName)) {
                return acc;
            }
        }
        throw new AccountNotFoundException();
    }
}
