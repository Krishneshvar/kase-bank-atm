package edu.citmss4semjp.atmsimulator;

// JavaFX imports
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ATMUI extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("ATM Simulator");
        Image appIcon = new Image("KASE_Logo2.png");
        stage.getIcons().add(appIcon);

        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    public static void launchApp() {
        launch();
    }
}
