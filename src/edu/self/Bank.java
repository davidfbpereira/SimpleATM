package edu.self;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Bank {
    private String name;
    private ArrayList<User> users;

    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
    }

    public User addUser(String firstName, String lastName, String pin) {
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        return newUser;
    }

    public void addUserAccount(User theUser, String name) {
        theUser.addAccount(name, this);
    }

    public byte[] generatePinHash(String theSecretPin) {
        byte[] aSecretPinHash = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            aSecretPinHash = messageDigest.digest(theSecretPin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return aSecretPinHash;
    }

    public boolean validatePin(String thePin, byte[] userSecretPinHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(thePin.getBytes()), userSecretPinHash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String generateUUID() {
        Boolean unique = false;
        UUID uuid;

        do {
            uuid = UUID.randomUUID();
            for (User user : users) {
                if (user.getUUID().equals(uuid.toString())) {
                    unique = true;
                    break;
                }
            }
        } while (unique);

        return uuid.toString();
    }

    public User userLogin(String userUUID, String pin) {
        for (User user : this.users) {
            if (user.getUUID().compareTo(userUUID) == 0) {
                if (validatePin(pin, user.getSecretPinHash())) {
                    return user;
                }
            }
        }
        return null;
    }

    public static void showTransactionHistory(User theUser) {
        Scanner input = new Scanner(System.in);
        String accountUUID;
        ArrayList<Account> userAccounts;

        do {
            System.out.print("\n\tEnter your account UUID: ");
            accountUUID = input.next();

            userAccounts = theUser.getAccounts();
            for (Account account : userAccounts) {
                if (account.getUUID().compareTo(accountUUID) == 0) {
                    account.printAccountTransactionHistory();
                    return;
                }
            }
            System.out.println("\n\tInvalid account index. Please try again.");
        } while (true);
    }

    public static void transferFunds(User theUser) {
        Account fromAccount, toAccount;
        double amount, balance;

        fromAccount = theUser.getInputAccount();
        toAccount = theUser.getInputAccount();

        balance = fromAccount.getAccountBalance();

        amount = theUser.getAmountToTransfer(balance);

        fromAccount.addTransaction(-1 * amount, String.format("Transfer to account %s", toAccount.getUUID()));
        toAccount.addTransaction(amount, String.format("Transfer from account %s", fromAccount.getUUID()));

        System.out.println("Transfer was successful!");
    }

    public static void withdrawFunds(User theUser) {
        Account fromAccount;
        double amount, balance;

        fromAccount = theUser.getInputAccount();

        balance = fromAccount.getAccountBalance();

        amount = theUser.getAmountToTransfer(balance);

        fromAccount.addTransaction(-1 * amount, String.format("Withdraw from %s", fromAccount.getUUID()));

        System.out.println("Transfer was successful!");
    }

    public static void depositFunds(User theUser) {
        Account toAccount;
        double amount, balance;

        toAccount = theUser.getInputAccount();

        balance = toAccount.getAccountBalance();

        amount = theUser.getAmountToTransfer(balance);

        toAccount.addTransaction(amount, String.format("Deposit to %s", toAccount.getUUID()));

        System.out.println("Transfer was successful!");
    }

    public String getName() {
        return this.name;
    }
}
