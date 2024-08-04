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

public class DetailsController {

    @FXML
    private TextField accTxtfield;

    @FXML
    private PasswordField pinTxtfield;

    @FXML
    private Button submitbtn;

    public int tf = 0;

    @FXML
    private void handleSubmission() {
        if (tf == 0) {
            tf++;
        }
        if (tf == 1) {
            String accountNumberStr = accTxtfield.getText();
            String pinStr = pinTxtfield.getText();

            try {
                String validationMessage = AccountValidator.validateAccount(accountNumberStr, pinStr);

                if (validationMessage.equals("Successfully validated.")) {
                    loadTransactions();

                    DatabaseConnection.truncateCurrentSession();
                    Connection connection = DatabaseConnection.getConnection();
                    String sql = "INSERT INTO current_atmuser (acc_no) VALUES (?)";

                    try {
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1, accountNumberStr);
                        statement.executeUpdate();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("Validation Unsuccessful.");
                }
            }
            catch (NumberFormatException | IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Error");
        }
    }

    @FXML
    void one() {
        if (tf == 0) {
            accTxtfield.appendText("1");
        }
        if (tf == 1) {
            pinTxtfield.appendText("1");
        }
    }
    @FXML
    void two() {
        if (tf == 0) {
            accTxtfield.appendText("2");
        }
        if (tf == 1) {
            pinTxtfield.appendText("2");
        }
    }
    @FXML
    void three() {
        if (tf == 0) {
            accTxtfield.appendText("3");
        }
        if (tf == 1) {
            pinTxtfield.appendText("3");
        }
    }
    @FXML
    void four() {
        if (tf == 0) {
            accTxtfield.appendText("4");
        }
        if (tf == 1) {
            pinTxtfield.appendText("4");
        }
    }
    @FXML
    void five() {
        if (tf == 0) {
            accTxtfield.appendText("5");
        }
        if (tf == 1) {
            pinTxtfield.appendText("5");
        }
    }
    @FXML
    void six() {
        if (tf == 0) {
            accTxtfield.appendText("6");
        }
        if (tf == 1) {
            pinTxtfield.appendText("6");
        }
    }
    @FXML
    void seven() {
        if (tf == 0) {
            accTxtfield.appendText("7");
        }
        if (tf == 1) {
            pinTxtfield.appendText("7");
        }
    }
    @FXML
    void eight() {
        if (tf == 0) {
            accTxtfield.appendText("8");
        }
        if (tf == 1) {
            pinTxtfield.appendText("8");
        }
    }
    @FXML
    void nine() {
        if (tf == 0) {
            accTxtfield.appendText("9");
        }
        if (tf == 1) {
            pinTxtfield.appendText("9");
        }
    }
    @FXML
    void zero() {
        if (tf == 0) {
            accTxtfield.appendText("0");
        }
        if (tf == 1) {
            pinTxtfield.appendText("0");
        }
    }

    @FXML
    void backspace() {
        if (tf == 0) {
            String currentText = accTxtfield.getText();
            if (!currentText.isEmpty()) {
                accTxtfield.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
        if (tf == 1) {
            String currentText = pinTxtfield.getText();
            if (!currentText.isEmpty()) {
                pinTxtfield.setText(currentText.substring(0, currentText.length() - 1));
            }
            if (currentText.isEmpty()) {
                tf = 0;
            }
        }
    }

    @FXML
    void loadTransactions() throws IOException {
        Stage currentStage = (Stage) submitbtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(DetailsController.class.getResource("Transactions.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        currentStage.setScene(scene);

        currentStage.setFullScreen(true);
        currentStage.show();
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
