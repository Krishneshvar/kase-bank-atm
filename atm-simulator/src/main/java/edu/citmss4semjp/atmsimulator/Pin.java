package edu.citmss4semjp.atmsimulator;

import java.util.Scanner;

public class Pin {
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter PIN: ");
        char[] passwordChars = System.console().readPassword();
        String pin = new String(passwordChars);
        sc.close();
        System.out.println("Your pin is: " + pin);
    }
}



