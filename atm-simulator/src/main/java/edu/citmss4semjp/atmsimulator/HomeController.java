package edu.citmss4semjp.atmsimulator;

// JavaFX imports
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.Objects;

public class HomeController {

    private Stage stage;
    private Scene scene;

    @FXML
    private void startScanEntryScene(ActionEvent event) throws IOException {
        Parent scanroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ScanEntryScene.fxml")));
        scene = new Scene(scanroot);

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);

        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void startDetailEntryScene(ActionEvent event) throws IOException {
        Parent detailroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginScene.fxml")));
        scene = new Scene(detailroot);

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);

        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }
}
