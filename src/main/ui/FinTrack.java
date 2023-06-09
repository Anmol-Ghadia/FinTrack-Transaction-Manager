package ui;

import exceptions.AccountNotFoundException;
import com.github.freva.asciitable.AsciiTable;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *   Console app for financial tracking
 */
public class FinTrack {
    User user;
    private Scanner input;
    private int transactionCount;
    String command;
    private final String saveFileLocation = "./data/userData.json";

    // EFFECTS: runs the console app
    public FinTrack() {
        runConsoleApp();
    }

    // MODIFIES: this
    // EFFECTS: Initializes global variables
    private void runConsoleApp() {
        init();
        loadDefault();
        do {
            String menu0 = "|Main menu\n| 1) Transaction\n| 2) Account\n| 3) Summary\n"
                    + "| 4) Save State \n| 5) Load State \n| 6) quit";
            System.out.println(menu0);
            command = input.next().toLowerCase();
        } while (handleMenu0());
    }

    // MODIFIES: this
    // EFFECTS: Initializes global variables
    private void init() {
        user = new User();
        command = null;
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this.user
    // EFFECTS: loads default data if user wants to
    private void loadDefault() {
        boolean defaultData = takeInputYesNo("Do you want to load default data?"
                + " this is different from save data", "Loading Data");
        if (defaultData) {
            ExampleData ed = new ExampleData(user, transactionCount);
            transactionCount = ed.getTransactionCount();
        } else {
            transactionCount = 0;
        }
    }

    // EFFECTS: Displays and takes user input for Main menu(0)
    //          returns false to stop app, to continue: true
    private boolean handleMenu0() {
        if (command.equals("1")) {
            pause("|-> Selected Transaction");
            handleMenu1();
        } else if (command.equals("2")) {
            pause("|-> Selected Account");
            handleMenu2();
        } else if (command.equals("3")) {
            pause("|-> Selected Summary");
            handleMenu3();
        } else if (command.equals("4")) {
            pause("|-> Selected Save State");
            saveUser();
        } else if (command.equals("5")) {
            pause("|-> Selected Load State");
            loadUser();
        } else if (command.equals("6")) {
            pause("|-> Exiting :(");
            return false;
        } else {
            pause("|-> **Invalid Input**");
        }
        return true;
    }

    // EFFECTS: Displays and takes user input for Transaction Menu(1)
    private void handleMenu1() {
        String menu1 = "||Transaction\n|| 1) Create\n|| 2) Modify\n|| 3) Delete\n|| 4) [Back] Main Menu";
        int selected = takeInputIntegerRange(menu1, 4);
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
        String[][] data = {{from.getAccountName(), to.getAccountName(),
                String.valueOf(amt), title,
                date.toString(), desc}};
        System.out.println(stringTable(data));
        boolean add = takeInputYesNo("|||-> Do you want to add the above transaction?", "|||-> Adding Transaction");
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
        getAllTransactionsAsTable();
        Transaction t = takeInputTransactionID("||| Enter Transaction ID to modify");
        String[][] data = {{t.getFrom().getAccountName(), t.getTo().getAccountName(),
                String.valueOf(t.getAmount()), t.getTitle(),
                t.getDate().toString(), t.getDesc()}};
        pause(stringTable(data));
        int selected = takeInputIntegerRange("||| What do you want to modify?\n 1) From\n 2) To\n"
                + " 3) Amount\n 4) Title\n 5) Date\n 6) Description", 6);
        if (selected != -1) {
            modifyTransactionMenuHandleInput(selected, t);
        }
    }

    // EFFECTS: Returns a (string)table for all transactions
    private void getAllTransactionsAsTable() {
        System.out.println("||| List of all transactions with ID");
        String[] header = {"ID", "From", "To", "amount", "title", "date", "description"};
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
        pause(AsciiTable.getTable(header, data));
    }

    // EFFECTS: Handles input for modify transaction menu
    private void modifyTransactionMenuHandleInput(int selected, Transaction transaction) {
        if (selected == 1) {
            // modify from
            Account from = takeInputAccount("||| Enter new credit Account");
            transactionNewFrom(transaction, from);
        } else if (selected == 2) {
            // modify to
            Account to = takeInputAccount("||| Enter new debit Account");
            transactionNewTo(transaction, to);
        } else if (selected == 3) {
            // modify amount
            int amt = takeInputNatural("||| Enter new transaction Amount");
            transaction.setAmount(amt);
        } else if (selected == 4) {
            // modify title
            String title = takeInputString("||| Enter new Title");
            transaction.setTitle(title);
        } else if (selected == 5) {
            // modify date
            LocalDate date = takeInputDate("||| Enter new Date");
            transaction.setDate(date);
        } else {
            // modify desc
            String desc = takeInputString("||| Enter new Description");
            transaction.setDesc(desc);
        }
        pause("|||-> Modified Transaction");
    }

    // MODIFIES: user, transaction
    // EFFECTS: changes the transaction's credit(from) account
    private void transactionNewTo(Transaction transaction, Account to) {
        transaction.getTo().deleteTransaction(transaction);
        transaction.setTo(to);
        to.addTransaction(transaction);
    }

    // MODIFIES: user, transaction
    // EFFECTS: changes the transaction's credit(from) account
    private void transactionNewFrom(Transaction transaction, Account from) {
        transaction.getFrom().deleteTransaction(transaction);
        transaction.setFrom(from);
        from.addTransaction(transaction);
    }

    // EFFECTS: Takes user input to delete transaction from appropriate places by ID
    private void deleteTransactionMenu() {
        System.out.println("||| Delete Transaction");
        getAllTransactionsAsTable();
        Transaction t = takeInputTransactionID("||| Enter Transaction ID to delete");
        boolean shouldDelete = takeInputYesNo("||| Are you sure that you want to delete the transaction?",
                "||| Deleting transaction with ID: " + t.getTransactionID());
        if (shouldDelete) {
            deleteTransaction(t);
        } else {
            pause("|||-> Aborting Delete");
        }
    }

    // MODIFIES: user, transaction
    // EFFECTS: deletes transaction from all appropriate places
    private void deleteTransaction(Transaction t) {
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
        String menu2 = "||Account\n|| 1) Add new\n|| 2) Modify\n|| 3) Delete\n|| 4) [Back] Main Menu";
        int selected = takeInputIntegerRange(menu2, 4);
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
                + "||| 4) Loan", 4);
        String accountName;
        while (true) {
            System.out.print("||| Enter Account name: ");
            accountName = input.next().toUpperCase();
            try {
                user.findAccountFromString(accountName);
                pause("|||-> **Enter a unique Account name(Account name already exists)**\n Try again");
            } catch (AccountNotFoundException e) {
                break;
            }
        }
        String desc = takeInputString("||| Enter Description for the Account");
        createNewAccountMenuHandleMenu(select, accountName, desc);
    }

    // EFFECTS: Handles input for create account menu
    private void createNewAccountMenuHandleMenu(int select, String accountName, String desc) {
        Account newAccount;
        switch (select) {
            case 1:
                newAccount = new Income(accountName.toUpperCase(), desc);
                user.addIncome(newAccount);
                break;
            case 2:
                newAccount = new Accumulator(accountName.toUpperCase(), desc);
                user.addAccumulator(newAccount);
                break;
            case 3:
                newAccount = new Expense(accountName.toUpperCase(), desc);
                user.addExpense(newAccount);
                break;
            case 4:
                newAccount = new Loan(accountName.toUpperCase(), desc);
                user.addLoan(newAccount);
                break;
        }
        pause("|||-> Account created");
    }

    // EFFECTS: takes user input to modify account details and modifies the account
    private void modifyAccountMenu() {
        Account acc = takeInputAccount("||| Enter Account Name");
        int selected = takeInputIntegerRange("||| Modify Account's \n||| 1) name \n||| 2) description", 2);
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
        for (int i = 0; i < transactions.size(); ) {
            deleteTransaction(transactions.get(i));
        }
    }

    // EFFECTS: Displays summary menu and takes user input for correct action
    private void handleMenu3() {
        String menu3 = "||Summary\n|| 1) Individual Account\n|| 2) All Account\n|| 3) [Back] Main Menu";
        int selected = takeInputIntegerRange(menu3, 3);
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
        System.out.println("Summary of " + selectedAccount.getAccountName()
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
        String stringAccountType;
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
        return accountType.get(0).getAccountType() + "\n" + AsciiTable.getTable(header, data);
    }

    // EFFECTS: Submenu for displaying all accounts and getting a valid account name from user
    private Account selectExistingAccount() {
        while (true) {
            System.out.println("||| List of all accounts\n||| Income:");
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
                pause("|||-> Invalid Account Name(Account does not exist)");
            }
        }
    }

    // EFFECTS: helper for select existing account to display accounts from given ArrayList
    private void selectExistingAccountHelper(ArrayList<Account> accountList) {
        for (Account a : accountList) {
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
            for (Transaction t : user.getTransactionList()) {
                if (possibleID == t.getTransactionID()) {
                    pause("Found ID: " + possibleID);
                    return t;
                }
            }
            pause("Invalid input: please enter a valid transaction ID");
        }
    }

    // EFFECTS: repeatedly asks user for yes or no until correct input is provided
    private boolean takeInputYesNo(String textToDisplay, String successMessage) {
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
    private int takeInputIntegerRange(String textToDisplay, int upperInc) {
        int selected;
        while (true) {
            System.out.print(textToDisplay + "\n(-1 to Main menu) :");
            command = input.next().toLowerCase();
            try {
                selected = Integer.parseInt(command);
                if (selected == -1) {
                    return -1;
                }
                if (1 <= selected && selected <= upperInc) {
                    pause("Valid input " + selected + " received");
                    return selected;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Invalid input: please enter a valid number in range ["
                        + 1 + "," + upperInc + "]");
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
                Account a = user.findAccountFromString(command);
                pause("Account found");
                return a;
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

    // returns a string formatted to look like table based on the transaction data
    private String stringTable(String[][] data) {
        String[] headers = {"From", "To", "Amount", "Title", "Date", "Description"};
        return AsciiTable.getTable(headers, data);
    }

    // EFFECTS: Save the current user in a file
    private void saveUser() {
        try {
            JsonWriter jsonWriter = new JsonWriter(saveFileLocation);
            jsonWriter.write(user);
            pause("Saved data :)");
        } catch (FileNotFoundException e) {
            pause("Cannot write to file");
        }
    }

    // MODIFIES: this.user
    // EFFECTS: Load the user data saved in the file
    private void loadUser() {
        try {
            JsonReader jsonReader = new JsonReader(saveFileLocation);
            this.user = jsonReader.read();
            pause("Loaded data successfully :)");
        } catch (IOException e) {
            System.out.println("unable to read from file");
        }
    }
}