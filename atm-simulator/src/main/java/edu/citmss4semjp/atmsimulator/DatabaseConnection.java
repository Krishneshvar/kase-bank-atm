/**
 * @author: Avanthika PG
 * @date: 13/04/2024
 * 
 * This file connects the PostgreSQL  database of  Kasebank to the java program.
 */

package edu.citmss4semjp.atmsimulator;

import java.sql.Connection;  // This interface represents the connection of database.
import java.sql.DriverManager;  // For managing a set of JDBC drivers
import java.sql.SQLException;  // Used to handle exceptions that may occur during database operations using JDBC.

// URL specifies the address and parameters needed to connect the database
public class DatabaseConnection {
    static String URL = "jdbc:postgresql://localhost:5432/kasebankdatabase";
    static String USERNAME = "postgres";
    static String PASSWORD = "kichapostgresequel";

    private static Connection connection;  // database connection object

    DatabaseConnection() {}  // Constructor

    /**
     * Method to get database connection
     * 
     * @returns connection 
     */
    public static Connection getConnection() {
      // If connection is not established,establishes new connection
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                // If conncetion fails,prints an error message.
                System.out.println("Connection to PostgreSQL failed. Check JDBC URL, username, and password.");
            }
        }
        return connection;
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
            // If an error occurs while closing prints an eroor message.
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
