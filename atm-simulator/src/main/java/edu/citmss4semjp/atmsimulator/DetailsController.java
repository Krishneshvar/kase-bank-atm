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

    @FXML
    private void handleSubmission() {
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
