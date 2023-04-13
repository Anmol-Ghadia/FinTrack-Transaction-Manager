package ui;

import exceptions.AccountNotFoundException;
import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 * GUI for FinTrack app
 */
public class GuiSwing implements Runnable {
    private User user;
    private int transactionCount;
    private JFrame frame;
    private JTextField viewTransactionsAccountName;
    private String[][] transactionsList;
    private String[] transactionsListColumns;
    private JTable tableTransactions;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private final String saveFileLocation = "./data/userData.json";
    private JTextField transactionAmount;
    private JTextField transactionDate;
    private  JTextField accountFrom;
    private  JTextField accountTo;
    private JTextField transactionTitle;
    private JTextField transactionDesc;
    private Label createTransactionOutput;
    private JTextField deleteTransactionID;
    private Color defaultLabelColor;
    private Label loadSaveOutput;
    private Label viewTransactionsOptionsOutput;
    private Label deleteTransactionOutput;
    private Label createAccountOutput;
    private JRadioButton accumulator;
    private JRadioButton income;
    private JRadioButton expense;
    private JRadioButton loan;
    private JTextField accountName;
    private JTextField accountDesc;
    private final String logoPath = "./images/logo.png";

    // EFFECTS: Initializes the app
    // MODIFIES: user
    public GuiSwing() {
        splashScreen();
        user = new User();
        transactionCount = 0;
        makeFrame();
    }

    // EFFECTS: packs the frame and makes it visible
    @Override
    public void run() {
        frame.pack();
        frame.setVisible(true);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(logoPath));
    }

    // EFFECTS: Creates a splash Screen
    private void splashScreen() {
        JWindow window = new JWindow();
        JLabel label = new JLabel(new ImageIcon(logoPath));
        window.getContentPane().add(label);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        window.setVisible(false);
        window.dispose();
    }

    // EFFECTS: Makes the main window of the app
    public void makeFrame() {
        frame = new JFrame("FinTrack");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(500, 500);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();

        makeLeftPanel();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 2;
        frame.add(leftPanel,cons);

        makeRightPanel();
        cons.gridx = 2;
        cons.gridy = 0;
        cons.gridwidth = 2;
        frame.add(rightPanel,cons);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?");
                if (option == JOptionPane.YES_OPTION) {
                    // call your function here
                    closeWindow();
                }
            }
        });
    }

    // EFFECTS: makes the left-hand side panel
    private JPanel makeLeftPanelCreateTransactions() {
        JPanel createTransactionPanel = new JPanel();
        createTransactionPanel.setLayout(new GridLayout(7,2));

        addComponentsOne(createTransactionPanel);
        addComponentsTwo(createTransactionPanel);
        addComponentsThree(createTransactionPanel);

        TitledBorder titleViewCreateTransaction;
        titleViewCreateTransaction = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Create Transaction");
        titleViewCreateTransaction.setTitleJustification(TitledBorder.LEFT);

        createTransactionPanel.setBorder(titleViewCreateTransaction);
        return createTransactionPanel;
    }

    // MODIFIES: this.user
    // EFFECTS: Load the user data saved in the file
    private void loadUser() {
        try {
            JsonReader jsonReader = new JsonReader(saveFileLocation);
            this.user = jsonReader.read();
            transactionCount = 100;
            setBackgroundDefaultTimer(loadSaveOutput,"              ", "Loaded",Color.green);
        } catch (IOException e) {
            setBackgroundDefaultTimer(loadSaveOutput,"                      ", "Unable to \n Load Data",Color.RED);
        }
    }

    // EFFECTS: adds amount, date, create transaction Button
    private void addComponentsThree(JPanel createTransactionPanel) {
        Label transactionAmountLabel = new Label("Amount:");
        transactionAmountLabel.setAlignment(Label.RIGHT);
        createTransactionPanel.add(transactionAmountLabel);

        transactionAmount = new JTextField();
        transactionAmount.setHorizontalAlignment(JTextField.CENTER);
        createTransactionPanel.add(transactionAmount);

        Label transactionDateLabel = new Label("Date(yyyy-mm-dd):");
        transactionDateLabel.setAlignment(Label.RIGHT);
        createTransactionPanel.add(transactionDateLabel);

        transactionDate = new JTextField();
        transactionDate.setHorizontalAlignment(JTextField.CENTER);
        createTransactionPanel.add(transactionDate);

        createTransactionOutput = new Label("");
        defaultLabelColor = createTransactionOutput.getBackground();
        createTransactionPanel.add(createTransactionOutput);

        JButton createTransactionButton = new JButton("Create Transaction");
        createTransactionButton.addActionListener(e -> extractTransactionInformation());
        createTransactionPanel.add(createTransactionButton);
    }

    // EFFECTS: Extracts Information relevant for making a transaction
    private void extractTransactionInformation() {
        String fromString = accountFrom.getText();
        String toString = accountTo.getText();
        String title = transactionTitle.getText();
        String desc = transactionDesc.getText();
        String amountString = transactionAmount.getText();
        String dateString = transactionDate.getText();
        try {
            Account from = checkFromAccountValidity(fromString);
            Account to = checkToAccountValidity(toString);
            int amount = checkAmountValidity(amountString);
            LocalDate date = checkDateValidity(dateString);

            makeTransaction(from,to,title,desc,amount,date);
            setBackgroundDefaultTimer(createTransactionOutput,"                   ",
                    "Created Transaction",Color.green);
            pressedViewTransactions();
        } catch (Exception e) {
            setBackgroundDefaultTimer(createTransactionOutput,"                             ",
                    "Unable to Create Transaction",Color.red);
        }
    }

    // EFFECTS: creates a transaction
    // MODIFIES: user
    private void makeTransaction(Account from, Account to, String title, String desc, int amount, LocalDate date) {
        Transaction transaction = new Transaction(transactionCount,from,to,amount,date,title,desc);
        user.addTransactionComplete(transaction);
        transactionCount++;
    }

    // EFFECTS: checks the validity of date and returns the correct date
    // Throws Exception when the date is in incorrect format
    private LocalDate checkDateValidity(String dateString) throws Exception {
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            transactionDate.setBackground(Color.red);
            setBackgroundDefaultTimer(transactionDate);
            throw new Exception();
        }
    }

    // EFFECTS: adds a timed transition for the given label to display a text temporarily with color 
    // MODIFIES: label
    private void setBackgroundDefaultTimer(Label label,String defaultText, String tempText, Color tempColor) {
        label.setText(tempText);
        label.setBackground(tempColor);
        int delay = 6000; // milliseconds
        Timer timer = new Timer(delay, f -> {
            label.setBackground(defaultLabelColor);
            label.setText(defaultText);
        });
        timer.setRepeats(false);
        timer.start();
    }

    // EFFECTS: adds a timed transition for the given TextField to display a text temporarily with color 
    // MODIFIES: textField
    private void setBackgroundDefaultTimer(JTextField textField) {
        int delay = 6000; // milliseconds
        Timer timer = new Timer(delay, f -> textField.setBackground(Color.WHITE));
        timer.setRepeats(false);
        timer.start();
    }

    // EFFECTS: checks the validity of amount and returns the correct amount
    // Throws Exception when the amount is not a number
    private int checkAmountValidity(String amountString) throws Exception {
        try {
            return Integer.parseInt(amountString);
        } catch (NumberFormatException e) {
            transactionAmount.setBackground(Color.red);
            setBackgroundDefaultTimer(transactionAmount);
            throw new Exception();
        }
    }

    // EFFECTS: checks the validity of "to" account and returns the correct account
    // Throws Exception when the account is not found
    private Account checkToAccountValidity(String toString) throws Exception {
        try {
            return user.findAccountFromString(toString);
        } catch (AccountNotFoundException e) {
            accountTo.setBackground(Color.red);
            setBackgroundDefaultTimer(accountTo);
            throw new Exception();
        }
    }

    // EFFECTS: checks the validity of "from" account and returns the correct account
    // Throws Exception when the account is not found
    private Account checkFromAccountValidity(String fromString) throws Exception {
        try {
            return user.findAccountFromString(fromString);
        } catch (AccountNotFoundException e) {
            accountFrom.setBackground(Color.red);
            setBackgroundDefaultTimer(accountFrom);
            throw new Exception();
        }
    }

    // EFFECTS: adds account "from", "to" to the given panel
    // MODIFIES: createTransactionPanel
    private void addComponentsOne(JPanel createTransactionPanel) {
        Label accountFromLabel = new Label("Account From:");
        accountFromLabel.setAlignment(Label.RIGHT);
        createTransactionPanel.add(accountFromLabel);

        accountFrom = new JTextField();
        accountFrom.setHorizontalAlignment(JTextField.CENTER);
        createTransactionPanel.add(accountFrom);

        Label accountToLabel = new Label("Account To:");
        accountToLabel.setAlignment(Label.RIGHT);
        createTransactionPanel.add(accountToLabel);

        accountTo = new JTextField();
        accountTo.setHorizontalAlignment(JTextField.CENTER);
        createTransactionPanel.add(accountTo);
    }

    // EFFECTS: adds title, description to the given createTransactionPanel
    // MODIFIES: createTransactionPanel
    public void addComponentsTwo(JPanel createTransactionPanel) {
        Label transactionTitleLabel = new Label("Title:");
        transactionTitleLabel.setAlignment(Label.RIGHT);
        createTransactionPanel.add(transactionTitleLabel);

        transactionTitle = new JTextField();
        transactionTitle.setHorizontalAlignment(JTextField.CENTER);
        createTransactionPanel.add(transactionTitle);

        Label transactionDescLabel = new Label("Description:");
        transactionDescLabel.setAlignment(Label.RIGHT);
        createTransactionPanel.add(transactionDescLabel);

        transactionDesc = new JTextField();
        transactionDesc.setHorizontalAlignment(JTextField.CENTER);
        createTransactionPanel.add(transactionDesc);
    }

    // EFFECTS: creates the left panel with its subcomponents
    private void makeLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();

        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 1;
        leftPanel.add(makeLeftPanelOptions(),cons);
        cons.gridy = 1;
        leftPanel.add(makeLeftPanelCreateAccount(),cons);
        cons.gridy = 2;
        leftPanel.add(makeLeftPanelCreateTransactions(),cons);
        cons.gridy = 3;
        leftPanel.add(makeLeftPanelDelete(),cons);
    }

    // EFFECTS: makes the left hand-side create account panel
    private JPanel makeLeftPanelCreateAccount() {
        JPanel createAccount = new JPanel();
        createAccount.setLayout(new GridLayout(5,2));

        createAccountMakeRadioButtons(createAccount);
        createAccountMakeAccountInformation(createAccount);

        TitledBorder titleViewTransactions;
        titleViewTransactions = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Create Account");
        titleViewTransactions.setTitleJustification(TitledBorder.LEFT);

        createAccount.setBorder(titleViewTransactions);
        return createAccount;
    }

    // EFFECTS: makes the labels and text fields necessary for creating an account
    private void createAccountMakeAccountInformation(JPanel createAccount) {
        Label accountNameLabel = new Label("Account Name");
        accountNameLabel.setAlignment(Label.RIGHT);
        createAccount.add(accountNameLabel);

        accountName = new JTextField();
        accountName.setHorizontalAlignment(JTextField.CENTER);
        createAccount.add(accountName);

        Label accountDescLabel = new Label("Account Description");
        accountDescLabel.setAlignment(Label.RIGHT);
        createAccount.add(accountDescLabel);

        accountDesc = new JTextField();
        accountDesc.setHorizontalAlignment(JTextField.CENTER);
        createAccount.add(accountDesc);

        createAccountOutput = new Label("");
        createAccountOutput.setAlignment(Label.CENTER);
        createAccount.add(createAccountOutput);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(e -> createAccountPressed());
        createAccount.add(createAccountButton);
    }

    // EFFECTS: creates the radio buttons for different types of account
    private void createAccountMakeRadioButtons(JPanel createAccount) {
        ButtonGroup accountTypeGroup = new ButtonGroup();

        accumulator = new JRadioButton("Accumulator");
        accumulator.setSelected(true);
        accountTypeGroup.add(accumulator);
        createAccount.add(accumulator);
        income = new JRadioButton("Income");
        accountTypeGroup.add(income);
        createAccount.add(income);
        expense = new JRadioButton("Expense");
        accountTypeGroup.add(expense);
        createAccount.add(expense);
        loan = new JRadioButton("Loan");
        accountTypeGroup.add(loan);
        createAccount.add(loan);
    }

    // EFFECTS: Attempts to make a new account
    private void createAccountPressed() {
        String name = accountName.getText();
        String desc = accountDesc.getText();
        try {
            user.findAccountFromString(name);
            // Account already exists
            setBackgroundDefaultTimer(createAccountOutput,"                      ",
                    "Account Already Exists",Color.RED);
            return;
        } catch (AccountNotFoundException e) {
            // Expected
        }
        if (accumulator.isSelected()) {
            user.addAccumulator(new Accumulator(name,desc));
        } else if (expense.isSelected()) {
            user.addExpense(new Expense(name, desc));
        } else if (income.isSelected()) {
            user.addIncome(new Income(name,desc));
        } else if (loan.isSelected()) {
            user.addLoan(new Loan(name,desc));
        } else {
            // no account type selected
            setBackgroundDefaultTimer(createAccountOutput,"                   ","Select Account Type",Color.red);
            return;
        }
        setBackgroundDefaultTimer(createAccountOutput,"               ","Account Created",Color.GREEN);
    }

    // EFFECTS: makes the left-hand side delete panel
    private JPanel makeLeftPanelDelete() {
        JPanel deletePanel = new JPanel();
        deletePanel.setLayout(new GridLayout(2,2));

        Label deleteTransactionIDLabel = new Label("Transaction ID:");
        deleteTransactionIDLabel.setAlignment(Label.RIGHT);
        deletePanel.add(deleteTransactionIDLabel);

        deleteTransactionID = new JTextField();
        deleteTransactionID.setHorizontalAlignment(JTextField.CENTER);
        deletePanel.add(deleteTransactionID);

        deleteTransactionOutput = new Label("");
        deleteTransactionOutput.setAlignment(Label.CENTER);
        deletePanel.add(deleteTransactionOutput);

        JButton deleteTransactionButton = new JButton("Delete");
        deleteTransactionButton.addActionListener(e -> deleteTransaction());
        deletePanel.add(deleteTransactionButton);

        TitledBorder titleViewTransactions;
        titleViewTransactions = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Delete Transaction");
        titleViewTransactions.setTitleJustification(TitledBorder.LEFT);

        deletePanel.setBorder(titleViewTransactions);
        return deletePanel;
    }

    // EFFECTS: deletes the transaction with the given id in the id text field
    private void deleteTransaction() {
        int id;
        try {
            id = Integer.parseInt(deleteTransactionID.getText());
        } catch (NumberFormatException e) { // Non-Numeric ID
            setBackgroundDefaultTimer(deleteTransactionOutput,"                 ",
                    "Invalid ID Format",Color.red);
            return;
        }
        Transaction foundTransaction = findTransaction(id,user.getTransactionList());
        if (foundTransaction == null) { // not found, let the user know
            setBackgroundDefaultTimer(deleteTransactionOutput, "                     ",
                    "Transaction Not Found",Color.red);
        } else {
            user.removeTransaction(foundTransaction);
            foundTransaction = findTransaction(id,foundTransaction.getTo().getTransaction());
            foundTransaction.getTo().deleteTransaction(foundTransaction);
            foundTransaction = findTransaction(id, foundTransaction.getFrom().getTransaction());
            foundTransaction.getFrom().deleteTransaction(foundTransaction);
            setBackgroundDefaultTimer(deleteTransactionOutput,"                   ",
                    "Deleted Transaction",Color.green);
            pressedViewTransactions();
        }
    }

    // EFFECTS: finds a transaction with the given id and returns it from the given list
    private Transaction findTransaction(int id, ArrayList<Transaction> transactionList) {
        Transaction foundTransaction = null;
        for (Transaction t: transactionList) {
            if (t.getTransactionID() == id) {
                foundTransaction = t;
            }
        }
        return foundTransaction;
    }

    // EFFECTS: makes the left-hand side panel for options
    private JPanel makeLeftPanelOptions() {
        JPanel optionsPanel = new JPanel();
        createLeftPanelOptionsSubComponents(optionsPanel);

        TitledBorder titleViewTransactions;
        titleViewTransactions = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Options");
        titleViewTransactions.setTitleJustification(TitledBorder.LEFT);

        optionsPanel.setBorder(titleViewTransactions);
        return optionsPanel;
    }

    // EFFECTS: Makes buttons and labels for the options panel
    private void createLeftPanelOptionsSubComponents(JPanel optionsPanel) {
        optionsPanel.setLayout(new GridLayout(1,4));

        JButton loadDataButton = new JButton("Load");
        loadDataButton.addActionListener(e -> loadUser());
        optionsPanel.add(loadDataButton);

//        cons.gridx = 1;
        JButton saveDataButton = new JButton("Save");
        saveDataButton.addActionListener(e -> saveUser());
        optionsPanel.add(saveDataButton);

        loadSaveOutput = new Label("                ");
        loadSaveOutput.setAlignment(Label.CENTER);
        optionsPanel.add(loadSaveOutput);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> closeWindow());
        optionsPanel.add(exit);
    }

    // EFFECTS: Closes the app properly
    private void closeWindow() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.getDescription());
        }
        frame.dispose();
    }

    // EFFECTS: Save the current user data in a file
    private void saveUser() {
        try {
            JsonWriter jsonWriter = new JsonWriter(saveFileLocation);
            jsonWriter.write(user);
            setBackgroundDefaultTimer(loadSaveOutput, "          ",
                    "Saved Data",Color.green);
        } catch (FileNotFoundException e) {
            setBackgroundDefaultTimer(loadSaveOutput, "                   ",
                    "Unable To Save Data",Color.RED);
        }
    }

    // EFFECTS: makes the right-hand side panel
    private void makeRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());


//        temporary
        makeTransactionsUITransactionsTableTempData();
        rightPanel.add(makeTransactionsTable(),BorderLayout.CENTER);
        rightPanel.add(makeRightPanelOptions(),BorderLayout.SOUTH);

        TitledBorder transactionViewBorder;
        transactionViewBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Transactions");
        transactionViewBorder.setTitleJustification(TitledBorder.CENTER);

        rightPanel.setBorder(transactionViewBorder);
    }

    // EFFECTS: creates default data for the table when starting the application
    private void makeTransactionsUITransactionsTableTempData() {
        transactionsList = new String[][]{
                {"999","Acc 1","Acc 6","1000","title 1","Desc 1"},
                {"998","Acc 2","Acc 5","500","title 2","Desc 2"},
                {"997","Acc 3","Acc 4","152","title 3","Desc 3"}};
        transactionsListColumns = new String[] {
                "Transaction ID",
                "Credit",
                "Debit",
                "Amount",
                "Title",
                "Description"
        };
    }

    // EFFECTS: Makes the table for transactions
    private JScrollPane makeTransactionsTable() {
        tableTransactions = new JTable(transactionsList,transactionsListColumns);
        return new JScrollPane(tableTransactions);
    }

    // EFFECTS: make options for the right panel
    private JPanel makeRightPanelOptions() {
        JPanel viewTransactionsOptionsPanel = new JPanel();
        viewTransactionsOptionsPanel.setLayout(new GridLayout(2,2));

        Label accountNameText = new Label("Account Name:");
        accountNameText.setAlignment(Label.RIGHT);
        viewTransactionsOptionsPanel.add(accountNameText);

        viewTransactionsAccountName = new JTextField();
        viewTransactionsAccountName.setHorizontalAlignment(JTextField.CENTER);
        viewTransactionsOptionsPanel.add(viewTransactionsAccountName);

        viewTransactionsOptionsOutput = new Label("");
        viewTransactionsOptionsOutput.setAlignment(Label.CENTER);
        viewTransactionsOptionsPanel.add(viewTransactionsOptionsOutput);

        JButton viewTransactionsButton = new JButton("View Transactions");
        viewTransactionsButton.addActionListener(e -> pressedViewTransactions());
        viewTransactionsOptionsPanel.add(viewTransactionsButton);

        TitledBorder titleViewTransactions;
        titleViewTransactions = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "View Transaction Options");
        titleViewTransactions.setTitleJustification(TitledBorder.LEFT);
        viewTransactionsOptionsPanel.setBorder(titleViewTransactions);
        return viewTransactionsOptionsPanel;
    }

    // EFFECTS: Shows transactions for the given account name
    public void pressedViewTransactions() {
        String accountName = viewTransactionsAccountName.getText();
        Account acc;
        try {
            acc = user.findAccountFromString(accountName);
        } catch (AccountNotFoundException e) {
            setBackgroundDefaultTimer(viewTransactionsOptionsOutput,"                 ",
                    "Account Not Found",Color.red);
            return;
        }
        setBackgroundDefaultTimer(viewTransactionsOptionsOutput,"             ",
                "Account Found",Color.green);
        transactionsList = extractTransactions(acc);
        transactionsListColumns = new String[] {
                "Transaction ID",
                "Credit",
                "Debit",
                "Amount",
                "Title",
                "Description"
        };
        DefaultTableModel newDataModel = new DefaultTableModel(transactionsList,transactionsListColumns);
        tableTransactions.setModel(newDataModel);
    }

    // EFFECTS: Converts the transactions into required format for displaying in the table
    private String[][] extractTransactions(Account acc) {
        int columns = 7;
        int rows = acc.getTransaction().size();
        String[][] outputString = new String[rows][columns];
        for (int row = 0; row < rows; row++) {
            outputString[row][0] = String.valueOf(acc.getTransaction(row).getTransactionID());
            outputString[row][1] = acc.getTransaction(row).getFrom().getAccountName();
            outputString[row][2] = acc.getTransaction(row).getTo().getAccountName();
            outputString[row][3] = String.valueOf(acc.getTransaction(row).getAmount());
            outputString[row][4] = acc.getTransaction(row).getTitle();
            outputString[row][5] = acc.getTransaction(row).getDesc();
        }
        return outputString;
    }
}