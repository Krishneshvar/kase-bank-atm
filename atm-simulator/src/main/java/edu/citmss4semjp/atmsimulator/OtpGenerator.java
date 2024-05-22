package edu.citmss4semjp.atmsimulator;

import java.sql.*;

public class OtpGenerator {

    public static String genOTP() throws SQLException {
        int n = 2;
        String otp = "";

        int numberOfDigits = (int) Math.pow(2, n); // Calculate 2^n

        long seed = System.currentTimeMillis();
        long a = 1664525L;
        long c = 1013904223L;
        long m = (long) Math.pow(2, 32);

        long randomNumber = seed;

        for (int i = 0; i < numberOfDigits; i++) {
            randomNumber = (a * randomNumber + c) % m;
            int randomDigit = (int) (randomNumber % 10);
            otp = otp + randomDigit;
        }
        String currUserAccNo = DatabaseConnection.getAccountNumberFromCurrentSession();
        updateCurrUserOTP(otp, currUserAccNo);
        return otp;
    }

    public static void updateCurrUserOTP(String Otp, String currAccNo) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "UPDATE current_atmuser SET otp = ? WHERE acc_no = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, Otp);
        statement.setString(2, currAccNo);
    }
}
