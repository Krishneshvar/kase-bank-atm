package edu.citmss4semjp.atmsimulator;

// JavaFX imports
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

// SQL imports
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WithdrawController {

    @FXML
    private Button customWithdrawBtn;

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
            handleWithdraw(1000, event);
        });

        fivekBtn.setOnAction(event -> {
            handleWithdraw(5000, event);
        });

        tenkBtn.setOnAction(event -> {
            handleWithdraw(10000, event);
        });

        twentyfivekBtn.setOnAction(event -> {
            handleWithdraw(25000, event);
        });

        customWithdrawBtn.setOnAction(event -> {
            navigateToCustWithdrawScene(event);
        });
    }

    void handleWithdraw(int amount, ActionEvent event) {
        boolean withdrawalSuccessful = debitAmountFromAccount(amount);

        if (withdrawalSuccessful) {
            try {
                ShowAlerts.showInfoAlert("Withdrawal Successful", "Amount withdrawn successfully.");
                DepositController.navigateToThankYouScene(event);

                String custID = DatabaseConnection.getCustID();
                String accNo = DatabaseConnection.getAccountNumberFromCurrentSession();
                TransactionHist.saveTransaction(custID, accNo, amount, "Withdrawal",  "");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            DatabaseConnection.truncateCurrentSession();

        } else {
            ShowAlerts.showErrorAlert("Withdraw Failed", "Failed to withdraw amount from account.");
        }
    }

    private boolean debitAmountFromAccount(int amount) {
        String accountNumber = DatabaseConnection.getAccountNumberFromCurrentSession();
        if (accountNumber == null) {
            return false; // No account number found in the current session
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

    private void navigateToCustWithdrawScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustWithdraw.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.setFullScreen(true);
            currentStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
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
