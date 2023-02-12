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
    private String desc;

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

    public String getDesc() {
        return desc;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setAmount(int amount) {
        // STUB
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        // STUB
        this.date = date;
    }

    public void setDay(int day) {
        // STUB
    }

    public void setMonth(int month) {
        // STUB
    }

    public void setYear(int year) {
        // STUB
    }


    public void setDesc(String desc) {
        // STUB
        this.desc = desc;
    }
}
