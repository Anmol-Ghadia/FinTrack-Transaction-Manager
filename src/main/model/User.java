package model;

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
}
