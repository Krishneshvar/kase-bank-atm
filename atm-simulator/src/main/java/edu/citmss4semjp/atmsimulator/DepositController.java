package edu.citmss4semjp.atmsimulator;

// JavaFX imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// SQL imports
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DepositController {

    @FXML
    private TextField depositAmt;

    @FXML
    private GridPane numpad;

    @FXML
    private PasswordField pinTxtfield;

    @FXML
    private Button clear;

    @FXML
    private TextField accTxtfield;

    private NumPadController numPadController;

    @FXML
    private void handleDeposit(ActionEvent event) throws SQLException {
        String accountNumber = DatabaseConnection.getAccountNumberFromCurrentSession();
        double amount;

        if (accountNumber == null) {
            ShowAlerts.showErrorAlert("Account Error", "No Account Number found in the current session.");
        }
        else {
            amount = Double.parseDouble(depositAmt.getText());

            if (depositAmount(accountNumber, amount)) {
                ShowAlerts.showInfoAlert("Deposit Successful", "Amount successfully deposited.");

                String custID = DatabaseConnection.getCustID();
                String accNo = DatabaseConnection.getAccountNumberFromCurrentSession();
                TransactionHist.saveTransaction(custID, accNo, amount, "Deposit", "");

                DatabaseConnection.truncateCurrentSession();
                navigateToThankYouScene(event);
            }
            else {
                ShowAlerts.showErrorAlert("Deposit Failed", "Failed to deposit specified amount.");
            }
        }
    }

    private boolean depositAmount(String accountNumber, double amount) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "UPDATE acc_details SET balance = balance + ? WHERE acc_no = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, amount);
            statement.setString(2, accountNumber);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void navigateToThankYouScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(DepositController.class.getResource("ThankYouScene.fxml"));
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
