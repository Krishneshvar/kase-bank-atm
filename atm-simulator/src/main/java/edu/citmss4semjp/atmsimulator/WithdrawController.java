package edu.citmss4semjp.atmsimulator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WithdrawController {

    @FXML
    private Button customWithdrawBtn;

    @FXML
    private Button fiftykBtn;

    @FXML
    private Button fivekBtn;

    @FXML
    private Button onekBtn;

    @FXML
    private Button tenkBtn;

    @FXML
    private Button twentyfivekBtn;

    @FXML
    private void initialize() {
        onekBtn.setOnAction(event -> {
            handleWithdraw(1000);
        });

        // Add similar event handlers for other buttons as needed
    }

    private void handleWithdraw(int amount) {
        boolean withdrawalSuccessful = debitAmountFromAccount(amount);

        if (withdrawalSuccessful) {
            navigateToThankYouScene();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Withdrawal Failed");
            alert.setHeaderText(null);
            alert.setContentText("Failed to withdraw amount from account.");

            alert.showAndWait();
        }
    }


    private boolean debitAmountFromAccount(int amount) {
        // Database connection and debit operation logic
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kasebankdatabase", "postgres", "kichapostgresequel");
            String sql = "UPDATE acc_details SET balance = balance - ? WHERE acc_no = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, amount);
            statement.setString(2, "");

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void navigateToThankYouScene() {
        // Code to navigate to ThankYou.fxml
    }
}