package edu.self;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private String firstName;
    private String lastName;
    private String UUID;
    private byte[] secretPinHash;
    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String secretPin, Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.secretPinHash = theBank.generatePinHash(secretPin);
        this.UUID = theBank.generateUUID().substring(0,8);
        this.accounts = new ArrayList<Account>();

        addAccount("Checking Account", theBank);

        System.out.printf("New user %s, %s with UUID %s was created.\n", this.lastName, this.firstName, this.UUID);
    }

    public void addAccount(String name, Bank theBank) {
        Account newAccount = new Account(name, theBank);
        this.accounts.add(newAccount);
    }

    public void printAccountsSummary() {
        for(Account account : this.accounts) {
            System.out.printf("\t\t%s\n", account.getSummaryLine());
        }
        System.out.println();
    }

    public String getUUID() {
        return this.UUID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }

    public byte[] getSecretPinHash() {
        return this.secretPinHash;
    }

    public Account getInputAccount() {
        Scanner input = new Scanner(System.in);
        String accountUUID;
        Account account = null;

        do {
            System.out.println("Enter account number to transfer from: ");
            accountUUID = input.next();

            account = getAccount(accountUUID);

            if (account == null) {
                System.out.println("Invalid user account. Please try again.");
            }
        } while (account == null);

        return account;
    }

    public Account getAccount(String theAccountUUID) {
        for (Account account : accounts) {
            if (account.getUUID().equals(theAccountUUID)) {
                return account;
            }
        }
        return null;
    }

    public double getAmountToTransfer(double balance) {
        Scanner input = new Scanner(System.in);
        double amount;

        do {
            System.out.printf("Enter the amount to transfer (max $%.02f)): $", balance);
            amount = input.nextDouble();

            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > balance) {
                System.out.printf("Amount must not be greater than balance of $%.02f.\n", balance);
            }
        } while (amount < 0 || amount > balance);

        return amount;
    }

    public double getAmountToDeposit() {
        Scanner input = new Scanner(System.in);
        double amount;

        do {
            System.out.printf("Enter the amount to transfer): $");
            amount = input.nextDouble();

            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            }
        } while (amount < 0);

        return amount;
    }
}
