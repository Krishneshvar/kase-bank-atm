package edu.citmss4semjp.atmsimulator;

import java.sql.*;

public class AccountValidator {
    public static void main(String[] args) {
        String accountNumber = "71762231019123";
        String pin = "1403";
        String validationMessage = validateAccount(accountNumber, pin);
        System.out.println(validationMessage);
        DatabaseConnection.closeConnection(); // Close the database connection
    }

    public static String validateAccount(String accountNumber, String pin) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String validationMessage = "Invalid account number or PIN";
        try {
            String sql = "SELECT * FROM acc_details WHERE acc_no = ? AND pin = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, pin);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                validationMessage = "Successfully validated";
            }
        } catch (SQLException se) {
            validationMessage = "Error validating account: " + se.getMessage();
        }         return validationMessage;
    }
}
