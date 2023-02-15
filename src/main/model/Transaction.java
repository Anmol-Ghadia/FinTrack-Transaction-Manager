package model;

import java.time.LocalDate;

/*
 * Represents a transaction, with important details like date, amount, accounts and more personalized information
 * like title, and description.
*/
public class Transaction {
    private final int transactionID;
    private Account from;
    private Account to;
    private int amount;
    private LocalDate date;
    private String title;
    private String desc;

    // REQUIRES: to, from are valid accounts
    //           amount > 0, title is not empty
    public Transaction(int transactionID,
                       Account from, Account to,
                       int amount,
                       LocalDate date,
                       String title, String desc) {
        this.transactionID = transactionID;
        this.amount = amount;
        this.from = from;
        this.to = to;
        this.date = date;
        this.title = title;
        this.desc = desc;
    }

    // EFFECTS: returns the transaction ID
    public int getTransactionID() {
        return transactionID;
    }

    // EFFECTS: returns the credit(from) account
    public Account getFrom() {
        return from;
    }

    // EFFECTS: returns the debit(to) account
    public Account getTo() {
        return to;
    }

    // EFFECTS: returns the amount of transaction
    public int getAmount() {
        return amount;
    }

    // EFFECTS: returns the date
    public LocalDate getDate() {
        return date;
    }

    // EFFECTS: returns the day of the month
    public int getDay() {
        return this.date.getDayOfMonth();
    }

    // EFFECTS: returns the month of the year as a number
    public int getMonth() {
        return this.date.getMonthValue();
    }

    // EFFECTS: returns the year of transaction
    public int getYear() {
        return this.date.getYear();
    }

    // EFFECTS: returns the title of the transaction
    public String getTitle() {
        return title;
    }

    // EFFECTS: returns the description of the transaction
    public String getDesc() {
        return desc;
    }

    // MODIFIES: this
    // EFFECTS: changes the from<account> of this transaction
    public void setFrom(Account from) {
        this.from = from;
    }

    // MODIFIES: this
    // EFFECTS: changes the to<account> of this transaction
    public void setTo(Account to) {
        this.to = to;
    }

    // REQUIRES: Amount > 0
    // MODIFIES: this
    // EFFECTS: sets the new amount for this transaction
    public void setAmount(int amount) {
        this.amount = amount;
    }

    // MODIFIES: this
    // EFFECTS: sets the new date for this transaction
    public void setDate(LocalDate date) {
        this.date = date;
    }

    // MODIFIES: this
    // EFFECTS: sets the new title for this transaction
    public void setTitle(String title) {
        this.title = title;
    }

    // MODIFIES: this
    // EFFECTS: sets the new description for this transaction
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
