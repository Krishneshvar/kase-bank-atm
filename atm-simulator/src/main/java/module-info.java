module edu.citmss4semjp.atmsimulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires webcam.capture;
    requires java.desktop;
    requires jakarta.mail;
    requires javafx.swing;

    opens edu.citmss4semjp.atmsimulator to javafx.fxml;
    exports edu.citmss4semjp.atmsimulator;
}
