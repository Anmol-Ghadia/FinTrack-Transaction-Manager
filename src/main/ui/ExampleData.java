package ui;

import model.*;

import java.time.LocalDate;

/*
    A class that may be used to add dummy/ Example values to the app
*/
public class ExampleData {

    User user;
    int transactionCount;
    Account cash;
    Account checking;
    Account saving;
    Account food;
    Account travel;
    Account clothing;
    Account phonePlan;
    Account job;
    Account bob;
    Account club;

    // MODIFIES: user
    // EFFECTS: adds example data to user
    public ExampleData(User userToInitialize,int transactionCount) {
        this.user = userToInitialize;
        this.transactionCount = transactionCount;
        initAccumulators();
        initExpense();
        initIncome();
        initLoan();
        addExampleTransactions();
    }


    // EFFECTS: returns the transaction count
    public int getTransactionCount() {
        return this.transactionCount;
    }

    // MODIFIES: user
    // EFFECTS: initializes accumulator accounts
    private void initAccumulators() {
        cash = new Accumulator("CASH", "Record of all cash exchanges");
        checking = new Accumulator("BMO-CHECKING", "BMO checking account *9482");
        saving = new Accumulator("BMO-SAVING", "BMO savings account *3926");
        user.addAccumulator(cash);
        user.addAccumulator(checking);
        user.addAccumulator(saving);
    }

    // MODIFIES: user
    // EFFECTS: initializes Expense accounts
    private void initExpense() {
        food = new Expense("FOOD", "Groceries or Meals");
        travel = new Expense("TRAVEL", "Any traveling expense");
        clothing = new Expense("CLOTHING", "Transactions related to clothes");
        phonePlan = new Expense("MOBILE-PLAN", "Mobile bills");
        user.addExpense(food);
        user.addExpense(travel);
        user.addExpense(clothing);
        user.addExpense(phonePlan);
    }

    // MODIFIES: user
    // EFFECTS:  initializes Income accounts
    public void initIncome() {
        job = new Income("JOB", "Income from job");
        user.addIncome(job);
    }

    // MODIFIES: user
    // EFFECTS:  initializes Loan accounts
    public void initLoan() {
        bob = new Loan("BOB", "Bob is a good friend, I usually pay for him and "
                + "then he pays me back later");
        club = new Loan("XYZ-CLUB", "As an exec at the club, I usually pay for expenses "
                + "and then I get reimbursed for the expense");
        user.addLoan(bob);
        user.addLoan(clothing);
    }

    // MODIFIES: user
    // EFFECTS: Adds example transactions to user
    public void addExampleTransactions() {
        addTransaction(job,checking,3500, LocalDate.parse("2022-12-31"),"Paycheck","2022 December");
        addTransaction(checking,cash,300, LocalDate.parse("2023-01-02"),"Withdrawn from checking",
                "Withdraw 300 from checking for general use");
        addTransaction(checking, food, 25, LocalDate.parse("2023-01-03"), "ThaiRestaurant",
                "Lunch at Thai restaurant with friends");
        addTransaction(cash,travel,18, LocalDate.parse("2023-01-03"),"Uber",
                "Uber to get back home from Allison street");
        addTransaction(checking,saving,2000, LocalDate.parse("2023-01-05"),"Transfer",
                "Transferred 2000 to savings for emergency");
        addTransaction(checking,phonePlan,45, LocalDate.parse("2023-01-06"),"PrimaryMobileBill",
                "Mobile bill for December 2022");
        addTransaction(checking,bob, 89, LocalDate.parse("2023-01-08"),"Bob's watch",
                "Paid for Bob's watch purchase");
        addTransaction(checking,club,34, LocalDate.parse("2023-01-10"),"Poster Print",
                "paid for club's day poster printing");
        addTransaction(club,checking, 34, LocalDate.parse("2023-01-14"),"Club Reimbursement",
                "club reimbursed for poster printing");
        addTransaction(bob,saving, 50, LocalDate.parse("2023-01-15"),"Bob loan payment partial",
                "paid partially for watch purchase");
        addTransaction(checking, food, 12, LocalDate.parse("2023-01-16"),"Starbucks",
                "had starbucks on my way to park");
    }

    // MODIFIES: user
    // EFFECTS: creates new transaction and adds it at appropriate places
    public void addTransaction(Account from,Account to,int amt,LocalDate date,String title, String desc) {
        Transaction t = new Transaction(transactionCount,from,to,amt,date,title,desc);
        user.addTransactionComplete(t);
        transactionCount++;
    }
}
