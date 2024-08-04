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

public class FTController {

    @FXML
    private TextField recAccNo;

    @FXML
    private TextField transAmt;

    public int tf = 0;

    @FXML
    private void handleFundTransfer(ActionEvent event) {
        if (tf == 0) {
            tf++;
        }
        if (tf == 1) {
            String senderAccountNumber = DatabaseConnection.getAccountNumberFromCurrentSession();
            String recipientAccountNumber = recAccNo.getText().trim();
            double amount;

            if (senderAccountNumber == null) {
                ShowAlerts.showErrorAlert("Account Missing", "No account found in the current session.");
            }
            else if (recipientAccountNumber.isEmpty()) {
                ShowAlerts.showErrorAlert("Recipient Account Number Missing", "Please enter the recipient account number. It shouldn't be empty.");
            }
            else {
                try {
                    amount = Double.parseDouble(transAmt.getText());

                    if (transferFunds(senderAccountNumber, recipientAccountNumber, amount)) {
                        ShowAlerts.showInfoAlert("Funds Transfer Successful", "Funds transferred to specified account successfully.");

                        String custID = DatabaseConnection.getCustID();
                        String accNo = DatabaseConnection.getAccountNumberFromCurrentSession();
                        TransactionHist.saveTransaction(custID, accNo, amount, "Fund Transfer", recipientAccountNumber);

                        //DatabaseConnection.truncateCurrentSession();
                        DepositController.navigateToThankYouScene(event);
                    }
                    else {
                        ShowAlerts.showErrorAlert("Fund Transfer Failed", "Failed to transfer funds.");
                    }
                }
                catch (NumberFormatException e) {
                    ShowAlerts.showErrorAlert("Invalid Amount", "Transfer amount is invalid. Please enter a valid amount.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean transferFunds(String senderAccountNumber, String recipientAccountNumber, double amount) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            String debitSql = "UPDATE acc_details SET balance = balance - ? WHERE acc_no = ?";
            PreparedStatement debitStatement = connection.prepareStatement(debitSql);
            debitStatement.setDouble(1, amount);
            debitStatement.setString(2, senderAccountNumber);
            int debitRowsUpdated = debitStatement.executeUpdate();

            String creditSql = "UPDATE acc_details SET balance = balance + ? WHERE acc_no = ?";
            PreparedStatement creditStatement = connection.prepareStatement(creditSql);
            creditStatement.setDouble(1, amount);
            creditStatement.setString(2, recipientAccountNumber);
            int creditRowsUpdated = creditStatement.executeUpdate();

            if (debitRowsUpdated > 0 && creditRowsUpdated > 0) {
                connection.commit();
                return true;
            }
            else {
                connection.rollback();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    void one() {
        if (tf == 0) {
            recAccNo.appendText("1");
        }
        if (tf == 1) {
            transAmt.appendText("1");
        }
    }
    @FXML
    void two() {
        if (tf == 0) {
            recAccNo.appendText("2");
        }
        if (tf == 1) {
            transAmt.appendText("2");
        }
    }
    @FXML
    void three() {
        if (tf == 0) {
            recAccNo.appendText("3");
        }
        if (tf == 1) {
            transAmt.appendText("3");
        }
    }
    @FXML
    void four() {
        if (tf == 0) {
            recAccNo.appendText("4");
        }
        if (tf == 1) {
            transAmt.appendText("4");
        }
    }
    @FXML
    void five() {
        if (tf == 0) {
            recAccNo.appendText("5");
        }
        if (tf == 1) {
            transAmt.appendText("5");
        }
    }
    @FXML
    void six() {
        if (tf == 0) {
            recAccNo.appendText("6");
        }
        if (tf == 1) {
            transAmt.appendText("6");
        }
    }
    @FXML
    void seven() {
        if (tf == 0) {
            recAccNo.appendText("7");
        }
        if (tf == 1) {
            transAmt.appendText("7");
        }
    }
    @FXML
    void eight() {
        if (tf == 0) {
            recAccNo.appendText("8");
        }
        if (tf == 1) {
            transAmt.appendText("8");
        }
    }
    @FXML
    void nine() {
        if (tf == 0) {
            recAccNo.appendText("9");
        }
        if (tf == 1) {
            transAmt.appendText("9");
        }
    }
    @FXML
    void zero() {
        if (tf == 0) {
            recAccNo.appendText("0");
        }
        if (tf == 1) {
            transAmt.appendText("0");
        }
    }

    @FXML
    void backspace() {
        if (tf == 0) {
            String currentText = recAccNo.getText();
            if (!currentText.isEmpty()) {
                recAccNo.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
        if (tf == 1) {
            String currentText = transAmt.getText();
            if (!currentText.isEmpty()) {
                transAmt.setText(currentText.substring(0, currentText.length() - 1));
            }
            if (currentText.isEmpty()) {
                tf = 0;
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
