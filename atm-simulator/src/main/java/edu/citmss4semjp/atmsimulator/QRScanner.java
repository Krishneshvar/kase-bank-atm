package edu.citmss4semjp.atmsimulator;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QRScanner {
    public static void main(String[] args) {
        Webcam webcam = Webcam.getDefault();
        webcam.open();

        while (true) {
            BufferedImage frame = webcam.getImage();

            LuminanceSource source = new BufferedImageLuminanceSource(frame);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                Result result = new MultiFormatReader().decode(bitmap);
                String qrCodeData = result.getText();

                // Validate the QR code data against the PostgreSQL database
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kasebankdatabase", "postgres", "kichapostgresequel");
                String query = "SELECT * FROM customers WHERE acc_no = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, qrCodeData);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    System.out.println("QR code data is valid!\nQR Data: " + qrCodeData + "\nAccount Holder: " + resultSet.getString("name"));
                }
                else {
                    System.out.println("QR code data is invalid!");
                }

                connection.close();
                break;
            }
            catch (NotFoundException nfe) {
                System.out.println("Not Found Exception: " + nfe);
            }
            catch (SQLException se) {
                System.out.println("SQL Exception: " + se);
            }
        }
        webcam.close();
    }
}
