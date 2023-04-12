package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/*
* A general Account model for more specific accounts
* (concrete subclasses of this abstract super class)
*        ( All tests related to this abstract class can be found in the AccumulatorTest which tests )
*        (     the Accumulator concrete class which is a subclass of this abstract class            )
*/
public abstract class Account {

    // Transactions are added from the front(index: 0)
    protected ArrayList<Transaction> transactions;
    protected String accountType;
    protected String accountName;
    protected String accountDesc;

    public ArrayList<Transaction> getTransaction() {
        return transactions;
    }

    // EFFECTS: Get transaction at specific index
    public Transaction getTransaction(int index) {
        return transactions.get(index);
    }

    // EFFECTS: get transaction by transaction id
    public Transaction getTransactionByID(int id) {
        for (Transaction t: transactions) {
            if (t.getTransactionID() == id) {
                return t;
            }
        }
        return null;
    }

    // EFFECTS: returns the last transaction, index 0
    public Transaction getLastTransaction() {
        // can return error if nothing exists in the array
        // can be implemented after C3
        return transactions.get(0);
    }

    // EFFECTS: return the outstanding balance of the account
    public int getBalance() {
        return getDebit() - getCredit();
    }

    // EFFECTS: return the total of inflows(debit) into the account
    public int getDebit() {
        int total = 0;
        for (Transaction t: transactions) {
            if (t.getTo().equals(this)) {
                total += t.getAmount();
            }
        }
        return total;
    }

    // EFFECTS: return the total of outflows(credit) from the account
    public int getCredit() {
        int total = 0;
        for (Transaction t: transactions) {
            if (t.getFrom().equals(this)) {
                total += t.getAmount();
            }
        }
        return total;
    }

    // MODIFIES: this
    // EFFECTS: Adds a new transaction to the account
    public void addTransaction(Transaction transaction) {
        transactions.add(0, transaction);
    }

    // EFFECTS: returns the type of account
    public String getAccountType() {
        return accountType;
    }

    // EFFECTS: returns the name of the account
    public String getAccountName() {
        return accountName;
    }

    // EFFECTS: returns the account description
    public String getAccountDesc() {
        return accountDesc;
    }

    // MODIFIES: this
    // EFFECTS: returns true if successfully removes the transaction, else false
    public boolean deleteTransaction(Transaction t) {
        return transactions.remove(t);
    }

    // MODIFIES: this
    // EFFECTS: changes the name of the account to newName
    public void setName(String newName) {
        EventLog.getInstance().logEvent(new Event("Changed (Y)Account name from: " + accountName
                + " to " + newName));
        accountName = newName.toUpperCase();
    }

    // MODIFIES: this
    // EFFECTS: changes the account description to desc
    public void setDesc(String desc) {
        EventLog.getInstance().logEvent(new Event("Changed (Y)Account description from: " + accountDesc
                + " to " + desc));
        this.accountDesc = desc;
    }

    // EFFECTS: returns the account as a JSON Object
    public JSONObject toJson() {
        JSONObject jsonAccount = new JSONObject();
        jsonAccount.put("name",accountName);
        jsonAccount.put("desc",accountDesc);
        jsonAccount.put("transactions",transactionToJsonArray());
        return jsonAccount;
    }

    // EFFECTS: returns all the transactions in a JSON Array
    private JSONArray transactionToJsonArray() {
        JSONArray jsonTransactionArray = new JSONArray();
        for (Transaction t: transactions) {
            jsonTransactionArray.put(t.toJson());
        }
        return jsonTransactionArray;
    }

    // MODIFIES: this
    // EFFECTS: Sets a new set of transactions, Old transactions are removed
    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
}
