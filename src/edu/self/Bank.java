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
        Scanner input = new Scanner(System.in);
        String fromAccountUUID, toAccountUUID;
        Account fromAccount, toAccount;
        double amount, balance;

        do {
            System.out.println("Enter account number to transfer from: ");
            fromAccountUUID = input.next();

            fromAccount = theUser.getAccount(fromAccountUUID);

            if (fromAccount == null) {
                System.out.println("Invalid user account. Please try again.");
            }
        } while (fromAccount == null);

        do {
            System.out.println("Enter account number to transfer to: ");
            toAccountUUID = input.next();

            toAccount = theUser.getAccount(toAccountUUID);

            if (toAccount == null) {
                System.out.println("Invalid user account. Please try again.");
            }
        } while (toAccount == null);

        balance = fromAccount.getAccountBalance();

        do {
            System.out.printf("Enter the amount to transfer (max $%.02f)): $", balance);
            amount = input.nextDouble();

            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > balance) {
                System.out.printf("Amount must not be greater than balance of $%.02f.\n", balance);
            }
        } while (amount < 0 || amount > balance);

        fromAccount.addTransaction(-1 * amount, String.format("Transfer to account %s", toAccountUUID));
        toAccount.addTransaction(amount, String.format("Transfer from account %s", fromAccountUUID));

        System.out.println("Transfer was successful!");
    }

    public String getName() {
        return this.name;
    }
}
