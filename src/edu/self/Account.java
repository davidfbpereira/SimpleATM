package edu.self;

import java.util.ArrayList;

public class Account {
    private String name;
    private String UUID;
    private ArrayList<Transaction> transactions;

    public Account(String name, Bank theBank) {
        this.name = name;
        this.UUID = theBank.generateUUID();
        this.transactions = new ArrayList<Transaction>();
    }

    public String getSummaryLine() {
        double balance = this.getAccountBalance();

        if (balance >= 0) {
            return String.format("%s : %s : $%.02f", this.UUID, this.name, balance);
        } else {
            return String.format("%s : %s : $(%.02f)", this.UUID, this.name, balance);
        }
    }

    public double getAccountBalance() {
        double balance = 0;
        for (Transaction transaction : this.transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }
}
