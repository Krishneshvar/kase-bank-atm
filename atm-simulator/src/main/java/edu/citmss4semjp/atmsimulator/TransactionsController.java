package edu.citmss4semjp.atmsimulator;

// JavFX imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

// SQL imports
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class TransactionsController {

    @FXML
    private Button balBtn;

    @FXML
    private Button depositBtn;

    @FXML
    private Button ftBtn;

    @FXML
    private Button pinchangeBtn;

    @FXML
    private Button pnochangeBtn;

    @FXML
    private Button withdrawBtn;

    @FXML
    private Button cancel;

    @FXML
    private void startBalanceInqScene() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("Balance.fxml")));
        Parent detailroot = loader.load();
        BalanceController balanceController = loader.getController();
        balanceController.dispBal();

        Scene scene = new Scene(detailroot);

        Stage stage = (Stage) balBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void startDepositScene() throws IOException {
        Parent detailroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Deposit.fxml")));
        Scene scene = new Scene(detailroot);

        Stage stage = (Stage) depositBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void startFTScene() throws IOException {
        Parent detailroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FundsTransfer.fxml")));
        Scene scene = new Scene(detailroot);

        Stage stage = (Stage) ftBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void startWithdrawScene() throws IOException {
        Parent detailroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Withdraw.fxml")));
        Scene scene = new Scene(detailroot);

        Stage stage = (Stage) withdrawBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void startPNoChangeScene() throws IOException, SQLException {
        Parent detailroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PhNoChange.fxml")));
        Scene scene = new Scene(detailroot);

        Stage stage = (Stage) pnochangeBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();

        String userEmail = getUserEmail();
        MailGenerator.sendMail(userEmail);
    }

    @FXML
    private void startPinChangeScene() throws IOException, SQLException {
        Parent detailroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PinChange.fxml")));
        Scene scene = new Scene(detailroot);

        Stage stage = (Stage) pinchangeBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();

        String userEmail = getUserEmail();
        MailGenerator.sendMail(userEmail);
    }

    private String getUserEmail() {
        try {
            String custID = DatabaseConnection.getCustID();
            Connection connection = DatabaseConnection.getConnection();

            String sql = "SELECT email_id FROM customers WHERE cust_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, custID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("email_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
