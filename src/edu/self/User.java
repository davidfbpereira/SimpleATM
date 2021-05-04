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

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            this.secretPinHash = messageDigest.digest(secretPin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.UUID = theBank.generateUUID();
        this.accounts = new ArrayList<Account>();

        System.out.printf("New user %s, %s with UUID %s was created.\n", lastName, firstName, this.UUID);
    }

    public String getUUID() {
        return this.UUID;
    }
}
