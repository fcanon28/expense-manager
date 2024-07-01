package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Manager {
    FileOperations file = new FileOperations();
    CostOperations cost = new CostOperations();
    Password pass = new Password();
    Scanner input = new Scanner(System.in);
    public void makeDailyLog() throws IOException {
        Log log = new Log();

        System.out.println("Enter date (mm/dd/yy): ");
        log.setDate(input.next());

        System.out.println("Enter item: ");
        log.setItem(input.next());

        System.out.println("Enter cost: ");
        log.setCost(input.nextInt());

        System.out.println("Successfully logged: " +log.getDate() +" "+ log.getItem()+ " " + log.getCost());
        enterLog(log);
    }

    public void enterLog(Log log) throws IOException {
        int budget = 0, expense = 0;
        String budgetBookData = file.getBudgetData(log.getDate());
        if(budgetBookData == null) {
            System.out.println("\nThe budget has not been set yet. Please set the budget: ");
            System.out.println("\nThe budget for the month is _ PHP ");
            budget = input.nextInt();
            file.updateLogBook(budget, 0, log.getDate(), "nil", 0);
        }
        else {
            budget = cost.getBudget(budgetBookData);
            expense = cost.getExpense(budgetBookData);
        }
        System.out.println("\nLog added successfully!");
        if(cost.budgetCheck(budget, expense, log.getCost())) {
            System.out.println("\nYour monthly budget has exceeded. Please review your finances.");
        }
        file.updateLogBook(budget, (expense + log.getCost()), log.getDate(), log.getItem(), log.getCost());
    }

    public void getDayExpenseDetails() throws IOException {
        System.out.println("\nEnter date (mm/dd/yy): ");
        String date = input.next();
        getDetails(date);
    }

    private void getDetails(String date) throws IOException {
        ArrayList<Log> list1 = file.getLog(date);
        int n = list1.size();
        if(n == 0) {
            System.out.println("\nNo entry has been made yet for this date. Please enter another date.");
        } else {
            System.out.println("\nExpenses for " + date + ": ");
            for(int i=0; i<n; i++) {
                Log log = list1.get(i);
                System.out.println((i+1) + " : " + "Item: " + log.getItem() + "\tCost: "+ log.getCost());
            }
        }
    }

    public void getMonthExpenseDetails() throws IOException, NumberFormatException {
        System.out.println("\nEnter the month and year (MM/YY)");
        String date = input.next();
        ArrayList<Log> list1 = file.getMonthLog(date);
        int n = list1.size();
        if(n == 0) {
            System.out.println("\nNo entry has been made for the date entered.");
        }else {
            System.out.println("\nExpenses for "+date+":");
            for(int i = 1; i < n; i++) {
                Log log = list1.get(i);
                System.out.println((i) + " : " + "\nDate: " + log.getDate() + " : " + "\nItem: " + log.getItem() + "\nExpenditure: PHP " + log.getCost());
            }
        }
    }

    public void displayMonthExpense() throws IOException{
        System.out.println("Enter the month and year (MM/YY)");
        String date = input.next();
        String budgetData = file.getBudgetData(date);
        int expense = cost.getExpense(budgetData);
        int budget = cost.getBudget(budgetData);
        System.out.println("\nExpenses incurred on date is PHP " + expense);
        if (budget > expense) {
            System.out.println("\nRemaining spendable amount is PHP " + (budget - expense));
        } else {
            System.out.println("\nBudget exceeded by PHP " + (expense - budget));
        }
    }

    public void setBudget() throws IOException {
        if (pass.checkPassword()) {
            System.out.println("\nResetting the budget may create errors in the calculation\nDo you still want to continue? (y/n)");
            char ch = input.next().charAt(0);
            if(ch == 'y' || ch == 'Y') {
                System.out.println("\nEnter the month and year (MM/YY)");
                String date = input.next();
                System.out.println("\nEnter new budget: PHP ");
                int budget = input.nextInt();
                file.resetBudget(date, budget);
                System.out.println("\nBudget has been successfully changed!");
            }
            ch = 'n';
        } else {
            System.out.println("The password is incorrect!");
        }

    }

    public void deleteMonthLog() throws IOException {
        System.out.println("Are you sure you want to delete a log? There won't be a way to recover (y/n)");
        char choice = input.next().charAt(0);
        if(choice == 'y' || choice == 'Y') {
            System.out.println("Enter the month and year (mm/yy)");
            String date = input.next();
            file.deleteLog(date);
        }
    }



}
