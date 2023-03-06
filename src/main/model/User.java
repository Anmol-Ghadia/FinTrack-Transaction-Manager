package model;

import exceptions.AccountNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

/*
    Represents a user, should be instantiated only once. contains different transactions and accounts
    that a user may need
*/
public class User {
    private ArrayList<Account> accumulator;
    private ArrayList<Account> expense;
    private ArrayList<Account> income;
    private ArrayList<Account> loan;
    private ArrayList<Transaction> transactionList;

    // EFFECTS: Creates a new user and initializes fields
    public User() {
        transactionList = new ArrayList<>();
        accumulator = new ArrayList<>();
        expense = new ArrayList<>();
        loan = new ArrayList<>();
        income = new ArrayList<>();
    }

    // REQUIRES: Json Object is of correct user specification
    // EFFECTS: Creates a new user as specified by the Json Object supplied
    public User(JSONObject jsonObject) {
        this.accumulator = jsonToAccountWithoutTransactions(jsonObject.getJSONArray("acc-names"),"acc");
        this.income = jsonToAccountWithoutTransactions(jsonObject.getJSONArray("income-names"),"income");
        this.expense = jsonToAccountWithoutTransactions(jsonObject.getJSONArray("expense-names"),"expense");
        this.loan = jsonToAccountWithoutTransactions(jsonObject.getJSONArray("loan-names"),"loan");

        this.accumulator = addTransactionsToAccounts(this.accumulator,jsonObject.getJSONArray("acc"));
        this.income = addTransactionsToAccounts(this.income, jsonObject.getJSONArray("income"));
        this.expense = addTransactionsToAccounts(this.expense, jsonObject.getJSONArray("expense"));
        this.loan = addTransactionsToAccounts(this.loan, jsonObject.getJSONArray("loan"));

        this.transactionList = jsonToTransactionList(jsonObject.getJSONArray("transaction"));
    }

    // EFFECTS: returns the accumulator account ArrayList
    public ArrayList<Account> getAccumulator() {
        return accumulator;
    }

    // EFFECTS: returns the expense account ArrayList
    public ArrayList<Account> getExpense() {
        return expense;
    }

    // EFFECTS: returns the income account ArrayList
    public ArrayList<Account> getIncome() {
        return income;
    }

    // EFFECTS: returns the loan account ArrayList
    public ArrayList<Account> getLoan() {
        return loan;
    }

    // EFFECTS: returns the transaction list
    public ArrayList<Transaction> getTransactionList() {
        return transactionList;
    }

    // MODIFIES: this
    // EFFECTS: adds accumulator if not already in list
    public void addAccumulator(Account acc) {
        if (!accumulator.contains(acc) && acc.getAccountType().equals("ACCUMULATOR")) {
            accumulator.add(acc);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds expense if not already in list
    public void addExpense(Account acc) {
        if (!expense.contains(acc) && acc.getAccountType().equals("EXPENSE")) {
            expense.add(acc);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds income if not already in list
    public void addIncome(Account acc) {
        if (!income.contains(acc) && acc.getAccountType().equals("INCOME")) {
            income.add(acc);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds loan if not already in list
    public void addLoan(Account acc) {
        if (!loan.contains(acc) && acc.getAccountType().equals("LOAN")) {
            loan.add(acc);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds transaction to the user's transaction list
    public void addTransaction(Transaction t) {
        transactionList.add(t);
    }

    // MODIFIES: this
    // EFFECTS: adds transaction to the user's transaction list, and also in appropriate accounts
    public void addTransactionComplete(Transaction t) {
        t.getFrom().addTransaction(t);
        t.getTo().addTransaction(t);
        transactionList.add(t);
    }

    // MODIFIES: this
    // EFFECTS: returns true if accumulator account is removed from user
    public void removeAccumulator(Account acc) {
        accumulator.remove(acc);
    }

    // MODIFIES: this
    // EFFECTS: returns true if income account is removed from user
    public void removeIncome(Account acc) {
        income.remove(acc);
    }

    // MODIFIES: this
    // EFFECTS: returns true if expense account is removed from user
    public void removeExpense(Account acc) {
        expense.remove(acc);
    }

    // MODIFIES: this
    // EFFECTS: returns true if loan account is removed from user
    public void removeLoan(Account acc) {
        loan.remove(acc);
    }

    // MODIFIES: this
    // EFFECTS: returns true if transaction is removed from user
    public boolean removeTransaction(Transaction t) {
        return transactionList.remove(t);
    }

    // EFFECTS: finds an account with the given name and returns it, else throws AccountNotFoundException
    public Account findAccountFromString(String accountName) throws AccountNotFoundException {
        for (Account acc: getAccumulator()) {
            if (acc.getAccountName().equalsIgnoreCase(accountName)) {
                return acc;
            }
        }
        for (Account acc: getExpense()) {
            if (acc.getAccountName().equalsIgnoreCase(accountName)) {
                return acc;
            }
        }
        for (Account acc: getIncome()) {
            if (acc.getAccountName().equalsIgnoreCase(accountName)) {
                return acc;
            }
        }
        for (Account acc: getLoan()) {
            if (acc.getAccountName().equalsIgnoreCase(accountName)) {
                return acc;
            }
        }
        throw new AccountNotFoundException();
    }

    // REQUIRES: Json object is of correct transaction structure
    // EFFECTS: adds JSON transactions to appropriate accounts
    private ArrayList<Account> addTransactionsToAccounts(ArrayList<Account> accountNames, JSONArray accounts) {
        ArrayList<Account> finalAccountList = new ArrayList<>();
        for (int i = 0; i < accounts.length(); i++) {
            finalAccountList.add(jsonToAccount(accountNames.get(i),accounts.getJSONObject(i)));
        }
        return finalAccountList;
    }

    // EFFECTS: returns a list of account with names as given by json array
    private ArrayList<Account> jsonToAccountWithoutTransactions(JSONArray jsonArray, String type) {
        ArrayList<Account> accList = new ArrayList<>();
        String accName;
        for (int i = 0; i < jsonArray.length(); i++) {
            accName = jsonArray.getString(i);
            accList.add(jsonToSingleAccountWithoutTransactions(accName,type));
        }
        return  accList;
    }

    // EFFECTS: returns a new account with given name and type
    private Account jsonToSingleAccountWithoutTransactions(String accName, String type) {
        Account out = null;
        switch (type) {
            case "acc":
                out = new Accumulator(accName);
                break;
            case "income":
                out = new Income(accName);
                break;
            case "expense":
                out = new Expense(accName);
                break;
            default:
                out = new Loan(accName);
                break;
        }
        return out;
    }

    // MODIFIES: account
    // EFFECTS: Sets tha accounts description and transactions for given account based on the json object
    private Account jsonToAccount(Account account, JSONObject jsonObject) {
        account.setDesc(jsonObject.getString("desc"));
        account.setTransactions(jsonToTransactionList(jsonObject.getJSONArray("transactions")));
        return account;
    }

    // REQUIRES: Json Array is of correct specification
    // EFFECTS: Returns the transaction list from provided jsonArray
    private ArrayList<Transaction> jsonToTransactionList(JSONArray transactions) {
        ArrayList<Transaction> transactionList = new ArrayList<>();
        JSONObject transactionObject;
        for (int i = 0; i < transactions.length(); i++) {
            transactionObject = transactions.getJSONObject(i);
            int id = transactionObject.getInt("id");
            Account from = findAccountFromString(transactionObject.getString("from"));
            Account to = findAccountFromString(transactionObject.getString("to"));
            int amount = transactionObject.getInt("amount");
            LocalDate date = LocalDate.parse(transactionObject.getString("date"));
            String title = transactionObject.getString("title");
            String desc = transactionObject.getString("desc");
            Transaction t = new Transaction(id,from,to,amount,date,title,desc);
            transactionList.add(t);
        }
        return transactionList;
    }


    //EFFECTS: returns the user data as a string in JSON format
    public String toJsonString() {
        JSONObject jsonString = new JSONObject();

        jsonString.put("acc-names", accountNamesToJson(accumulator));
        jsonString.put("income-names", accountNamesToJson(income));
        jsonString.put("expense-names", accountNamesToJson(expense));
        jsonString.put("loan-names", accountNamesToJson(loan));

        jsonString.put("acc", accountToJson(accumulator));
        jsonString.put("income", accountToJson(income));
        jsonString.put("expense", accountToJson(expense));
        jsonString.put("loan", accountToJson(loan));

        jsonString.put("transaction", transactionToJson());

        return jsonString.toString(4);
    }

    // EFFECTS: Returns a json array with account names
    private JSONArray accountNamesToJson(ArrayList<Account> accList) {
        JSONArray nameJsonArray = new JSONArray();
        for (Account acc: accList) {
            nameJsonArray.put(acc.accountName);
        }
        return nameJsonArray;
    }

    // EFFECTS: returns a json array with accounts as json objects
    private JSONArray accountToJson(ArrayList<Account> accList) {
        JSONArray accJsonArray = new JSONArray();
        for (Account acc: accList) {
            accJsonArray.put(acc.toJson());
        }
        return accJsonArray;
    }

    // EFFECTS: returns all the transactions as a JSON Array
    private JSONArray transactionToJson() {
        JSONArray transactionJsonArray = new JSONArray();
        for (Transaction t: transactionList) {
            transactionJsonArray.put(t.toJson());
        }
        return transactionJsonArray;
    }
}
