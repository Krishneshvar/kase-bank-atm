/**
 * This file is only used to create QR codes for those who have a bank account, but do not have a
 * debit card to access the ATM. This QR code helps them overcome that barrier, by using it as a medium
 * for the ATM to access user information.
 */

package edu.citmss4semjp.atmsimulator;

// Package imports
import java.util.Scanner; // Class for reading user input
import java.io.IOException; // Class for handling I/O exceptions
import java.nio.file.FileSystems; // Class for accessing the default file system
import java.nio.file.Path; // Class for representing file system paths

// Zebra Crossing library imports
import com.google.zxing.BarcodeFormat; // Class for defining barcode formats
import com.google.zxing.WriterException; // Class for handling exceptions while generating QR code
import com.google.zxing.client.j2se.MatrixToImageWriter; // Class for writing QR code matrix on image
import com.google.zxing.common.BitMatrix; // Class for representing QR code matrix
import com.google.zxing.qrcode.QRCodeWriter; // Class for encoding QR code

// SQL imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class genFormat {
    /**
     * Generates the QR code with given data
     */
    static void genQRCode(String data, String fileName, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrcWriter = new QRCodeWriter();
        BitMatrix bitMat = qrcWriter.encode(data, BarcodeFormat.QR_CODE, width, height); // Encodes data into bitMat

        Path path = FileSystems.getDefault().getPath(fileName);
        MatrixToImageWriter.writeToPath(bitMat, "PNG", path); // Write bitMat to a PNG file
    }
}

public class QRCodeGen {
    public static void main(String[] args) {
        Scanner obj = new Scanner(System.in);

        try {
            System.out.print("\nEnter Customer ID: ");
            String custID = obj.nextLine(); // Get Customer ID as user input
            obj.close();

            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM customers WHERE cust_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, custID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String customFileName = "./" + resultSet.getString("cust_name") + ".png"; // Create file name using account holder name
                genFormat.genQRCode(custID, customFileName, 300, 300);
            }
            else {
                System.out.println("Account not found.");
            }
        }
        catch (WriterException | IOException | SQLException e) {
            System.out.println("\nException: " + e);
        }
    }
}
