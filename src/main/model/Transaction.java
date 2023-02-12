package model;

import java.time.LocalDate;

/*
 * Represents a unique transaction
*/
public class Transaction {
    private final int transactionID;
    private String from;
    private String to;
    private int amount;
    private LocalDate date;
    private String title;
    private String desc;

    // REQUIRES: to, from are valid accounts
    //           amount > 0, title is not empty
    public Transaction(int transactionID,
                       String from, String to,
                       int amount,
                       LocalDate date,
                       String desc) {
        this.transactionID = transactionID;
        this.amount = amount;
        this.from = from;
        this.to = to;
        this.date = date;
        this.desc = desc;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getDay() {
        // STUB
        return -1;
    }

    public int getMonth() {
        // STUB
        return -1;
    }

    public int getYear() {
        // STUB
        return -1;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    // REQUIRES: from should be a valid account
    // MODIFIES: this
    // EFFECTS: changes the from<account> of this transaction
    public void setFrom(String from) {
        this.from = from;
    }

    // REQUIRES: to should be a valid account
    // MODIFIES: this
    // EFFECTS: changes the to<account> of this transaction
    public void setTo(String to) {
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

//    // REQUIRES:
//    // MODIFIES:
//    // EFFECTS:
//    public void setDay(int day) {
//        // STUB
//    }
//
//    // REQUIRES:
//    // MODIFIES:
//    // EFFECTS:
//    public void setMonth(int month) {
//        // STUB
//    }
//
//    // REQUIRES:
//    // MODIFIES:
//    // EFFECTS:
//    public void setYear(int year) {
//        // STUB
//    }

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
