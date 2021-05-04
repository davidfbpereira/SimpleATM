package edu.self;

public class ATM {
    public static void main(String[] arg) {
        Bank dummyBank = ATM.populateBank("Bank of Lisbon");
    }

    public static Bank populateBank(String bankName) {
        Bank aBank = new Bank(bankName);

        User aUser = new User("Dévis", "Pereirinha", "1234", aBank);

        return aBank;
    }
}
