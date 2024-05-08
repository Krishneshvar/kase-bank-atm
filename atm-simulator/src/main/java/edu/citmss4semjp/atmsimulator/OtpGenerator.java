package edu.citmss4semjp.atmsimulator;

public class OtpGenerator {

    public static void main(String[] args) {
        int n = 2; // Specify the value of n for 2^n digits
        if (n >= 0) {
            int numberOfDigits = (int) Math.pow(2, n); // Calculate 2^n

            String otp = "";

            // LCG parameters
            long seed = System.currentTimeMillis();
            long a = 1664525L;
            long c = 1013904223L;
            long m = (long) Math.pow(2, 32);

            long randomNumber = seed;

            // Generate each digit of the random number 2^n times
            for (int i = 0; i < numberOfDigits; i++) {
                randomNumber = (a * randomNumber + c) % m;
                int randomDigit = (int) (randomNumber % 10);
                otp = otp + randomDigit;
            }

            // Print the generated random number
            System.out.println("Welcome!\nYour one-time password is  " + otp+". \nPlease do not share this code. ");
        }
    }
}
