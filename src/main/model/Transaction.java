package model;

import java.time.LocalDate;

/*
 * Represents a unique transaction
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

    public int getTransactionID() {
        return transactionID;
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getDay() {
        return this.date.getDayOfMonth();
    }

    public int getMonth() {
        return this.date.getMonthValue();
    }

    public int getYear() {
        return this.date.getYear();
    }

    public String getTitle() {
        return title;
    }

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
        // STUB
        this.amount = amount;
    }

    // MODIFIES: this
    // EFFECTS: sets the new date for this transaction
    public void setDate(LocalDate date) {
        // STUB
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
        // STUB
        this.desc = desc;
    }
}
