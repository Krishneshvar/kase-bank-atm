/**
 *  This file validates the ATM's current user's account number and pin.
 */

package edu.citmss4semjp.atmsimulator;

// SQL imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountValidator {

    public static String validateAccount(String accountNumber, String pin) {
        String validationMessage = "Invalid account number or PIN.";

        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM acc_details WHERE acc_no = ? AND pin = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, pin);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                validationMessage = "Successfully validated.";
            }
        }
        catch (SQLException se) {
            validationMessage = "Error validating account: " + se;
        }

        return validationMessage;
    }
}
