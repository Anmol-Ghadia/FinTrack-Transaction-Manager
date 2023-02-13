package ui;

import Exceptions.AccountNotFoundException;
import com.github.freva.asciitable.AsciiTable;
import model.Account;
import model.Accumulator;
import model.Expense;
import model.Transaction;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class FinTrack {
    private ArrayList<Account> accumulator;
    private ArrayList<Account> expense;
    private ArrayList<Account> income;
    private ArrayList<Account> loan;
    private ArrayList<Transaction> transactionList;
    private Scanner input;
    private boolean appRunning = true;
    private int transactionCount = 0;
    String command;
    private final String menu0 = "Main menu\n 1) Transaction\n 2) Account\n 3) Summary\n 4) quit";
    private final String menu1 = "Transaction\n 1) Add new\n 2) Modify\n 3) Delete\n 4) [Back] Main Menu";
    private final String menu3 = "Summary\n 1) Individual Account\n 2) All Account\n 3) [Back] Main Menu";

    public FinTrack() {
        command = null;
        runConsoleApp();
    }


    private void runConsoleApp() {
        init();
        while (appRunning) {
            System.out.println(menu0);
            command = input.next().toLowerCase();
            handleMenu0();
        }
    }

    @SuppressWarnings("methodlength")
    private void handleMenu0() {
        switch (command) {
            case "1":
                // transaction operation
                System.out.println("selected transaction");
                pause();
                handleMenu1();
                break;
            case "2":
                // account operation
                System.out.println("selected account");
                pause();
//                handleMenu2();
                break;
            case "3":
                // Account summary
                System.out.println("selected Summary");
                pause();
                handleMenu3();
                break;
            case "4":
                // end of app
                appRunning = false;
                System.out.println("End of app");
                pause();
                break;
            default:
                // incorrect input
                System.out.println("invalid input");
                pause();
                break;
        }
    }

    @SuppressWarnings("methodlength")
    private void handleMenu3() {
        boolean loop = true;
        while (loop) {
            System.out.println(menu3);
            command = input.next().toLowerCase();
            switch (command) {
                case "1":
                    System.out.println("selected Individual Account summary");
                    pause();
                    individualAccountSummary();
                    break;
                case "2":
                    System.out.println("selected All account summary");
                    pause();
//                    allAccountSummary();
                    break;
                case "3":
                    System.out.println("Going back to Main menu");
                    pause();
                    loop = false;
                    break;
                default:
                    // incorrect input
                    System.out.println("invalid input");
                    pause();
                    break;
            }
        }
    }

    private void individualAccountSummary() {
        Account selectedAccount = selectAccount();
        System.out.print("Summary of " + selectedAccount.getAccountName());
        System.out.println("<" + selectedAccount.getAccountType() + ">");
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
        System.out.println(stringTable(data));
    }


    @SuppressWarnings("methodlength")
    private Account selectAccount() {
        while (true) {
            System.out.println("List of all accounts");
            System.out.println("Income:");
            for (Account a: income) {
                System.out.println(" - " + a.getAccountName());
            }
            System.out.println("Expense:");
            for (Account a: expense) {
                System.out.println(" - " + a.getAccountName());
            }
            System.out.println("Accumulator:");
            for (Account a: accumulator) {
                System.out.println(" - " + a.getAccountName());
            }
            System.out.println("Loan:");
            for (Account a: loan) {
                System.out.println(" - " + a.getAccountName());
            }
            command = input.next().toLowerCase();
            try {
                return findAccountFromString(command);
            } catch (AccountNotFoundException e) {
                System.out.println("Invalid Account Name(Account does not exist)");
            }
        }
    }

    @SuppressWarnings("methodlength")
    private void handleMenu1() {
        boolean loop = true;
        while (loop) {
            System.out.println(menu1);
            command = input.next().toLowerCase();
            switch (command) {
                case "1":
                    System.out.println("selected new transaction");
                    pause();
                    newTransaction();
                    break;
                case "2":
                    System.out.println("selected Modify transaction");
                    pause();
                    modifyTransaction();
                    break;
                case "3":
                    System.out.println("selected delete transaction");
                    pause();
                    deleteTransaction();
                    break;
                case "4":
                    System.out.println("Going back to Main menu");
                    pause();
                    loop = false;
                    break;
                default:
                    // incorrect input
                    System.out.println("invalid input");
                    pause();
                    break;
            }
        }
    }

    private void deleteTransaction() {
        System.out.println("Delete Transaction");
        printAllTransactions();
        Transaction t = takeInputTransactionID("Enter Transaction ID to delete");
        boolean shouldDelete = takeInputYesNo("Are you sure that you want to delete the transaction?",
                "Deleting transaction with ID: " + t.getTransactionID());
        if (shouldDelete) {
            if (!transactionList.remove(t)) {
                System.out.println("transaction not found in transaction list");
            }
            if (!t.getFrom().deleteTransaction(t)) {
                System.out.println("transaction not found in FROM account");
            }
            if (!t.getTo().deleteTransaction(t)) {
                System.out.println("transaction not found in TO account");
            }
        } else {
            System.out.println("Aborting Delete");
        }
        pause();
    }

    private void modifyTransaction() {
        printAllTransactions();
        Transaction t = takeInputTransactionID("Enter Transaction ID to modify");
        String[][] data = {{t.getFrom().getAccountName(),t.getTo().getAccountName(),
                        String.valueOf(t.getAmount()),t.getTitle(),
                        t.getDate().toString(),t.getDesc()}};
        System.out.println(stringTable(data));
        System.out.println("What do you want to modify?");
        System.out.println(" 1) From\n 2) To\n 3) Amount\n 4) Title\n 5) Date\n 6) Description\n");
        int selected;
        while (true) {
            command = input.next().toLowerCase();
            try {
                selected = Integer.parseInt(command);
                if (selected < 7 && selected > 0) {
                    break;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Invalid input: please enter a valid number");
            }
        }
        modifyTransactionSubMenu(selected, t);
    }

    @SuppressWarnings("methodlength")
    private void modifyTransactionSubMenu(int selected,Transaction transaction) {
        switch (selected) {
            case 1: // modify from
                Account from = takeInputAccount("Enter new credit Account");
                transaction.setFrom(from);
                break;
            case 2: // modify to
                Account to = takeInputAccount("Enter new debit Account");
                transaction.setTo(to);
                break;
            case 3: // modify amount
                int amt = takeInputNatural("Enter new transaction Amount");
                transaction.setAmount(amt);
                break;
            case 4: // modify title
                String title = takeInputString("Enter new Title");
                transaction.setTitle(title);
                break;
            case 5: // modify date
                LocalDate date = takeInputDate("Enter new Date");
                transaction.setDate(date);
                break;
            case 6: // modify desc
                String desc = takeInputString("Enter new description");
                transaction.setDesc(desc);
                break;
        }
        System.out.println("Modified Transaction");
        pause();
    }

    private void printAllTransactions() {
        System.out.println("List of all transaction with ID");
        String[] header = {"ID","From","To","amount","title","date","description"};
        Transaction t;
        String[][] data = new String[transactionList.size()][7];
        for (int i = 0; i < transactionList.size(); i++) {
            t = transactionList.get(i);
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
        System.out.println(AsciiTable.getTable(header,data));
    }

    private Transaction takeInputTransactionID(String textToDisplay) {
        int possibleID;
        while (true) {
            System.out.println(textToDisplay);
            command = input.next().toLowerCase();
            possibleID = Integer.parseInt(command);
            for (Transaction t: transactionList) {
                if (possibleID == t.getTransactionID()) {
                    System.out.println("Found ID: " + possibleID);
                    pause();
                    return t;
                }
            }
            System.out.println("Invalid input: please enter a valid transaction ID");
            pause();
        }
    }


    private void newTransaction() {
        Account from = takeInputAccount("Enter Credit Account:");
        Account to = takeInputAccount("Enter Debit Account:");
        int amt = takeInputNatural("enter amt: ");
        LocalDate date = takeInputDate("Enter date of transaction:");
        String title = takeInputString("Enter title of transaction:");
        String desc = takeInputString("Enter description of transaction:");
        String[][] data = {
                {from.getAccountName(),to.getAccountName(),String.valueOf(amt),title,date.toString(),desc}
        };
        System.out.println(stringTable(data));
        boolean add = takeInputYesNo("Do you want to add the above transaction?",
                "Added transaction");
        if (add) {
            Transaction t = new Transaction(transactionCount, from, to, amt, date, title, desc);
            from.addTransaction(t);
            to.addTransaction(t);
            transactionList.add(t);
            transactionCount++;
        } else {
            System.out.println("aborting transaction");
            pause();
        }

    }

    private boolean takeInputYesNo(String textToDisplay,String successMessage) {
        while (true) {
            System.out.println(textToDisplay + " (y/n)");
            command = input.next().toLowerCase();
            if (command.equals("y") || command.equals("yes")) {
                System.out.println("Entered: " + command);
                System.out.println(successMessage);
                pause();
                return true;
            } else if (command.equals("n") || command.equals("no")) {
                System.out.println("Entered: " + command);
                pause();
                return false;
            } else {
                System.out.println("Invalid input: please enter 'y' or 'n'");
                pause();
            }

        }
    }

    private String stringTable(String[][] data) {
        String[] headers = {"from","to","amount","title","date","Description"};
        return AsciiTable.getTable(headers,data);
    }

    public void init() {
        transactionList = new ArrayList<>();
        accumulator = new ArrayList<>();
        expense = new ArrayList<>();
        loan = new ArrayList<>();
        income = new ArrayList<>();


        // TEMP
        Account acc1 = new Accumulator("SAVINGS","CIBC");
        accumulator.add(acc1);
        Account travel = new Expense("TRAVEL","traveling costs");
        expense.add(travel);
        // TEMP


        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // function that waits for 1 second
    private void pause() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            // User interrupt, go back to regular execution
        }
    }

    // function that waits for the given interval
    private void pause(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception e) {
            // User interrupt, go back to regular execution
        }
    }

    private int takeInputNatural(String textToDisplay) {
        boolean loop = true;
        int output = 0;
        while (loop) {
            System.out.println(textToDisplay);
            try {
                command = input.next().toLowerCase();
                output = Integer.parseInt(command);
                if (output <= 0) {
                    throw new Exception();
                }
                loop = false;
                System.out.println("Entered: " + output);
                pause();
            } catch (Exception e) {
                System.out.println("Invalid input: please enter a Natural Number");
                pause();
            }
        }
        return output;
    }

    private LocalDate takeInputDate(String textToDisplay) {
        boolean loop = true;
        LocalDate output = null;
        while (loop) {
            System.out.println(textToDisplay);
            System.out.println("Enter the date in yyyy-mm-dd format");
            try {
                command = input.next().toLowerCase();
                output = LocalDate.parse(command);
                loop = false;
                System.out.println("Entered: " + output);
                pause();
            } catch (Exception e) {
                System.out.println("Invalid input: please enter a valid date");
                pause();
            }
        }
        return output;
    }

    private Account takeInputAccount(String textToDisplay) {
        while (true) {
            System.out.println(textToDisplay);
            command = input.next().toLowerCase();
            try {
                return findAccountFromString(command);
            } catch (AccountNotFoundException e) {
                System.out.println("Invalid Account Name(Account does not exist)");
            }
        }
    }

    private String takeInputString(String textToDisplay) {
        System.out.println(textToDisplay);
        return input.next();
    }

    private Account findAccountFromString(String accountName) throws AccountNotFoundException {
        for (Account acc: accumulator) {
            if (acc.getAccountName().toLowerCase().equals(accountName)) {
                return acc;
            }
        }
        for (Account acc: expense) {
            if (acc.getAccountName().toLowerCase().equals(accountName)) {
                return acc;
            }
        }
        for (Account acc: income) {
            if (acc.getAccountName().toLowerCase().equals(accountName)) {
                return acc;
            }
        }
        for (Account acc: loan) {
            if (acc.getAccountName().toLowerCase().equals(accountName)) {
                return acc;
            }
        }
        throw new AccountNotFoundException();
    }
}
