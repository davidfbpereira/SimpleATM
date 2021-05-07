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

        System.out.printf("New user %s, %s with UUID %s was created.\n", this.lastName, this.firstName, this.UUID);
    }

    public boolean validatePin(String aPin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.secretPinHash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String getUUID() {
        return this.UUID;
    }

    public String getFirstName() {
        return this.firstName;
    }
}
