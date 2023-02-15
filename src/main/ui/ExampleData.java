package ui;

import model.*;

import java.time.LocalDate;

public class ExampleData {

    User user;
    Integer transactionCount;

    public ExampleData() {}

    // MODIFIES: user
    public void setData(User userToInitialize,Integer transactionCount) {
        this.user = userToInitialize;
        this.transactionCount = transactionCount;
        initData();
    }

    // MODIFIES: user
    // EFFECTS: inputs some example data for user
    @SuppressWarnings("methodlength")
    private void initData() {
        Account cash = new Accumulator("CASH","Record of all cash exchanges");
        Account checking = new Accumulator("BMO-CHECKING","BMO checking account *9482");
        Account saving = new Accumulator("BMO-SAVING","BMO savings account *3926");
        user.addAccumulator(cash);
        user.addAccumulator(checking);
        user.addAccumulator(saving);

        Account food = new Expense("FOOD", "Groceries or Meals");
        Account travel = new Expense("TRAVEL", "Any traveling expense");
        Account clothing = new Expense("CLOTHING", "Transactions related to clothes");
        Account phonePlan = new Expense("MOBILE-PLAN","Mobile bills");
        user.addExpense(food);
        user.addExpense(travel);
        user.addExpense(clothing);
        user.addExpense(phonePlan);

        Account job = new Income("JOB","Income from job");
        user.addIncome(job);

        Account bob = new Loan("BOB","Bob is a good friend, I usually pay for him and "
                + "then he pays me back later");
        Account club = new Loan("XYZ-CLUB","As an exec at the club, I usually pay for expenses "
                + "and then I get reimbursed for the expense");
        user.addLoan(bob);
        user.addLoan(clothing);

        Transaction t1 = new Transaction(transactionCount + 1,job,checking,3500,
                LocalDate.parse("2022-12-31"),"Paycheck","2022 December");
        user.addTransactionComplete(t1);
        transactionCount++;
        Transaction t2 = new Transaction(transactionCount + 1,checking,cash,300,
                LocalDate.parse("2023-01-02"),"Withdrawn from checking","Withdrawed 300 from checking for"
                + "general use");
        user.addTransactionComplete(t2);
        transactionCount++;
        Transaction t3 = new Transaction(transactionCount + 1, checking, food, 25,
                LocalDate.parse("2023-01-03"), "ThaiRestaurant","Lunch at Thai restaurant with friends");
        user.addTransactionComplete(t3);
        transactionCount++;
        Transaction t4 = new Transaction(transactionCount + 1,cash,travel,18,
                LocalDate.parse("2023-01-03"),"Uber","Uber to get back home from Allison street");
        user.addTransactionComplete(t4);
        transactionCount++;
        Transaction t5 = new Transaction(transactionCount + 1,checking,saving,2000,
                LocalDate.parse("2023-01-05"),"Transfer","Transferred 2000 to savings for emergency");
        user.addTransactionComplete(t5);
        transactionCount++;
        Transaction t6 = new Transaction(transactionCount + 1,checking,phonePlan,45,
                LocalDate.parse("2023-01-06"),"PrimaryMobileBill","Mobile bill for December 2022");
        user.addTransactionComplete(t6);
        transactionCount++;
        Transaction t7 = new Transaction(transactionCount + 1, checking,bob, 89,
                LocalDate.parse("2023-01-08"),"Bob's watch","Paid for Bob's watch purchase");
        user.addTransactionComplete(t7);
        transactionCount++;
        Transaction t8 = new Transaction(transactionCount + 1, checking,club,34,
                LocalDate.parse("2023-01-10"),"Poster Print","paid for club's day poster printing");
        user.addTransactionComplete(t8);
        transactionCount++;
        Transaction t9 = new Transaction(transactionCount + 1, club,checking, 34,
                LocalDate.parse("2023-01-14"),"Club Reimbursement", "club reimbursed for poster printing");
        user.addTransactionComplete(t9);
        transactionCount++;
        Transaction t10 = new Transaction(transactionCount + 1, bob,saving, 50,
                LocalDate.parse("2023-01-15"),"Bob loan payment partial","paid partially for watch purchase");
        user.addTransactionComplete(t10);
        transactionCount++;
        Transaction t11 = new Transaction(transactionCount + 1, checking, food, 12,
                LocalDate.parse("2023-01-16"),"Starbucks","had starbucks on my way to park");
        user.addTransactionComplete(t11);
        transactionCount++;
    }
}
