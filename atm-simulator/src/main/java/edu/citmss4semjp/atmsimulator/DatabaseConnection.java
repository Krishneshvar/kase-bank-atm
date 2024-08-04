/**
 * This file connects the PostgreSQL database of Kase Bank to the java program.
 */

package edu.citmss4semjp.atmsimulator;

// SQL imports
import java.sql.*;

public class DatabaseConnection {
    static String URL = "JDBC_LINK";
    static String USERNAME = "DB_USERNAME";
    static String PASSWORD = "DB_PASSWORD";

    private static Connection connection;  // database connection object

    DatabaseConnection() {}  // Default constructor

    /**
     * Method to get database connection
     * @returns connection 
     */
    public static Connection getConnection() {
      // If connection is not established, establishes new connection
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                // If connection fails, prints an error message.
                System.out.println("\nConnection to PostgreSQL failed. Check JDBC URL, username, and password.\n" + e);
            }
        }
        return connection;
    }

    static String getAccountNumberFromCurrentSession() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "SELECT acc_no FROM current_atmuser LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("acc_no");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void truncateCurrentSession() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "TRUNCATE TABLE current_atmuser";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static String getCustID() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String accNo = DatabaseConnection.getAccountNumberFromCurrentSession();

        String sql = "SELECT cust_id FROM acc_details WHERE acc_no = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, accNo);

        ResultSet res = statement.executeQuery();

        if (res.next()) {
            return res.getString("cust_id");
        }
        return null;
    }

    static int getAmtFromDB() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String accNo = DatabaseConnection.getAccountNumberFromCurrentSession();

            String sql = "SELECT balance FROM acc_details WHERE acc_no = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, accNo);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("balance");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Method to close database connection
    public static void closeConnection() {
        try {
            // If connection is not null and not closed, closes the connection
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            // If an error occurs while closing prints an error message.
            System.out.println("\nError closing connection: " + e);
        }
    }
}
