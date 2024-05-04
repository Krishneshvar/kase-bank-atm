package edu.citmss4semjp.atmsimulator;

import java.util.Scanner;

public class Deposit {
    private double balance;

    public Deposit(double availableBalance) {
        balance = availableBalance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: Rs." + amount+ " Successfully!");
    }

    public double getBalance() {
        return balance;
    }

    public static void main(String[] args) {
        double availableBalance = 8000.0; 
        
        Deposit account = new Deposit(availableBalance);
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter amount to deposit: Rs.");
        double depositAmount = scanner.nextDouble();
        
        account.deposit(depositAmount);
        
        System.out.println("New balance: Rs." + account.getBalance());
        
        scanner.close();
    }
}