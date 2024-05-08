package edu.citmss4semjp.atmsimulator;

import java.util.Scanner;

public class Withdrawal {
    long bal;
    long w_amt;

    Withdrawal(long amt, long w_amt) {
        this.bal = amt;
        this.w_amt = w_amt;
    }

    void withdraw() {
        if (w_amt > bal)
            System.out.println("\nINSUFFICIENT FUNDS.");
        else {
            bal = bal - w_amt;
            System.out.println("\nWITHDRAWAL DONE");
            System.out.println("\nBalance: " + bal);
        }
    }

    public static void main(String args[]) {
        long avl_bal = 60000; // Available balance - This must be fetched from DB
        long w_amt;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter withdrawal amount: ");
        w_amt = sc.nextLong();
        sc.close();
        if(w_amt <= 10000)
        {
            Withdrawal wd = new Withdrawal(avl_bal, w_amt);
            wd.withdraw(); // Corrected this line to use the instance method
        }
        else {
            System.out.print("\nTRY WITH LESSER AMOUNT");
        }
    }
}
