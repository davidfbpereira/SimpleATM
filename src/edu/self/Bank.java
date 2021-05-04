package edu.self;

import java.util.ArrayList;
import java.util.UUID;

public class Bank {
    private String name;
    private ArrayList<User> users;

    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
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
}
