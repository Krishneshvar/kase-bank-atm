package edu.citmss4semjp.atmsimulator;

// JavaMail imports
import javax.mail.*;
import javax.mail.internet.*;

// SQL imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

class MailGenerator {

    public static void sendMail(String recipient) throws SQLException {
        System.out.println("Preparing to Send Email...");

        // Store the properties needed to access SMTP server
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Sender User name(email) and Password
        final String username = "71762231048@cit.edu.in";
        final String password = "shaanthakumar.cit";

        // Create Session Object and pass the Credentials
        Session session = Session.getInstance(properties,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        // Create Message Object receiving from
        Message message = prepareMessage(session, username, recipient);

        try {
            Transport.send(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Email Sent Successfully to " + recipient);
    }

    private static void updateOTP(String otp) throws SQLException {
        String accNo = DatabaseConnection.getAccountNumberFromCurrentSession();

        Connection connection = DatabaseConnection.getConnection();
        String sql = "UPDATE current_atmuser SET otp = ? WHERE acc_no = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, otp);
        statement.setString(2, accNo);
        statement.executeUpdate();
    }

    private static Message prepareMessage(Session session, String username, String recipient) throws SQLException {

        String otp = OtpGenerator.genOTP();
        updateOTP(otp);
        Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("ATM validation OTP");
            message.setText("\nHello KASE Bank User!" +
                            "\nThe OTP for your current transaction is " + otp + "." +
                            "\nPlease DO NOT share this code with anyone." +
                            "If you aren't using the ATM right now, but you got this message, please report to the nearest bank." +
                            "\n\nThank you!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}
