package me.jangluzniewicz.tripledes.gui;

import me.jangluzniewicz.tripledes.dao.FileReader;
import me.jangluzniewicz.tripledes.logic.DesEncryption;
import me.jangluzniewicz.tripledes.logic.KeyGenerator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import me.jangluzniewicz.tripledes.managers.EncryptionManager;
import me.jangluzniewicz.tripledes.managers.FileManager;
import me.jangluzniewicz.tripledes.managers.KeyManager;

import java.io.File;
import java.util.BitSet;

public class GUIController {
    @FXML
    private TextField filePathTextField;
    @FXML
    private TextField key1;
    @FXML
    private TextField key2;
    @FXML
    private TextField key3;

    private FileManager fileManager;
    private KeyManager keyManager;
    private EncryptionManager encryptionManager;

    @FXML
    public void initialize() {
        keyManager = new KeyManager(new KeyGenerator());
        fileManager = new FileManager(new FileReader());
        encryptionManager = new EncryptionManager(new DesEncryption());

        addKeyValidation(key1);
        addKeyValidation(key2);
        addKeyValidation(key3);
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
        if (event.getGestureSource() != filePathTextField && event.getDragboard().hasFiles()) {
            File file = event.getDragboard().getFiles().getFirst();
            filePathTextField.setText(file.getAbsolutePath());
            event.setDropCompleted(true);
        } else {
            event.setDropCompleted(false);
        }
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

    private void showMessage(String message, String title, Alert.AlertType type) {
        if (message != null && !message.isEmpty()) {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

    private File showSaveFileDialog(String title, String initialFileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialFileName(initialFileName);
        return fileChooser.showSaveDialog(null);
    }

    private void addKeyValidation(TextField keyField) {
        ChangeListener<String> listener = (observable, oldValue, newValue) -> {
            if (newValue.length() > 32) {
                keyField.setText(oldValue);
            } else {
                keyField.setText(newValue.toLowerCase().replaceAll("[^0-9a-f]", ""));
            }
            keyField.positionCaret(newValue.length());
        };
        keyField.textProperty().addListener(listener);
        keyField.getProperties().put("listener", listener);
    }

    private void removeKeyValidation(TextField keyField) {
        ChangeListener<String> listener = (ChangeListener<String>) keyField.getProperties().get("listener");
        if (listener != null) {
            keyField.textProperty().removeListener(listener);
        }
    }

    @FXML
    public void encryptFile() {
        if (isInvalidInput()) {
            return;
        }

        String inputFilePath = filePathTextField.getText();
        String fileExtension = getFileExtension(inputFilePath);
        File saveFile = showSaveFileDialog("Save Encrypted File", "encrypted" + fileExtension);
        if (saveFile == null) {
            showMessage("File save location not chosen!", "Warning", Alert.AlertType.WARNING);
            return;
        }

        try {
            BitSet fileContent = fileManager.read(inputFilePath);
            fileContent = encryptionManager.encrypt(fileContent, keyManager.hexStringToBitSet(key1.getText()),
                    keyManager.hexStringToBitSet(key2.getText()), keyManager.hexStringToBitSet(key3.getText()));
            fileManager.write(saveFile.getAbsolutePath(), fileContent);
            showMessage("Encrypting successful!", "Success", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showMessage("Error processing file: " + e.getMessage(), "Error", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void decryptFile() {
        if (isInvalidInput()) {
            return;
        }

        String inputFilePath = filePathTextField.getText();
        String fileExtension = getFileExtension(inputFilePath);
        File saveFile = showSaveFileDialog("Save Decrypted File", "decrypted" + fileExtension);
        if (saveFile == null) {
            showMessage("File save location not chosen!", "Warning", Alert.AlertType.WARNING);
            return;
        }

        try {
            BitSet fileContent = fileManager.read(inputFilePath);
            fileContent = encryptionManager.decrypt(fileContent, keyManager.hexStringToBitSet(key1.getText()),
                    keyManager.hexStringToBitSet(key2.getText()), keyManager.hexStringToBitSet(key3.getText()));
            fileManager.write(saveFile.getAbsolutePath(), fileContent);
            showMessage("Decrypting successful!", "Success", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showMessage("Error processing file: " + e.getMessage(), "Error", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void generateKeys() {
        File saveFile = showSaveFileDialog("Save Generated Keys", "keys.txt");
        if (saveFile == null) {
            showMessage("File save location not chosen!", "Warning", Alert.AlertType.WARNING);
            return;
        }

        try {
            removeKeyValidation(key1);
            removeKeyValidation(key2);
            removeKeyValidation(key3);

            key1.setText(keyManager.bitsToHexString(keyManager.generateKey()));
            key2.setText(keyManager.bitsToHexString(keyManager.generateKey()));
            key3.setText(keyManager.bitsToHexString(keyManager.generateKey()));

            addKeyValidation(key1);
            addKeyValidation(key2);
            addKeyValidation(key3);

            String keys = "Key 1: " + key1.getText() + "\n" +
                    "Key 2: " + key2.getText() + "\n" +
                    "Key 3: " + key3.getText() + "\n";

            fileManager.write(saveFile.getAbsolutePath(), keys.getBytes());
            showMessage("Generating keys successful!", "Success", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showMessage("Error writing keys to file: " + e.getMessage(), "Error", Alert.AlertType.ERROR);
        }
    }

    private String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filePath.length() - 1) {
            return filePath.substring(dotIndex);
        }
        return "";
    }

    @FXML
    void readDataFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Data File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            filePathTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    private boolean isValidHexKey(String key) {
        return key != null && key.length() == 32 && key.matches("[0-9a-f]{32}");
    }

    private boolean isInvalidInput() {
        if (validateTextField(filePathTextField, "Enter file path") ||
                validateTextField(key1, "First key") || validateTextField(key2, "Second key") ||
                validateTextField(key3, "Third key")) {
            return true;
        }

        if (!isValidHexKey(key1.getText()) || !isValidHexKey(key2.getText()) || !isValidHexKey(key3.getText())) {
            showMessage("Keys must be 16-character hexadecimal strings!", "Invalid Key", Alert.AlertType.ERROR);
            return true;
        }
        return false;
    }
}