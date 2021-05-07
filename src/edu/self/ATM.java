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
            System.out.printf("\tWelcome %s, please select one of the following actions:\n", currentUser.getFirstName());
//            ATM.printUserMenu(currentUser);
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
}
