package ui;

import model.*;

import java.time.LocalDate;

public class Main {
    public static void main(String []args) {
        Account a1 = new Loan("Person 1","This person is trustworthy");
        Transaction t1 = new Transaction(0,
                new Loan("",""),
                new Loan("",""),
                0, LocalDate.now(),"","");
        a1.addTransaction(t1);
    }
}
