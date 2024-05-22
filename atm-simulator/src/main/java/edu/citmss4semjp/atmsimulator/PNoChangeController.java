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
