package edu.self;

import java.util.ArrayList;

public class Bank {
    private String name;
    private ArrayList<User> users;

    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
    }
}
