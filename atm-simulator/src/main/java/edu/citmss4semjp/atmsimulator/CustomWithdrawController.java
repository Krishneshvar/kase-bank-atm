package edu.citmss4semjp.atmsimulator;

// JavaFX imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// SQL imports
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomWithdrawController {

    @FXML
    private TextField withdrawAmt;

    @FXML
    void handleCustomWithdraw(ActionEvent event) throws SQLException {
        int amount = getCustomAmount();
        if (0 < amount && amount < 25000) {
            boolean withdrawalSuccessful = debitAmountFromAccount(amount);
            if (withdrawalSuccessful) {
                    String custID = DatabaseConnection.getCustID();
                    String accNo = DatabaseConnection.getAccountNumberFromCurrentSession();
                    TransactionHist.saveTransaction(custID, accNo, amount, "Withdrawal", "");

                    DepositController.navigateToThankYouScene(event);
                    ShowAlerts.showInfoAlert("Withdrawal Successful", "Amount withdrawn successfully.");
                    DatabaseConnection.truncateCurrentSession();
            }
            else {
                ShowAlerts.showErrorAlert("Withdraw Failed", "Failed to withdraw amount from account.");
            }
        }
        else {
            ShowAlerts.showErrorAlert("Invalid Amount", "The specified amount is not within the range of allowed withdrawal amount.");
        }
    }

    private int getCustomAmount() {
        try {
            String customAmountText = withdrawAmt.getText().trim();
            if (!customAmountText.isEmpty()) {
                return Integer.parseInt(customAmountText);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean debitAmountFromAccount(int amount) {
        String accountNumber = DatabaseConnection.getAccountNumberFromCurrentSession();
        if (accountNumber == null) {
            return false;
        }
        else {
            try {
                Connection connection = DatabaseConnection.getConnection();
                String sql = "UPDATE acc_details SET balance = balance - ? WHERE acc_no = ?";

                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, amount);
                statement.setString(2, accountNumber);

                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            }
            catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @FXML
    void goHome(ActionEvent event) throws IOException {
        DatabaseConnection.truncateCurrentSession();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.setFullScreen(true);
        currentStage.show();
    }
}
