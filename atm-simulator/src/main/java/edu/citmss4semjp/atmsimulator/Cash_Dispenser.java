/**
 *  This file is for maintaining the cash dispenser and keeping count of the available notes.
 */

package edu.citmss4semjp.atmsimulator;

import java.util.Scanner;

class Cash_Dispenser {
    static int a, b, c;
    int input, h_notes, th_notes, fh_notes;

    Cash_Dispenser(int inp) {
        input = inp;
        h_notes = 100;
        th_notes = 100;
        fh_notes= 100;
    }

    void dispfh()
    {
        int n = input;
        fh_notes = n/500;
        if(n%500 == 0 && fh_notes <= a)
        {
            a = a - fh_notes;
            th_notes = 0;
            h_notes = 0;
            receipt();
        }

        else if(n%500 != 0) {
            System.out.println("DENOMINATIONS DOESN'T MATCH THE AMOUNT ENTERED, TRY MULTIPLES OF 500");
        }
        else {
            System.out.println("INSUFFICIENT NOTES, TRY ANOTHER DENOMINATIONS ");
        }
    }
    
    void dispth()
    {
        int n = input;
        th_notes = n/200;
        if(n%200 == 0 && th_notes <= b)
        {
            b = b - th_notes;
            fh_notes = 0;
            h_notes = 0;
            receipt();
        }
        else if(n%200 != 0) {
            System.out.println("DENOMINATIONS DOESN'T MATCH THE AMOUNT ENTERED, TRY MULTIPLES OF 200");
        }
        else {
            System.out.println("INSUFFICIENT NOTES, TRY ANOTHER DENOMINATIONS");
        }
    }

    void disph()
    {
        int n = input;
        h_notes = n/200;
        if(n%100 == 0 && h_notes <= b)
        {
            c = c - h_notes;
            fh_notes = 0;
            th_notes = 0;
            receipt();
        }
        else if(n%100 != 0) {
            System.out.println("DENOMINATIONS DOESN'T MATCH THE AMOUNT ENTERED, TRY MULTIPLES OF 100");
        }
        else {
            System.out.println("INSUFFICIENT NOTES, TRY ANOTHER DENOMINATIONS ");
        }
    }

    void mixed()
    {
        int n = input;
        while(n != 0) {
            if(n>500)
            {
            fh_notes = (n / 500)-1;
            n = n - (fh_notes * 500);
            th_notes = (n / 200);
            n = n - (th_notes*200);
            h_notes = n/100;
            n = n - (h_notes*100);

            if (fh_notes <= a && th_notes <= b && h_notes <= c) {
                a = a - fh_notes;
                b = b - th_notes;
                c = c - 1;
                n = 0;
                receipt();
            }

            else if (fh_notes > a) {
                if (input <= 5000 && input > 2000) {
                    fh_notes = 0;
                    th_notes = (n / 200) - 5;
                    h_notes = 10;
                    if (th_notes >= b && h_notes >= c) {
                        b = b - th_notes;
                        n = n - (th_notes * 200);
                        c = c - 10;
                        receipt();
                    } else if (th_notes > b) {
                        th_notes = (n / 200);
                        b = b - th_notes;
                        n = n - (th_notes * 200);
                        h_notes = 0;
                        receipt();
                    } else {
                        System.out.println("TRY AGAIN WITH LESSER AMOUNT");
                    }
                } else if (input <= 2000) {
                    th_notes = 0;
                    h_notes = input / 100;
                    if (h_notes >= c) {
                        c = c - h_notes;
                        n = n - (h_notes * 100);
                        receipt();
                    } else {
                        System.out.println("TRY AGAIN WITH LESSER AMOUNT");
                    }
                } else {
                    System.out.println("TRY AGAIN WITH LESSER AMOUNT");
                }
            }
            }
            else
            {

            }
        }
    }

    void receipt()
    {
        System.out.println("Dispensed:\n500: " + fh_notes + "\n200: " + th_notes + "\n100: " + h_notes);
        System.out.println("balance:\n500: " + a + "\n200: " + b + "\n100: " + c);
    }

    public static void main(String[] args) {
        //Cash_Dispense d = new Cash_Dispense();
        int amt,dnm;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the amount: ");
        amt = sc.nextInt();
        if(amt <= 10000)
        {
            System.out.print("\nChoose: \n1 for 500\n2 for 200\n3 for 100\n4 for mixed\nINPUT: ");
            dnm = sc.nextInt();
            sc.close();
            switch (dnm)
            {
                case 1:
                    Cash_Dispenser d1 = new Cash_Dispenser(amt);
                    d1.dispfh();
                    break;
                case 2:
                    Cash_Dispenser d2 = new Cash_Dispenser(amt);
                    d2.dispth();
                    break;
                case 3:
                    Cash_Dispenser d3 = new Cash_Dispenser(amt);
                    d3.disph();
                    break;
                case 4:
                    Cash_Dispenser d4 = new Cash_Dispenser(amt);
                    if((amt % 500) == 0) {d4.mixed();}
                    else
                    {
                        amt=((amt/500) * 500);
                        int amt2 = amt - ((amt/500) * 500);
                        Cash_Dispenser d5 = new Cash_Dispenser(amt);
                        d5.mixed();
                        Cash_Dispenser d6 = new Cash_Dispenser(amt2);
                        d6.mixed();
                    }
                    break;
            }
        }
        else {
            System.out.print("ENTER AMOUNT LESS THAN 10000.");
        }
    }
}
