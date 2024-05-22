package edu.citmss4semjp.atmsimulator;

// JavFX imports
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class NumPadController extends GridPane{

    private TextField currentField;
    private StringBuilder input = new StringBuilder();

    public void NumPad() {
        setPadding(new Insets(10));
        setHgap(5);
        setVgap(5);

        // Add number buttons
        for (int i = 1; i <= 9; i++) {
            Button button = new Button(String.valueOf(i));
            button.setOnAction(event -> handleButtonClick(button.getText()));
            add(button, (i - 1) % 3, (i - 1) / 3);
        }

        // Add additional buttons
        Button button0 = new Button("0");
        button0.setOnAction(event -> handleButtonClick("0"));
        add(button0, 0, 3);

        Button buttonClear = new Button("Clear");
        buttonClear.setOnAction(event -> handleButtonClick("Clear"));
        add(buttonClear, 1, 3);

        Button buttonEnter = new Button("Enter");
        buttonEnter.setOnAction(event -> handleButtonClick("Enter"));
        add(buttonEnter, 2, 3);
    }

    private void handleButtonClick(String command) {
        if (command.matches("\\d")) { // If the command is a digit
            input.append(command);
            updateCurrentField();
        } else {
            switch (command) {
                case "Clear":
                    input.setLength(0);
                    updateCurrentField();
                    break;
                case "Enter":
                    // Perform desired action with the input value
                    System.out.println("Input value: " + input.toString());
                    input.setLength(0);
                    updateCurrentField();
                    break;
            }
        }
    }

    private void updateCurrentField() {
        if (currentField != null) {
            currentField.setText(input.toString());
        }
    }

    public void setCurrentField(TextField field) {
        if (currentField != null) {
            currentField.focusedProperty().removeListener(this::handleFocusChange);
        }
        currentField = field;
        if (currentField != null) {
            currentField.focusedProperty().addListener(this::handleFocusChange);
        }
    }

    private void handleFocusChange(Observable observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            setCurrentField((TextField) ((javafx.beans.property.ReadOnlyBooleanPropertyBase) observable).getBean());
        }
    }
}
