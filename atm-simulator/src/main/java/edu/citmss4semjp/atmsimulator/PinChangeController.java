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
    public PasswordField cNewPinField;

    @FXML
    public PasswordField newPinField;

    @FXML
    public PasswordField oldPinField;

    @FXML
    public TextField otpField;

    @FXML
    public Button pinSubmitBtn;

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
