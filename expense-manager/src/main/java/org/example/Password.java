package org.example;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.IOException;
import java.util.Scanner;

public class Password {
    FileOperations file = new FileOperations();
    Scanner input = new Scanner(System.in);
    public boolean checkPassword() throws IOException {
        System.out.println("Enter password: ");
        String pass = input.next();
        checkUntilPasswordIsCorrect(pass);
        return true;
    }

    private String getPassword() throws IOException {
        return file.getPassword();
    }

    private boolean checkUntilPasswordIsCorrect(String oldPass) throws IOException {
        while (!oldPass.equals(getPassword())) {
            System.out.println("Password incorrect! \n\nRe-enter password: ");
            oldPass = input.next();

            if(oldPass.equals(getPassword())) {
                break;
            }
        }
        return true;
    }

    public void changePassword() throws IOException {
        System.out.println("Enter old password: ");
        String oldPass = input.next();

        if(checkUntilPasswordIsCorrect(oldPass)) {
            System.out.println("Enter new password: ");
            String newPass = input.next();
            file.setPassword(newPass);
            System.out.println("Password has been changed successfully!");
        }
    }


}
