package edu.citmss4semjp.atmsimulator;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QRScanner {

    public static void scanQR(Stage primaryStage) throws IOException {
        Webcam webcam = Webcam.getDefault();
        webcam.open();

        boolean qrFound = false;

        while (!qrFound) {
            BufferedImage frame = webcam.getImage();

            LuminanceSource source = new BufferedImageLuminanceSource(frame);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                Result result = new MultiFormatReader().decode(bitmap);
                String qrCodeData = result.getText();

                // Validate the QR code data against the PostgreSQL database
                Connection connection = DatabaseConnection.getConnection();
                String query = "SELECT * FROM customers WHERE cust_id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, qrCodeData);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String name = resultSet.getString("cust_name");
                    System.out.println("QR code data is valid!\nQR Data: " + qrCodeData + "\nAccount Holder: " + name);

                    try {
                        String sql = "SELECT acc_no FROM acc_details WHERE cust_id = ?";
                        statement = connection.prepareStatement(sql);
                        statement.setString(1, qrCodeData);
                        resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                            String accNo = resultSet.getString("acc_no");

                            sql = "INSERT INTO current_atmuser (acc_no) VALUES (?)";
                            statement = connection.prepareStatement(sql);
                            statement.setString(1, accNo);
                            statement.executeUpdate();
                        }
                        else {
                            System.out.println("Current user could not be updated!");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    qrFound = true;
                } else {
                    System.out.println("QR code data is invalid!");
                }
            } catch (NotFoundException e) {
                System.out.println(".");
            } catch (SQLException se) {
                System.out.println("SQL Exception: " + se);
            }
        }
        webcam.close();

        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(DetailsController.class.getResource("PINValidator.fxml"));

            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            primaryStage.setFullScreen(true);
            primaryStage.show();
        });
    }
}
