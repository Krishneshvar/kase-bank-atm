package edu.citmss4semjp.atmsimulator;

import java.util.Scanner;

public class Pin {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String pin="";
        while (pin.length() != 4) {
            System.out.print("ENTER PIN: ");
            char[] passwordChars = System.console().readPassword();
            pin = new String(passwordChars);
        }
        sc.close();
        
        if(pin==pin)//from db
        {
            System.out.print("CORRECT PIN");
        }
        else {
            System.out.print("INCORRECT PIN");
        }
    }
}
