package cryptography.tripledes.gui;

import cryptography.tripledes.dao.KeyReader;
import cryptography.tripledes.managers.KeyManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Duration;

import java.io.File;

public class GUIController {
    @FXML
    private TextField filePathTextField;
    @FXML
    private TextField keyPathTextField;
    private KeyManager keyManager;

    @FXML
    public void initialize() {
        keyManager = new KeyManager(new KeyReader());
    }

    @FXML
    public void onDragOverFilePath(DragEvent event) {
        if (event.getGestureSource() != filePathTextField && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    public void onDragDroppedFilePath(DragEvent event) {
        boolean success = false;
        if (event.getGestureSource() != filePathTextField && event.getDragboard().hasFiles()) {
            File file = event.getDragboard().getFiles().getFirst();
            filePathTextField.setText(file.getAbsolutePath());
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    public void onDragOverKeyPath(DragEvent event) {
        if (event.getGestureSource() != keyPathTextField && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    public void onDragDroppedKeyPath(DragEvent event) {
        boolean success = false;
        if (event.getGestureSource() != keyPathTextField && event.getDragboard().hasFiles()) {
            File file = event.getDragboard().getFiles().getFirst();
            keyPathTextField.setText(file.getAbsolutePath());
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private boolean validateTextField(TextField textField, String promptText) {
        if (textField.getText().isEmpty()) {
            textField.getStyleClass().add("text-field-error");
            textField.setPromptText("This field cannot be empty!");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> {
                textField.getStyleClass().remove("text-field-error");
                textField.setPromptText(promptText);
            }));
            timeline.play();
            return true;
        } else {
            textField.getStyleClass().remove("text-field-error");
            textField.setPromptText(promptText);
            return false;
        }
    }

    private void showMessage(String message) {
        if (message != null && !message.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

    @FXML
    public void encryptFile() {
        if (validateTextField(filePathTextField, "Enter file path") ||
                validateTextField(keyPathTextField, "Enter key file path or destination path")) {
            return;
        }
        showMessage("Encrypting file...");
    }

    @FXML
    public void decryptFile() {
        if (validateTextField(filePathTextField, "Enter file path") ||
                validateTextField(keyPathTextField, "Enter key file path or destination path")) {
            return;
        }
        showMessage("Decrypting file...");
    }

    @FXML
    public void generateKeys() {
        if (validateTextField(keyPathTextField, "Enter key file path or destination path")) {
            return;
        }
        try {
            keyManager.generateKeys(keyPathTextField.getText());
        } catch (Exception e) {
            showMessage("Error generating keys: " + e.getMessage());
            return;
        }
        showMessage("Generating keys successful!");
    }
}