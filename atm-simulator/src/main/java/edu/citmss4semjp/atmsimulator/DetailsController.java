package edu.citmss4semjp.atmsimulator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class DetailsController {

    @FXML
    private TextField accTxtfield;

    @FXML
    private PasswordField pinTxtfield;

    @FXML
    private Button submitbtn;

    @FXML
    private Label validationMessageLbl;

    @FXML
    private BorderPane rootLayout;

    @FXML
    private void initialize() {
        submitbtn.setOnAction(event  -> {
            handleSubmission();
        });
    }

    @FXML
    private void handleSubmission() {
        String accountNumberStr = accTxtfield.getText();
        String pinStr = pinTxtfield.getText();
        try {
            String validationMessage = AccountValidator.validateAccount(accountNumberStr, pinStr);

            if (validationMessage.equals("Successfully validated")) {
                loadTransactions();
                updateCurrentUser(accountNumberStr);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Details");
                alert.setHeaderText(null);
                alert.setContentText("Invalid account number or PIN. Please try again.");

                // Add an event handler for the alert to redirect to home.fxml when the OK button is clicked
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            loadHome();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (NumberFormatException | IOException e) {
            validationMessageLbl.setText("Invalid input. Please enter valid numbers.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void loadTransactions() throws IOException {
        Parent scanroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Transactions.fxml")));
        Scene scene = new Scene(scanroot);

        Stage stage = (Stage) submitbtn.getScene().getWindow();
        stage.setScene(scene);

        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void loadHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));
        Scene scene = new Scene(root);

        Stage stage = (Stage) submitbtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    private void updateCurrentUser(String accNoStr) throws SQLException {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kasebankdatabase", "postgres", "kichapostgresequel");
            String sql = "INSERT INTO current_atmuser VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, accNoStr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
