package edu.self;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

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

    public Account getAccount(String theAccountUUID) {
        for (Account account : accounts) {
            if (account.getUUID().equals(theAccountUUID)) {
                return account;
            }
        }
        return null;
    }
}
