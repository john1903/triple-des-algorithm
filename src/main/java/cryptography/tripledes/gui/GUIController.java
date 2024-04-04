package cryptography.tripledes.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class GUIController {
    @FXML
    private TextField filePathTextField;
    @FXML
    private TextField keyPathTextField;

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
        showMessage("Generating keys...");
    }
}
