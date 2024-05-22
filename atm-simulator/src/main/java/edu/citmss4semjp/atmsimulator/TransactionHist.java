/**
 *  This file contains a generic function to update the transaction history of an ATM session
 */

package edu.citmss4semjp.atmsimulator;

// SQL imports
import java.sql.*;
import java.util.Date;

public class TransactionHist {

    public static void saveTransaction(String customerId, String accountNumber, double amount, String type, String recipientAccountNumber) {
        Timestamp transac_time = new Timestamp(new Date().getTime());
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO transac_hist VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customerId);
            statement.setString(2, accountNumber);
            statement.setString(3, type);
            statement.setTimestamp(4, transac_time);
            statement.setDouble(5, amount);
            statement.setString(6, recipientAccountNumber);

            statement.executeUpdate();
            System.out.println("Transaction saved successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
