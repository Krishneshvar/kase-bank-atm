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
import javafx.stage.Stage;

// SQL imports
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class PinChangeController {

    @FXML
    public PasswordField oldPinField;

    @FXML
    public PasswordField newPinField;

    @FXML
    public PasswordField cNewPinField;

    @FXML
    public TextField otpField;

    @FXML
    public Button pinSubmitBtn;

    public int tf = 0;

    public boolean checkCurrPIN() {
        String accNo = DatabaseConnection.getAccountNumberFromCurrentSession();
        String oldPin = oldPinField.getText();
        String validation = AccountValidator.validateAccount(accNo, oldPin);

        return Objects.equals(validation, "Successfully validated.");
    }

    public boolean confirmNewPINs() {
        String newPin = newPinField.getText();
        String cNewPin = cNewPinField.getText();

        return Objects.equals(newPin, cNewPin);
    }

    @FXML
    public void pinChange(ActionEvent event) throws SQLException {
        if (tf == 0 || tf == 1 || tf == 2) {
            tf++;
        }
        if (tf == 3) {
            if (checkCurrPIN()) {
                if (confirmNewPINs()) {
                    if (!Objects.equals(oldPinField.getText(), newPinField.getText())) {
                        if (OTPValidation.validateOTP(otpField)) {
                            Connection connection = DatabaseConnection.getConnection();
                            String accNo = DatabaseConnection.getAccountNumberFromCurrentSession();
                            String newPin = newPinField.getText();

                            String sql = "UPDATE acc_details SET pin = ? WHERE acc_no = ?";
                            PreparedStatement pstmt = connection.prepareStatement(sql);
                            pstmt.setString(1, newPin);
                            pstmt.setString(2, accNo);
                            pstmt.executeUpdate();

                            String custID = DatabaseConnection.getCustID();
                            TransactionHist.saveTransaction(custID, accNo, 0, "Pin Change", "");

                            DatabaseConnection.truncateCurrentSession();
                            DepositController.navigateToThankYouScene(event);
                        }
                        else {
                            ShowAlerts.showErrorAlert("Invalid OTP", "Entered OTP is invalid.");
                        }
                    }
                    else {
                        ShowAlerts.showErrorAlert("PIN Error", "Current PIN and new PIN are similar.");
                    }
                }
                else {
                    ShowAlerts.showErrorAlert("PIN Error", "Entered new PINs are not similar.");
                }
            }
            else {
                ShowAlerts.showErrorAlert("PIN Error", "Current PIN is invalid.");
            }
        }
    }

    @FXML
    void one() {
        if (tf == 0) {
            oldPinField.appendText("1");
        }
        if (tf == 1) {
            newPinField.appendText("1");
        }
        if (tf == 2) {
            cNewPinField.appendText("1");
        }
        if (tf == 3) {
            otpField.appendText("1");
        }
    }
    @FXML
    void two() {
        if (tf == 0) {
            oldPinField.appendText("2");
        }
        if (tf == 1) {
            newPinField.appendText("2");
        }
        if (tf == 2) {
            cNewPinField.appendText("2");
        }
        if (tf == 3) {
            otpField.appendText("2");
        }
    }
    @FXML
    void three() {
        if (tf == 0) {
            oldPinField.appendText("3");
        }
        if (tf == 1) {
            newPinField.appendText("3");
        }
        if (tf == 2) {
            cNewPinField.appendText("3");
        }
        if (tf == 3) {
            otpField.appendText("3");
        }
    }
    @FXML
    void four() {
        if (tf == 0) {
            oldPinField.appendText("4");
        }
        if (tf == 1) {
            newPinField.appendText("4");
        }
        if (tf == 2) {
            cNewPinField.appendText("4");
        }
        if (tf == 3) {
            otpField.appendText("4");
        }
    }
    @FXML
    void five() {
        if (tf == 0) {
            oldPinField.appendText("5");
        }
        if (tf == 1) {
            newPinField.appendText("5");
        }
        if (tf == 2) {
            cNewPinField.appendText("5");
        }
        if (tf == 3) {
            otpField.appendText("5");
        }
    }
    @FXML
    void six() {
        if (tf == 0) {
            oldPinField.appendText("6");
        }
        if (tf == 1) {
            newPinField.appendText("6");
        }
        if (tf == 2) {
            cNewPinField.appendText("6");
        }
        if (tf == 3) {
            otpField.appendText("6");
        }
    }
    @FXML
    void seven() {
        if (tf == 0) {
            oldPinField.appendText("7");
        }
        if (tf == 1) {
            newPinField.appendText("7");
        }
        if (tf == 2) {
            cNewPinField.appendText("7");
        }
        if (tf == 3) {
            otpField.appendText("7");
        }
    }
    @FXML
    void eight() {
        if (tf == 0) {
            oldPinField.appendText("8");
        }
        if (tf == 1) {
            newPinField.appendText("8");
        }
        if (tf == 2) {
            cNewPinField.appendText("8");
        }
        if (tf == 3) {
            otpField.appendText("8");
        }
    }
    @FXML
    void nine() {
        if (tf == 0) {
            oldPinField.appendText("9");
        }
        if (tf == 1) {
            newPinField.appendText("9");
        }
        if (tf == 2) {
            cNewPinField.appendText("9");
        }
        if (tf == 3) {
            otpField.appendText("9");
        }
    }
    @FXML
    void zero() {
        if (tf == 0) {
            oldPinField.appendText("0");
        }
        if (tf == 1) {
            newPinField.appendText("0");
        }
        if (tf == 2) {
            cNewPinField.appendText("0");
        }
        if (tf == 3) {
            otpField.appendText("0");
        }
    }

    @FXML
    void backspace() {
        if (tf == 0) {
            String currentText = oldPinField.getText();
            if (!currentText.isEmpty()) {
                oldPinField.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
        if (tf == 1) {
            String currentText = newPinField.getText();
            if (!currentText.isEmpty()) {
                newPinField.setText(currentText.substring(0, currentText.length() - 1));
            }
            if (currentText.isEmpty()) {
                tf = 0;
            }
        }
        if (tf == 2) {
            String currentText = cNewPinField.getText();
            if (!currentText.isEmpty()) {
                cNewPinField.setText(currentText.substring(0, currentText.length() - 1));
            }
            if (currentText.isEmpty()) {
                tf = 1;
            }
        }
        if (tf == 3) {
            String currentText = otpField.getText();
            if (!currentText.isEmpty()) {
                otpField.setText(currentText.substring(0, currentText.length() - 1));
            }
            if (currentText.isEmpty()) {
                tf = 2;
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
