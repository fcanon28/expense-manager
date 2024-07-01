package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FileOperations {
    File logbookFile;
    File password = new File("src/main/java/org/example/password.txt");
    Scanner input = new Scanner(System.in);


    public ArrayList<Log> getLog(String date) throws IOException {
        logbookFile = new File("logbook" + getMonthYearName(date) + ".txt");
        FileReader read = new FileReader(logbookFile);
        BufferedReader rd = new BufferedReader(read);
        String temp = "";

        ArrayList<Log> list1 = new ArrayList<Log>();
        while((temp = rd.readLine()) != null) {
            Log log = new Log();
            StringTokenizer st = new StringTokenizer(temp, "\t");
            String[] str = new String[5];
            int i = 0;
            while (st.hasMoreTokens()) {
                str[i] = st.nextToken();
                i++;
            }

            if(str[2].equals(date)) {
                log.setDate(str[2]);
                log.setItem(str[3]);
                log.setCost(Integer.parseInt(str[4]));
                list1.add(log);
            }
        }
        rd.close();
        return list1;
    }

    public String getBudgetData(String date) throws IOException {
        logbookFile = new File("logbook" + getMonthYearName(date) + ".txt");
        if (!logbookFile.exists()) {
            logbookFile.createNewFile();
        }
        FileReader read = new FileReader(logbookFile);
        BufferedReader rd = new BufferedReader(read);
        String budgetData;
        String line, last = null;
        if (rd.readLine() == null) {
            budgetData = null;
        }
        else {
            while((line = rd.readLine()) != null) {
                last = line;
            }
            String[] arr = new String[5];
            int i = 0;
            assert last != null;
            StringTokenizer st = new StringTokenizer(last, "\t");
            while (st.hasMoreTokens()) {
                arr[i] = st.nextToken();
                i++;
            }
            budgetData = arr[0].concat(" " + arr[1]);
        }
        rd.close();
        return budgetData;
    }

    private String getMonthYearName(String date) throws IOException {
        String name = "";
//        if(date.length() == 7) {
//            date = "00/".concat(date);
//        }

        StringTokenizer st = new StringTokenizer(date, "/");
        int i = 0;
        String[] arr = new String[3];
        while(st.hasMoreTokens()) {
            arr[i] = st.nextToken();
            i++;
        } if(date.length() == 5) {
            name = arr[0].concat(arr[1]);
        }
        else if(date.length() == 10) {
            name = arr[1].concat(arr[2]);
        }
        else if(date.length() == 8) {
            name = arr[0].concat(arr[2]);
        }

        return name;
    }

    public void updateLogBook(int budget, int expense, String date, String item, int cost) throws IOException {
        logbookFile = new File("logbook" + getMonthYearName(date) + ".txt");
        FileWriter writer = new FileWriter(logbookFile, true);
        BufferedWriter br = new BufferedWriter(writer);
        if(item == "nil") {
            date = "nil";
        }
        br.append(String.valueOf(budget)).append("\t").append(String.valueOf(expense)).append("\t").append(date).append("\t").append(item).append("\t").append(String.valueOf(cost));
        br.newLine();
        br.close();
    }

    public ArrayList<Log> getMonthLog(String date) throws IOException {
        logbookFile = new File("logbook" + getMonthYearName(date) + ".txt");
        FileReader read = new FileReader(logbookFile);
        BufferedReader rd = new BufferedReader(read);
        String temp = "";
        ArrayList<Log> list1 = new ArrayList<Log>();

        while ((temp = rd.readLine()) != null) {
            Log log = new Log();
            StringTokenizer st = new StringTokenizer(temp, "\t");
            String[] str = new String[5];
            int i = 0;
            while (st.hasMoreTokens()) {
                str[i] = st.nextToken();
                i++;
            }
            log.setDate(str[2]);
            log.setItem(str[3]);
            log.setCost(Integer.parseInt(str[4]));
            list1.add(log);
        }
        rd.close();
        return list1;
    }

    public void resetBudget(String date, int budget) throws IOException {
        String last = "";
        String line = "";
        logbookFile = new File("logbook" + getMonthYearName(date) + ".txt");
        FileReader read =new FileReader(logbookFile);
        BufferedReader rd = new BufferedReader(read);

        while ((line = rd.readLine()) != null) {
            last = line;
        }
        StringTokenizer st = new StringTokenizer(last, "\t");
        int i = 0;
        String[] arr = new String[5];
        while(st.hasMoreTokens()) {
            arr[i] = st.nextToken();
            i++;
        }
        rd.close();

        RandomAccessFile f = new RandomAccessFile("logbook" + getMonthYearName(date) + ".txt", "rw");
        long length = f.length() - 1;
        byte b;
        do {
            length--;
            f.seek(length);
            b = f.readByte();
        } while (b != 10 && length > 0);
        if(length == 0) {
                f.setLength(length);
        } else {
                f.setLength(length + 1);
        }
        f.close();
        updateLogBook(budget, Integer.parseInt(arr[1]), arr[2], arr[3], Integer.parseInt(arr[4]));
    }

    public void deleteLog(String date) throws IOException {
        logbookFile = new File("logbook" + getMonthYearName(date) + ".txt");
        boolean bool = true;

        if(logbookFile.exists()) {
            bool = logbookFile.delete();
        }

        if (bool) {
            System.out.println("\nFile successfully deleted!");
        } else {
            System.out.println("\nFile does not exist!");
        }
    }


    public String getPassword() throws IOException {
        if(password.length() == 0) {
            System.out.println("\nPlease check if password.txt file is present!");
        }
        FileReader read = new FileReader(this.password);
        BufferedReader rd = new BufferedReader(read);

        String pass = rd.readLine().trim();
        rd.close();
        return pass;
    }

    public void setPassword(String password) throws IOException {
        RandomAccessFile f = new RandomAccessFile("password.txt", "rw");
        long length = f.length();

        if (length == 0) {
            // If the file is empty, directly write the new password
            f.close();
            try (BufferedWriter br = new BufferedWriter(new FileWriter("password.txt"))) {
                br.write(password);
            }
            System.out.println("Password successfully changed!");
            return;
        }

        length--; // Start from the last byte
        byte b;

        do {
            f.seek(length);
            b = f.readByte();
            length--;
        } while (b != 10 && length >= 0);

        if (length < 0) {
            f.setLength(0); // No newline found, truncate the whole file
        } else {
            f.setLength(length + 2); // Truncate the file at the last newline (length + 2 because length was decremented one extra time in the last loop iteration)
        }

        f.close();

        // Writing new password
        try (BufferedWriter br = new BufferedWriter(new FileWriter("password.txt", true))) {
            br.write(password);
        }
        System.out.println("Password successfully changed!");
    }


}
