package model;

import exceptions.AccountNotFoundException;

import java.util.ArrayList;

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

    public ArrayList<Account> getAccumulator() {
        return accumulator;
    }

    public ArrayList<Account> getExpense() {
        return expense;
    }

    public ArrayList<Account> getIncome() {
        return income;
    }

    public ArrayList<Account> getLoan() {
        return loan;
    }

    public ArrayList<Transaction> getTransactionList() {
        return transactionList;
    }

    // MODIFIES: this
    // EFFECTS: adds accumulator if not already in list,
    //          can throw exception if account type is incorrect
    public void addAccumulator(Account acc) {
        if (!accumulator.contains(acc) && acc.getAccountType().equals("ACCUMULATOR")) {
            accumulator.add(acc);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds expense if not already in list,
    //          can throw exception if account type is incorrect
    public void addExpense(Account acc) {
        if (acc.getAccountType().equals("EXPENSE")) {
            if (!expense.contains(acc)) {
                expense.add(acc);
            }
        }
//        if (!expense.contains(acc) && acc.getAccountType().equals("EXPENSE")) {
//            expense.add(acc);
//        }
    }

    // MODIFIES: this
    // EFFECTS: adds income if not already in list,
    //          can throw exception if account type is incorrect
    public void addIncome(Account acc) {
        if (!income.contains(acc) && acc.getAccountType().equals("INCOME")) {
            income.add(acc);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds loan if not already in list,
    //          can throw exception if account type is incorrect
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

    // EFFECTS: finds am account with given name, returns a reference to the object
    public Account findAccountFromString(String accountName) throws AccountNotFoundException {
        for (Account acc: getAccumulator()) {
            if (acc.getAccountName().toUpperCase().equals(accountName.toUpperCase())) {
                return acc;
            }
        }
        for (Account acc: getExpense()) {
            if (acc.getAccountName().toUpperCase().equals(accountName.toUpperCase())) {
                return acc;
            }
        }
        for (Account acc: getIncome()) {
            if (acc.getAccountName().toUpperCase().equals(accountName.toUpperCase())) {
                return acc;
            }
        }
        for (Account acc: getLoan()) {
            if (acc.getAccountName().toUpperCase().equals(accountName.toUpperCase())) {
                return acc;
            }
        }
        throw new AccountNotFoundException();
    }
}
