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

    public void addUser(String firstName, String lastName, String pin) {
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);
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
            if (user.getUUID().compareTo(userUUID) == 0 && user.validatePin(pin)) {
                return user;
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

    public ArrayList<Account> getAccounts(User theUser) {
        return theUser.getAccounts();
    }

    public String getName() {
        return this.name;
    }
}
