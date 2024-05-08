package edu.citmss4semjp.atmsimulator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

public class HomeController {

    @FXML
    private Button detailsbtn;

    @FXML
    private Button scanbtn;

    private Stage stage;
    private Scene scene;
//    @FXML
//    private void initialize() {
//        scanbtn.setOnAction(event -> {
//            try {
//                startScanEntryScene();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        detailsbtn.setOnAction(event  -> {
//            try {
//                startDetailEntryScene();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//    }

    @FXML
    private void startScanEntryScene(ActionEvent event) throws IOException {
        Parent scanroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ScanEntryScene.fxml")));
        scene = new Scene(scanroot);

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);

        stage.setFullScreen(true);
//        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void startDetailEntryScene(ActionEvent event) throws IOException {
        Parent detailroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginScene.fxml")));
        scene = new Scene(detailroot);

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);

        stage.setFullScreen(true);
//        stage.setResizable(false);
        stage.show();
    }
}
