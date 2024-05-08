package edu.citmss4semjp.atmsimulator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TransactionsController {

    @FXML
    private Button balBtn;

    @FXML
    private Button depositBtn;

    @FXML
    private Button ftBtn;

    @FXML
    private Button pinchangeBtn;

    @FXML
    private Button pnochangeBtn;

    @FXML
    private Button withdrawBtn;

    @FXML
    private void initialize() {
        balBtn.setOnAction(event  -> {
            try {
                startBalanceInqScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        depositBtn.setOnAction(event  -> {
            try {
                startDepositScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ftBtn.setOnAction(event  -> {
            try {
                startFTScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        pinchangeBtn.setOnAction(event  -> {
            try {
                startPinChangeScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        pnochangeBtn.setOnAction(event  -> {
            try {
                startPNoChangeScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        withdrawBtn.setOnAction(event  -> {
            try {
                startWithdrawScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void startBalanceInqScene() throws IOException {
        Parent detailroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Balance.fxml")));
        Scene scene = new Scene(detailroot);

        Stage stage = (Stage) balBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    private void startDepositScene() throws IOException {
        Parent detailroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Deposit.fxml")));
        Scene scene = new Scene(detailroot);

        Stage stage = (Stage) depositBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    private void startFTScene() throws IOException {
        Parent detailroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FundsTransfer.fxml")));
        Scene scene = new Scene(detailroot);

        Stage stage = (Stage) ftBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    private void startWithdrawScene() throws IOException {
        Parent detailroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Withdraw.fxml")));
        Scene scene = new Scene(detailroot);

        Stage stage = (Stage) withdrawBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    private void startPNoChangeScene() throws IOException {
        Parent detailroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PhNoChange.fxml")));
        Scene scene = new Scene(detailroot);

        Stage stage = (Stage) pnochangeBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    private void startPinChangeScene() throws IOException {
        Parent detailroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PinChange.fxml")));
        Scene scene = new Scene(detailroot);

        Stage stage = (Stage) pinchangeBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }
}
