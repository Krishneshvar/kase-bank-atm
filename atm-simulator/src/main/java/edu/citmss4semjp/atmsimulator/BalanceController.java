package edu.citmss4semjp.atmsimulator;

// JavaFX imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

// SQL imports
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class BalanceController {

    @FXML
    private Label accBalLbl;

    @FXML
    private Label accNoLbl;

    // Method to check balance
    static String chkBalance() throws SQLException {
        String accNo = DatabaseConnection.getAccountNumberFromCurrentSession();
        Connection connection = DatabaseConnection.getConnection();

        String sql = "SELECT balance FROM acc_details WHERE acc_no = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, accNo);

        ResultSet res = statement.executeQuery();

        if (res.next()) {
            return res.getString("balance");
        }
        return null;
    }

    // Method to display balance
    @FXML
    void dispBal() throws SQLException {
        String accNo = DatabaseConnection.getAccountNumberFromCurrentSession();
        String bal = chkBalance();

        accNoLbl.setText(accNo);
        accBalLbl.setText(bal);
    }

    @FXML
    void gotoThankYou(ActionEvent event) {
        DepositController.navigateToThankYouScene(event);
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
