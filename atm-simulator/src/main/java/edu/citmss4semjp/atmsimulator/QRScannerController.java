package edu.citmss4semjp.atmsimulator;

import com.github.sarxos.webcam.Webcam;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class QRScannerController {
    @FXML
    private Button cancel;

    @FXML
    private Button enter;

    @FXML
    private TextField pinTxtField;

    @FXML
    private Button submitPin;

    @FXML
    private ImageView camView;

    private Webcam webcam;
    private AtomicBoolean stopCamera = new AtomicBoolean(false);
    private Thread cameraThread;
    private Thread qrScanThread;

    @FXML
    public void initialize() {
        webcam = Webcam.getDefault();
        webcam.setViewSize(Webcam.getDefault().getViewSizes()[0]);
        webcam.open();

        cameraThread = new Thread(() -> {
            try {
                while (!stopCamera.get()) {
                    BufferedImage image = webcam.getImage();
                    if (image != null) {
                        WritableImage fxImage = SwingFXUtils.toFXImage(image, null);
                        Platform.runLater(() -> camView.setImage(fxImage));
                    }
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (webcam.isOpen()) {
                    webcam.close();
                }
            }
        });

        cameraThread.setDaemon(true);
        cameraThread.start();

        Platform.runLater(() -> {
            Stage stage = (Stage) camView.getScene().getWindow();
            qrScanThread = new Thread(() -> {
                try {
                    QRScanner.scanQR(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stopCamera.set(true);
            });

            qrScanThread.setDaemon(true);
            qrScanThread.start();
        });
        webcam.close();
    }
    @FXML
    void validate(ActionEvent event) throws IOException {
        String accNo = DatabaseConnection.getAccountNumberFromCurrentSession();
        String PIN = pinTxtField.getText();

        String validateMsg = AccountValidator.validateAccount(accNo, PIN);

        if (Objects.equals(validateMsg, "Successfully validated.")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Transactions.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.setFullScreen(true);
            currentStage.show();
        }
        else {
            ShowAlerts.showErrorAlert("Invalid PIN", "The entered PIN is invalid. Please try again.");
        }
    }
    @FXML
    void goHome(ActionEvent event) throws IOException {
        webcam.close();
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
