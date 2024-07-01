package org.example;

import java.util.StringTokenizer;

public class CostOperations {
    public boolean budgetCheck(int budget, int expense, int cost) {
        if(budget < (expense + cost)) {
            return true;
        } else return false;
    }

    public int getExpense(String temp) {
        StringTokenizer st = new StringTokenizer(temp, " ");
        int[] a = {0, 0};
        int i = 0;
        while(st.hasMoreTokens()) {
            a[i] = Integer.parseInt(st.nextToken());
            i++;
        }
        return a[1];
    }

    public int getBudget(String temp) {
        StringTokenizer st = new StringTokenizer(temp, " ");
        int[] a = {0, 0};
        int i = 0;
        while(st.hasMoreTokens()) {
            a[i] = Integer.parseInt(st.nextToken());
            i++;
        }
        return a[0];
    }
}
