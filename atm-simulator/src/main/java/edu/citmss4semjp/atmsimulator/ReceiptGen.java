package edu.citmss4semjp.atmsimulator;

import java.text.SimpleDateFormat;
import java.util.Date;

// SQL imports
import java.sql.SQLException;
import java.util.Objects;

public class ReceiptGen {

    private static final String ATM_ID = "KB0A000001";
    private static final String BANK_NAME = "KASE BANK";
    private static final String BANK_CODE = "KB";

    // Prime number used for calculations
    private static final int PRIME_NUMBER = 47;

    // Method to mask the account number except for the last 4 digits
    private static String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) {
            return "XXXX XXXX XXXX XXXX"; // Return a default masked number if invalid
        }
        return "XXXX XXXX XXXX " + accountNumber.substring(accountNumber.length() - 4);
    }

    // Convert a numeric string to a long manually, avoiding parse methods
    private static long numericStringToLong(String numericString) {
        if (numericString == null) {
            throw new IllegalArgumentException("Numeric string cannot be null");
        }
        long result = 0;
        for (int i = 0; i < numericString.length(); i++) {
            char ch = numericString.charAt(i);
            if (ch >= '0' && ch <= '9') {
                result = result * 10 + (ch - '0');
            }
        }
        return result;
    }

    // Method to generate an 8-digit receipt number
    public static String generateReceiptNumber(String accountNumber) {
        if (accountNumber == null) {
            throw new IllegalArgumentException("Account number cannot be null");
        }

        // Get the current timestamp as a string in the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());

        // Convert the account number and timestamp manually to numerical values
        long numericAccount = numericStringToLong(accountNumber);
        long numericTimestamp = numericStringToLong(timestamp);

        // Combine timestamp and account number, then perform a prime multiplication
        long primeResult = (numericAccount + numericTimestamp) * PRIME_NUMBER;

        // Reduce the prime result to 8-digits
        long receiptNumber = primeResult % 100000000;

        // If the result is negative, add 100 million to bring it to a positive range
        if (receiptNumber < 0) {
            receiptNumber += 100000000;
        }

        // Return the formatted receipt number with the bank code
        return String.format("%s%08d", BANK_CODE, receiptNumber);
    }

    public static String printATMReceiptGen(String transac) throws SQLException {
        String accNo = DatabaseConnection.getAccountNumberFromCurrentSession();
        double balance = Double.parseDouble(Objects.requireNonNull(BalanceController.chkBalance()));
        if (accNo == null) {
            System.err.println("Error: Account number is null.");
            return null;
        }

        // Generate an 8-digit receipt number
        String receiptNumber = generateReceiptNumber(accNo);

        // Get current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(new Date());

        // Mask the account number
        String maskedAccountNumber = maskAccountNumber(accNo);

        return "***********************************\n" +
                "             " + BANK_NAME + "\n            ATM Receipt" +
                "\n***********************************\n" +
                "Date          : " + currentDate +
                "ATM ID        : " + ATM_ID +
                "Receipt Number: " + receiptNumber +
                "Account Number: " + maskedAccountNumber +
                "\nTransaction      : " + transac +
                "Available Balance: ₹" + String.format("%.2f", balance) +
                "\n***********************************\n" +
                "Thank you for choosing KASE Bank!" +
                "\n***********************************";
    }
}
