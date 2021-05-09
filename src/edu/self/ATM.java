package edu.self;

import java.util.Collections;
import java.util.Scanner;

public class ATM {
    private static final int MAX_RETRIES = 3;

    public static void main(String[] arg) {
        Bank dummyBank = ATM.populateBank("Bank of Lisbon");

        ATM.printBanner(dummyBank, "*", 50);

        User currentUser = ATM.userLogin(dummyBank);

        if (currentUser == null) {
            System.out.println("\n\tYou reached the maximum number of retries.");
            System.out.println("\tATM is shutting down...");
            System.exit(1);
        } else {
            System.out.printf("\n\tWelcome back %s!\n", currentUser.getFirstName());
            ATM.printUserMenu(currentUser);
        }

    }

    public static Bank populateBank(String bankName) {
        Bank aBank = new Bank(bankName);
        aBank.addUser("Dévis", "NastyByNature", "1234");

        return aBank;
    }

    public static void printBanner(Bank theBank, String symbol, int reps) {
        System.out.println();
        System.out.println("\t" + String.join("", Collections.nCopies(reps, symbol)) + "\t\t");
        System.out.println(String.join("", Collections.nCopies(reps / 10, "\t")) + "ATM is booting...");
        System.out.println("\t" + String.join("", Collections.nCopies(reps, symbol)) + "\t\t");
        System.out.println();

        System.out.printf("\tWelcome to the %s!\n\n", theBank.getName());
    }

    public static User userLogin(Bank theBank) {
        String userID, pin;
        User authUser;
        int number_of_retries = 0;

        Scanner input = new Scanner(System.in);

        System.out.println("\tPlease enter your private information:");
        while(number_of_retries < 3) {
            System.out.print("\t\tuser ID: ");
            userID = input.nextLine();

            System.out.print("\t\tpin: ");
            pin = input.nextLine();

            authUser = theBank.userLogin(userID, pin);

            if (authUser != null) {
                return authUser;
            } else {
                number_of_retries++;
                if (number_of_retries == MAX_RETRIES) {
                    break;
                } else {
                    System.out.println("\n\tIncorrect user ID/pin combination.");
                    System.out.printf("\tYou still have %d retries.\n", MAX_RETRIES - number_of_retries);
                    System.out.println("\tPlease try again:");
                }
            }
        }
        return null;
    }

    public static void printUserMenu(User currentUser) {
        System.out.println("\n\tYour accounts summary:");

        currentUser.printAccountsSummary();

        System.out.println("\tPlease enter operation number:");

        Scanner input = new Scanner(System.in);
        int operation;
        do {
            System.out.println("\t\t1) Check account history");
            System.out.println("\t\t2) Withdraw");
            System.out.println("\t\t3) Deposit");
            System.out.println("\t\t4) Transfer");
            System.out.println("\t\t5) Quit");
            System.out.println();

            System.out.print("\t\tEnter operation number: ");
            operation = input.nextInt();

            if (operation < 1 || operation > 5) {
                System.out.println("\n\tInvalid action.");
                System.out.println("\tPlease try again:");
            }
        } while (operation < 1 || operation > 5);

        switch (operation) {
            case 1:
                Bank.showTransactionHistory(currentUser);
                break;
            case 2:
                //ATM.withdrawFunds(theUser);
                break;
            case 3:
                //ATM.depositFunds(theUser);
                break;
            case 4:
                //ATM.transferFunds(theUser);
                break;
            case 5:
                ATM.sayGoodBye();
                System.exit(1);
                break;
        }
        ATM.printUserMenu(currentUser);
    }

    public static void sayGoodBye() {
        System.out.println("\n\tThank you, come again!");
        System.out.println("\tATM is shutting down...");
    }
}
