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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class PNoChangeController {

    @FXML
    private TextField currPnoTxtField;

    @FXML
    private TextField newPnoTxtField;

    @FXML
    private TextField otpField;

    public int tf = 0;

    boolean checkCurrPNo() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String custID = DatabaseConnection.getCustID();
        String currPno = currPnoTxtField.getText();

        String sql = "SELECT ph_no FROM customers WHERE cust_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, custID);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String dbPno = rs.getString("ph_no");
            return Objects.equals(currPno, dbPno);
        }
        else {
            return false;
        }
    }

    @FXML
    public void pnoChange(ActionEvent event) throws SQLException {
        if (tf == 0 || tf == 1) {
            tf++;
        }
        if (tf == 2) {
            if (checkCurrPNo()) {
                if (!Objects.equals(currPnoTxtField.getText(), newPnoTxtField.getText())) {
                    if (OTPValidation.validateOTP(otpField)) {
                        Connection connection = DatabaseConnection.getConnection();
                        String custID = DatabaseConnection.getCustID();
                        String newPNo = newPnoTxtField.getText();

                        String sql = "UPDATE customers SET ph_no = ? WHERE cust_id = ?";
                        PreparedStatement pstmt = connection.prepareStatement(sql);
                        pstmt.setString(1, newPNo);
                        pstmt.setString(2, custID);
                        pstmt.executeUpdate();

                        String accNo = DatabaseConnection.getAccountNumberFromCurrentSession();
                        TransactionHist.saveTransaction(custID, accNo, 0, "Phone Number Change", "");

                        DatabaseConnection.truncateCurrentSession();
                        DepositController.navigateToThankYouScene(event);
                    }
                    else {
                        ShowAlerts.showErrorAlert("Invalid OTP", "Entered OTP is invalid.");
                    }
                }
                else {
                    ShowAlerts.showErrorAlert("Phone Number Error", "Current phone number and new phone number are similar.");
                }
            }
            else {
                ShowAlerts.showErrorAlert("Phone Number Error", "Current phone number is invalid.");
            }
        }
    }

    @FXML
    void one() {
        if (tf == 0) {
            currPnoTxtField.appendText("1");
        }
        if (tf == 1) {
            newPnoTxtField.appendText("1");
        }
        if (tf == 2) {
            otpField.appendText("1");
        }
    }
    @FXML
    void two() {
        if (tf == 0) {
            currPnoTxtField.appendText("2");
        }
        if (tf == 1) {
            newPnoTxtField.appendText("2");
        }
        if (tf == 2) {
            otpField.appendText("2");
        }
    }
    @FXML
    void three() {
        if (tf == 0) {
            currPnoTxtField.appendText("3");
        }
        if (tf == 1) {
            newPnoTxtField.appendText("3");
        }
        if (tf == 2) {
            otpField.appendText("3");
        }
    }
    @FXML
    void four() {
        if (tf == 0) {
            currPnoTxtField.appendText("4");
        }
        if (tf == 1) {
            newPnoTxtField.appendText("4");
        }
        if (tf == 2) {
            otpField.appendText("4");
        }
    }
    @FXML
    void five() {
        if (tf == 0) {
            currPnoTxtField.appendText("5");
        }
        if (tf == 1) {
            newPnoTxtField.appendText("5");
        }
        if (tf == 2) {
            otpField.appendText("5");
        }
    }
    @FXML
    void six() {
        if (tf == 0) {
            currPnoTxtField.appendText("6");
        }
        if (tf == 1) {
            newPnoTxtField.appendText("6");
        }
        if (tf == 2) {
            otpField.appendText("6");
        }
    }
    @FXML
    void seven() {
        if (tf == 0) {
            currPnoTxtField.appendText("7");
        }
        if (tf == 1) {
            newPnoTxtField.appendText("7");
        }
        if (tf == 2) {
            otpField.appendText("7");
        }
    }
    @FXML
    void eight() {
        if (tf == 0) {
            currPnoTxtField.appendText("8");
        }
        if (tf == 1) {
            newPnoTxtField.appendText("8");
        }
        if (tf == 2) {
            otpField.appendText("8");
        }
    }
    @FXML
    void nine() {
        if (tf == 0) {
            currPnoTxtField.appendText("9");
        }
        if (tf == 1) {
            newPnoTxtField.appendText("9");
        }
        if (tf == 2) {
            otpField.appendText("9");
        }
    }
    @FXML
    void zero() {
        if (tf == 0) {
            currPnoTxtField.appendText("0");
        }
        if (tf == 1) {
            newPnoTxtField.appendText("0");
        }
        if (tf == 2) {
            otpField.appendText("0");
        }
    }

    @FXML
    void backspace() {
        if (tf == 0) {
            String currentText = currPnoTxtField.getText();
            if (!currentText.isEmpty()) {
                currPnoTxtField.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
        if (tf == 1) {
            String currentText = newPnoTxtField.getText();
            if (!currentText.isEmpty()) {
                newPnoTxtField.setText(currentText.substring(0, currentText.length() - 1));
            }
            if (currentText.isEmpty()) {
                tf = 0;
            }
        }
        if (tf == 2) {
            String currentText = otpField.getText();
            if (!currentText.isEmpty()) {
                otpField.setText(currentText.substring(0, currentText.length() - 1));
            }
            if (currentText.isEmpty()) {
                tf = 1;
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
