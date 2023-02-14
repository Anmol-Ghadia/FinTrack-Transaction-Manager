package ui;

import exceptions.AccountNotFoundException;
import com.github.freva.asciitable.AsciiTable;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class FinTrack {
    User user;
    private Scanner input;
    private boolean appRunning;
    private int transactionCount;
    String command;
    private final String menu0 = "|Main menu\n| 1) Transaction\n| 2) Account\n| 3) Summary\n| 4) quit";
    private final String menu1 = "||Transaction\n|| 1) Create\n|| 2) Modify\n|| 3) Delete\n|| 4) [Back] Main Menu";

    private final String menu2 = "||Account\n|| 1) Add new\n|| 2) Modify\n|| 3) Delete\n|| 4) [Back] Main Menu";
    private final String menu3 = "||Summary\n|| 1) Individual Account\n|| 2) All Account\n|| 3) [Back] Main Menu";

    public FinTrack() {
        runConsoleApp();
    }


    // EFFECTS: Initializes global variables, sets some initial data and starts the app
    private void runConsoleApp() {
        init();
        while (appRunning) {
            System.out.println(menu0);
            command = input.next().toLowerCase();
            handleMenu0();
        }
    }

    // EFFECTS: Initializes global variables
    public void init() {
        user = new User();
        transactionCount = 0;
        appRunning = true;
        command = null;
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        setInitialData();
    }

    // EFFECTS: sets some initial data as an example
    private void setInitialData() {
        Account acc1 = new Accumulator("SAVINGS","CIBC");
        user.addAccumulator(acc1);
        Account travel = new Expense("TRAVEL","traveling costs");
        user.addExpense(travel);
    }

    // EFFECTS: Displays and takes user input for Main menu(0)
    private void handleMenu0() {
        switch (command) {
            case "1": // transaction operation
                pause("|-> Selected Transaction");
                handleMenu1();
                break;
            case "2": // account operation
                pause("|-> Selected Account");
                handleMenu2();
                break;
            case "3": // Account summary
                pause("|-> Selected Summary");
                handleMenu3();
                break;
            case "4": // end of app
                appRunning = false;
                pause("|-> Exiting :(");
                break;
            default: // incorrect input
                pause("|-> **Invalid Input**");
                break;
        }
    }

    // EFFECTS: Displays and takes user input for Transaction Menu(1)
    private void handleMenu1() {
        int selected = takeInputIntegerRange(menu1,1,4);
        switch (selected) {
            case 1: // create transaction
                pause("||-> Selected Create transaction");
                createNewTransactionMenu();
                break;
            case 2: // modify transaction
                pause("||-> Selected Modify transaction");
                modifyTransactionMenu();
                break;
            case 3: // delete transaction
                pause("||-> Selected Delete transaction");
                deleteTransactionMenu();
                break;
            case 4: // back to main menu
                pause("||-> Going back to Main menu");
                break;
        }
    }

    // EFFECTS: takes user input for new transaction and adds it in appropriate places
    private void createNewTransactionMenu() {
        Account from = takeInputAccount("||| Enter Credit Account");
        Account to = takeInputAccount("||| Enter Debit Account");
        int amt = takeInputNatural("||| Enter Amount");
        LocalDate date = takeInputDate("||| Enter date of transaction");
        String title = takeInputString("||| Enter title of transaction");
        String desc = takeInputString("||| Enter description of transaction");
        String[][] data = {{from.getAccountName(),to.getAccountName(),String.valueOf(amt),title,date.toString(),desc}};
        System.out.println(stringTable(data));
        boolean add = takeInputYesNo("|||-> Do you want to add the above transaction?", "|||->Adding Transaction");
        if (add) {
            Transaction t = new Transaction(transactionCount, from, to, amt, date, title, desc);
            from.addTransaction(t);
            to.addTransaction(t);
            user.addTransaction(t);
            transactionCount++;
            pause("|||-> Added Transaction Successfully");
        } else {
            pause("|||-> **Aborting Addition**");
        }
    }

    // EFFECTS: takes user input for modifying a transaction by id
    private void modifyTransactionMenu() {
        printAllTransactions();
        Transaction t = takeInputTransactionID("||| Enter Transaction ID to modify");
        String[][] data = {{t.getFrom().getAccountName(),t.getTo().getAccountName(),
                String.valueOf(t.getAmount()),t.getTitle(),
                t.getDate().toString(),t.getDesc()}};
        pause(stringTable(data));
        int selected = takeInputIntegerRange("||| What do you want to modify?\n 1) From\n 2) To\n"
                + " 3) Amount\n 4) Title\n 5) Date\n 6) Description\n", 1,6);
        if (selected != -1) {
            modifyTransactionMenuHandleInput(selected, t);
        }
    }

    // EFFECTS: Prints all transactions in the app
    private void printAllTransactions() {
        System.out.println("||| List of all transactions with ID");
        String[] header = {"ID","From","To","amount","title","date","description"};
        Transaction t;
        String[][] data = new String[user.getTransactionList().size()][7];
        for (int i = 0; i < user.getTransactionList().size(); i++) {
            t = user.getTransactionList().get(i);
            String[] row = new String[7];
            row[0] = String.valueOf(t.getTransactionID());
            row[1] = t.getFrom().getAccountName();
            row[2] = t.getTo().getAccountName();
            row[3] = String.valueOf(t.getAmount());
            row[4] = t.getTitle();
            row[5] = t.getDate().toString();
            row[6] = t.getDesc();
            data[i] = row;
        }
        pause(AsciiTable.getTable(header,data));
    }

    // EFFECTS: Handles input for modify transaction menu
    @SuppressWarnings("methodlength")
    private void modifyTransactionMenuHandleInput(int selected, Transaction transaction) {
        switch (selected) {
            case 1: // modify from
                Account from = takeInputAccount("||| Enter new credit Account");
                transaction.setFrom(from);
                break;
            case 2: // modify to
                Account to = takeInputAccount("||| Enter new debit Account");
                transaction.setTo(to);
                break;
            case 3: // modify amount
                int amt = takeInputNatural("||| Enter new transaction Amount");
                transaction.setAmount(amt);
                break;
            case 4: // modify title
                String title = takeInputString("||| Enter new Title");
                transaction.setTitle(title);
                break;
            case 5: // modify date
                LocalDate date = takeInputDate("||| Enter new Date");
                transaction.setDate(date);
                break;
            case 6: // modify desc
                String desc = takeInputString("||| Enter new Description");
                transaction.setDesc(desc);
                break;
        }
        pause("|||-> Modified Transaction");
    }

    // EFFECTS: Takes user input to delete transaction from appropriate places by ID
    private void deleteTransactionMenu() {
        System.out.println("||| Delete Transaction");
        printAllTransactions();
        Transaction t = takeInputTransactionID("||| Enter Transaction ID to delete");
        boolean shouldDelete = takeInputYesNo("||| Are you sure that you want to delete the transaction?",
                "||| Deleting transaction with ID: " + t.getTransactionID());
        if (shouldDelete) {
            deleteTransaction(t);
        } else {
            pause("|||-> Aborting Delete");
        }
    }

    // EFFECTS: deletes transaction from all appropriate places
    private void deleteTransaction(Transaction t) {
        // maybe redo this method
        if (!user.removeTransaction(t)) {
            pause("|||-> **Transaction not found <transaction list>**");
        }
        if (!t.getFrom().deleteTransaction(t)) {
            pause("|||-> **Transaction not found <FROM> : " + t.getTransactionID() + "**");
        }
        if (!t.getTo().deleteTransaction(t)) {
            pause("|||-> Transaction not found <TO> : " + t.getTransactionID() + "**");
        }
    }

    // EFFECTS: takes user input for Account Menu(2)
    private void handleMenu2() {
        int selected = takeInputIntegerRange(menu2,1,4);
        switch (selected) {
            case 1: // create new account
                pause("||-> Selected Create new account");
                createNewAccountMenu();
                break;
            case 2: // modify account
                pause("||-> Selected Modify account");
                modifyAccountMenu();
                break;
            case 3: // delete account
                pause("||-> Selected Delete account");
                deleteAccountMenu();
                break;
            case 4: // go back to Main menu(0)
                pause("||-> Going back to Main menu");
                break;
        }
    }

    // EFFECTS: takes input from user to create new account
    private void createNewAccountMenu() {
        int select = takeInputIntegerRange("||| Account Types\n||| 1) Income\n||| 2) Accumulator\n||| 3) Expense\n"
                        + "||| 4) Loan", 1,4);
        String accountName;
        while (true) {
            System.out.println("||| Enter Account name");
            accountName = input.next().toLowerCase();
            try {
                user.findAccountFromString(accountName);
            } catch (AccountNotFoundException e) {
                pause("|||-> **Enter a unique Account name(Account name already exists)**");
                break;
            }
        }
        String desc = takeInputString("||| Enter Description for the Account");
        createNewAccountMenuHandleMenu(select, accountName,desc);
    }

    // EFFECTS: Handles input for create account menu
    private void createNewAccountMenuHandleMenu(int select,String accountName, String desc) {
        Account newAccount;
        switch (select) {
            case 1:
                newAccount = new Income(accountName.toUpperCase(),desc);
                user.addIncome(newAccount);
                break;
            case 2:
                newAccount = new Accumulator(accountName.toUpperCase(),desc);
                user.addAccumulator(newAccount);
                break;
            case 3:
                newAccount = new Expense(accountName.toUpperCase(),desc);
                user.addExpense(newAccount);
                break;
            case 4:
                newAccount = new Loan(accountName.toUpperCase(),desc);
                user.addLoan(newAccount);
                break;
        }
        pause("|||-> Account created");
    }

    // EFFECTS: takes user input to modify account details and modifies the account
    private void modifyAccountMenu() {
        Account acc = takeInputAccount("||| Enter Account Name");
        int selected = takeInputIntegerRange("||| Modify Account's \n||| 1) name \n||| 2) description", 1,2);
        switch (selected) {
            case 1: // modify Name
                String name = takeInputString("||| Enter new name");
                acc.setName(name);
                pause("|||-> Name changed");
                break;
            case 2: // modify desc
                String desc = takeInputString("||| Enter new description");
                acc.setDesc(desc);
                pause("|||-> Description changed");
                break;
        }
    }

    // EFFECTS: takes user input to delete account and delete transactions from all appropriate places
    private void deleteAccountMenu() {
        Account acc = takeInputAccount("||| Enter account name to delete");
        boolean delete = takeInputYesNo("||| Are you sure that you want to delete the account?" + "\n"
                + "All transactions in this account will be deleted including "
                + "their double entries", "|||-> Deleting Account");
        if (delete) {
            deleteTransactions(acc.getTransaction());
            user.removeIncome(acc);
            user.removeAccumulator(acc);
            user.removeExpense(acc);
            user.removeLoan(acc);
            pause("|||-> Account deleted and all the transactions from that account");
        } else {
            pause("|||-> Aborting delete");
        }
    }

    // EFFECTS: deletes all the transactions from everywhere
    private void deleteTransactions(ArrayList<Transaction> transactions) {
        for (Transaction t: transactions) {
            deleteTransaction(t);
        }
    }

    // EFFECTS: Displays summary menu and takes user input for correct action
    private void handleMenu3() {
        int selected = takeInputIntegerRange(menu3,1,3);
        switch (selected) {
            case 1: // individual account summary
                pause("|| Selected individual account summary");
                individualAccountSummaryMenu();
                break;
            case 2: // all account summary
                pause("|| Selected all account summary");
                allAccountSummaryMenu();
                break;
            case 3: // go back to main menu
                pause("|| Going back to Main menu");
                break;
        }
    }

    // EFFECTS: takes user input to decide and display summary of individual account
    private void individualAccountSummaryMenu() {
        Account selectedAccount = selectExistingAccount();
        System.out.print("Summary of " + selectedAccount.getAccountName()
                + "<" + selectedAccount.getAccountType() + ">");
        ArrayList<Transaction> transactions = selectedAccount.getTransaction();
        String[][] data = new String[transactions.size()][6];
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            String[] row = new String[6];
            row[0] = t.getFrom().getAccountName();
            row[1] = t.getTo().getAccountName();
            row[2] = String.valueOf(t.getAmount());
            row[3] = t.getTitle();
            row[4] = t.getDate().toString();
            row[5] = t.getDesc();
            data[i] = row;
        }
        pause(stringTable(data));
    }

    // EFFECTS: Displays all accounts sorted by type
    private void allAccountSummaryMenu() {
        String separator = "------------------------------------------------------------------";
        System.out.println(summarizeAccountType(user.getIncome()));
        System.out.println(separator);
        System.out.println(summarizeAccountType(user.getAccumulator()));
        System.out.println(separator);
        System.out.println(summarizeAccountType(user.getExpense()));
        System.out.println(separator);
        System.out.println(summarizeAccountType(user.getLoan()));
    }

    // EFFECTS: Returns table summarizing a list of single type of accounts
    private String summarizeAccountType(ArrayList<Account> accountType) {
        int total = 0;
        String stringAccountType = "";
        try {
            stringAccountType = accountType.get(0).getAccountType();
        } catch (IndexOutOfBoundsException e) {
            return "|||->  *Empty Type*";
        }
        String[] header = {stringAccountType, "Account Balance"};
        String[][] data = new String[accountType.size() + 1][2];
        Account acc;
        for (int i = 0; i < accountType.size(); i++) {
            acc = accountType.get(i);
            String[] row = new String[2];
            row[0] = acc.getAccountName();
            row[1] = String.valueOf(acc.getBalance());
            total += acc.getBalance();
            data[i] = row;
        }
        String[] finalRow = new String[2];
        finalRow[0] = "Total Balance";
        finalRow[1] = String.valueOf(total);
        data[accountType.size()] = finalRow;
        return accountType.get(0).getAccountType() + "\n" + AsciiTable.getTable(header,data);
    }

    // EFFECTS: Submenu for displaying all accounts and getting a valid account name from user
    private Account selectExistingAccount() {
        while (true) {
            System.out.println("||| List of all accounts\n ||| Income:");
            selectExistingAccountHelper(user.getIncome());
            System.out.println("||| Expense:");
            selectExistingAccountHelper(user.getExpense());
            System.out.println("||| Accumulator:");
            selectExistingAccountHelper(user.getAccumulator());
            System.out.println("||| Loan:");
            selectExistingAccountHelper(user.getLoan());
            command = input.next().toLowerCase();
            try {
                return user.findAccountFromString(command);
            } catch (AccountNotFoundException e) {
                System.out.println("|||-> Invalid Account Name(Account does not exist)");
            }
        }
    }

    // EFFECTS: helper for select existing account to display accounts from given ArrayList
    private void selectExistingAccountHelper(ArrayList<Account> accountList) {
        for (Account a: accountList) {
            System.out.println("|||   - " + a.getAccountName());
        }
    }

    // EFFECTS: repeatedly asks user for transaction ID until correct input is provided
    private Transaction takeInputTransactionID(String textToDisplay) {
        int possibleID;
        while (true) {
            System.out.print(textToDisplay + ": ");
            command = input.next().toLowerCase();
            possibleID = Integer.parseInt(command);
            for (Transaction t: user.getTransactionList()) {
                if (possibleID == t.getTransactionID()) {
                    pause("Found ID: " + possibleID);
                    return t;
                }
            }
            pause("Invalid input: please enter a valid transaction ID");
        }
    }

    // EFFECTS: repeatedly asks user for yes or no until correct input is provided
    private boolean takeInputYesNo(String textToDisplay,String successMessage) {
        while (true) {
            System.out.print(textToDisplay + " (y/n): ");
            command = input.next().toLowerCase();
            if (command.equals("y") || command.equals("yes")) {
                System.out.println("Entered: " + command);
                pause(successMessage);
                return true;
            } else if (command.equals("n") || command.equals("no")) {
                pause("Entered: " + command);
                return false;
            } else {
                pause("Invalid input: please enter 'y' or 'n'");
            }
        }
    }

    // EFFECTS: repeatedly asks user for Natural Number until correct input is provided
    private int takeInputNatural(String textToDisplay) {
        boolean loop = true;
        int output = 0;
        while (loop) {
            System.out.print(textToDisplay + ": ");
            try {
                command = input.next().toLowerCase();
                output = Integer.parseInt(command);
                if (output <= 0) {
                    throw new Exception();
                }
                loop = false;
                System.out.println();
                pause("Entered: " + output);
            } catch (Exception e) {
                pause("Invalid input: please enter a Natural Number");
            }
        }
        return output;
    }

    // !!! remove -1 functionality
    // EFFECTS: Attempts to take input repeatedly until correct data is received,
    //          user may enter -1 to exit (which should be handled in the calling function)
    private int takeInputIntegerRange(String textToDisplay,int lowerInclusive, int upperInclusive) {
        int selected;
        while (true) {
            System.out.println(textToDisplay + "\n -1 to go back");
            command = input.next().toLowerCase();
            try {
                selected = Integer.parseInt(command);
                if (selected == -1) {
                    return -1;
                }
                if (lowerInclusive <= selected && selected <= upperInclusive) {
                    pause("Valid input " + selected + " received");
                    return selected;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Invalid input: please enter a valid number in range"
                        + lowerInclusive + "," + upperInclusive + "]");
            }
        }
    }

    // EFFECTS: repeatedly asks user for valid date until correct input is provided
    private LocalDate takeInputDate(String textToDisplay) {
        boolean loop = true;
        LocalDate output = null;
        while (loop) {
            System.out.print(textToDisplay + "(yyyy-mm-dd): ");
            try {
                command = input.next().toLowerCase();
                output = LocalDate.parse(command);
                loop = false;
                pause("Entered: " + output);
            } catch (Exception e) {
                pause("Invalid input: please enter a valid date");
            }
        }
        return output;
    }

    // EFFECTS: repeatedly asks user for valid account name until correct input is provided
    private Account takeInputAccount(String textToDisplay) {
        while (true) {
            System.out.print(textToDisplay + ": ");
            command = input.next().toLowerCase();
            try {
                pause("Account found");
                return user.findAccountFromString(command);
            } catch (AccountNotFoundException e) {
                pause("Invalid Account Name(Account does not exist)");
            }
        }
    }

    // EFFECTS: asks user for a string with given question
    private String takeInputString(String textToDisplay) {
        System.out.print(textToDisplay + ": ");
        return input.next();
    }

    // EFFECTS: Displays given text and waits for 1 second
    private void pause(String textToDisplay) {
        try {
            System.out.println(textToDisplay);
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            // User interrupt, go back to regular execution
        }
    }

    private String stringTable(String[][] data) {
        String[] headers = {"from","to","amount","title","date","Description"};
        return AsciiTable.getTable(headers,data);
    }
}
