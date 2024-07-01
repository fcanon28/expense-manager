package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        Password pass = new Password();
        Manager manage = new Manager();
        int ch;
        char userChoice = 'n';

        if (pass.checkPassword()) {
            System.out.println("Successfully logged in!");
            do {
                System.out.println("""
                        What do you wanna do today?\s
                        1 - Make an entry\s
                        2 - Get expenses for a particular date\s
                        3 - Get expenses for a particular month\s
                        4 - Check total expense for a particular month\s
                        5 - Set budget for the month\s
                        6 - Delete log for the month\s
                        7 - Change my password""");
                ch = input.nextInt();
                switch (ch) {
                    case 1:
                        manage.makeDailyLog();
                        break;
                    case 2:
                        manage.getDayExpenseDetails();
                        break;
                    case 3:
                        manage.getMonthExpenseDetails();
                        break;
                    case 4:
                        manage.displayMonthExpense();
                        break;
                    case 5:
                        manage.setBudget();
                        break;
                    case 6:
                        manage.deleteMonthLog();
                        break;
                    case 7:
                        pass.changePassword();
                }

                System.out.println("Anything else? (Y/N): ");
                userChoice = input.next().charAt(0);
            } while(userChoice == 'y' || userChoice == 'Y');
        }

    }
}