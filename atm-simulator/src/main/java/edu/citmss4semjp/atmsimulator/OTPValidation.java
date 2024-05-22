package edu.citmss4semjp.atmsimulator;

// JavaFX imports
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

// SQL imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class OTPValidation {

    static String getOTPFromCurrentSession() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "SELECT otp FROM current_atmuser LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("otp");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    static boolean validateOTP(TextField txtFld) {
        String storedOTP = getOTPFromCurrentSession();
        String enteredOTP = txtFld.getText();

        if (Objects.equals(storedOTP, enteredOTP)) {
            ShowAlerts.showInfoAlert("Validation Successful", "OTP validated successfully. Transaction successful.");
            return true;
        }
        else {
            ShowAlerts.showInfoAlert("Validation Error", "Error validating OTP. Transaction failed.");
            return false;
        }
    }
}
