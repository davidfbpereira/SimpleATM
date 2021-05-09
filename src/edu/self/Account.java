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

    public void printAccountTransactionHistory() {
        String summaryLine;
        if (this.transactions.size() == 0) {
            System.out.printf("\n\tAccount %s has no transaction history yet.", this.UUID);
        } else {
            System.out.printf("\n\tTransaction history for account %s:", this.UUID);

            for (int i = this.transactions.size() - 1; i >= 0; i--) {
                summaryLine = this.transactions.get(i).getSummaryLine();
                System.out.println(summaryLine);
            }
        }
        System.out.println();
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

    public String getUUID() {
        return this.UUID;
    }
}
