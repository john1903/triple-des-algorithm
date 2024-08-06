package me.jangluzniewicz.tripledes.gui;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
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

/**
 * The MainController class handles the interactions between the MainWindow components and the backend logic
 * for file encryption and decryption using the Triple DES (3DES) algorithm.
 */
public class MainController {
    @FXML
    private TextField filePathTextField; // TextField for displaying and entering file path
    @FXML
    private TextField key1; // TextField for the first encryption key
    @FXML
    private TextField key2; // TextField for the second encryption key
    @FXML
    private TextField key3; // TextField for the third encryption key
    @FXML
    private Button encryptButton; // Button for encrypting the file
    @FXML
    private Button decryptButton; // Button for decrypting the file
    @FXML
    private Button generateKeysButton; // Button for generating encryption keys
    @FXML
    private Button chooseFileButton; // Button for choosing a file to encrypt/decrypt

    private FileManager fileManager; // Manages file reading and writing operations
    private KeyManager keyManager; // Manages key generation and conversion
    private EncryptionManager encryptionManager; // Manages encryption and decryption processes

    /**
     * Initializes the controller, setting up key managers and adding validation to the key fields.
     */
    @FXML
    public void initialize() {
        keyManager = new KeyManager(new KeyGenerator());
        fileManager = new FileManager(new FileReader());
        encryptionManager = new EncryptionManager(new DesEncryption());

        addKeyValidation(key1);
        addKeyValidation(key2);
        addKeyValidation(key3);
    }

    /**
     * Handles drag over event on the file path TextField.
     *
     * @param event the drag event
     */
    @FXML
    public void onDragOverFilePath(DragEvent event) {
        if (event.getGestureSource() != filePathTextField && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    /**
     * Handles drag dropped event on the file path TextField.
     *
     * @param event the drag event
     */
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

    /**
     * Validates the given TextField, checking if it is empty and updating its style and prompt text accordingly.
     *
     * @param textField the TextField to be validated
     * @param promptText the original prompt text to be restored
     * @return true if the TextField is invalid (empty), false otherwise
     */
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

    /**
     * Shows an alert message with the given parameters.
     *
     * @param message the message to be displayed
     * @param title the title of the alert
     * @param type the type of the alert
     */
    private void showMessage(String message, String title, Alert.AlertType type) {
        if (message != null && !message.isEmpty()) {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

    /**
     * Shows a save file dialog with the given title and initial file name.
     *
     * @param title the title of the file dialog
     * @param initialFileName the initial file name
     * @return the selected file, or null if no file was selected
     */
    private File showSaveFileDialog(String title, String initialFileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialFileName(initialFileName);
        return fileChooser.showSaveDialog(null);
    }

    /**
     * Adds validation to a key TextField to ensure it only contains valid hexadecimal characters and is of correct length.
     *
     * @param keyField the key TextField to add validation to
     */
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

    /**
     * Removes the validation from a key TextField.
     *
     * @param keyField the key TextField to remove validation from
     */
    private void removeKeyValidation(TextField keyField) {
        ChangeListener<String> listener = (ChangeListener<String>) keyField.getProperties().get("listener");
        if (listener != null) {
            keyField.textProperty().removeListener(listener);
        }
    }

    private void setUIElementsDisabled(boolean disabled) {
        encryptButton.setDisable(disabled);
        decryptButton.setDisable(disabled);
        generateKeysButton.setDisable(disabled);
        chooseFileButton.setDisable(disabled);
        filePathTextField.setDisable(disabled);
        key1.setDisable(disabled);
        key2.setDisable(disabled);
        key3.setDisable(disabled);
    }

    /**
     * Encrypts the file specified in the file path TextField using the keys provided in the key TextFields.
     */
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

        Task<Boolean> encryptTask = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                setUIElementsDisabled(true);
                BitSet fileContent = fileManager.read(inputFilePath);
                fileContent = encryptionManager.encrypt(fileContent,
                        keyManager.hexStringToBitSet(key1.getText()),
                        keyManager.hexStringToBitSet(key2.getText()),
                        keyManager.hexStringToBitSet(key3.getText()));
                fileManager.write(saveFile.getAbsolutePath(), fileContent);
                return true;
            }
        };

        encryptTask.setOnSucceeded(event -> {
            setUIElementsDisabled(false);
            showMessage("Encrypting successful!", "Success", Alert.AlertType.INFORMATION);
            encryptionManager.shutdown();  // Shutdown executor service after encryption is done
        });

        encryptTask.setOnFailed(event -> {
            Throwable exception = encryptTask.getException();
            setUIElementsDisabled(false);
            showMessage("Error encrypting file: " + exception.getMessage(), "Error", Alert.AlertType.ERROR);
            encryptionManager.shutdown();  // Ensure shutdown even on failure
        });

        Thread encryptThread = new Thread(encryptTask);
        encryptThread.setDaemon(true);
        encryptThread.start();
    }

    /**
     * Decrypts the file specified in the file path TextField using the keys provided in the key TextFields.
     */
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

        Task<Boolean> decryptTask = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                setUIElementsDisabled(true);
                BitSet fileContent = fileManager.read(inputFilePath);
                fileContent = encryptionManager.decrypt(fileContent,
                        keyManager.hexStringToBitSet(key1.getText()),
                        keyManager.hexStringToBitSet(key2.getText()),
                        keyManager.hexStringToBitSet(key3.getText()));
                fileManager.write(saveFile.getAbsolutePath(), fileContent);
                return true;
            }
        };

        decryptTask.setOnSucceeded(event -> {
            setUIElementsDisabled(false);
            showMessage("Decrypting successful!", "Success", Alert.AlertType.INFORMATION);
            encryptionManager.shutdown();  // Shutdown executor service after decryption is done
        });

        decryptTask.setOnFailed(event -> {
            Throwable exception = decryptTask.getException();
            setUIElementsDisabled(false);
            showMessage("Error decrypting file: " + exception.getMessage(), "Error", Alert.AlertType.ERROR);
            encryptionManager.shutdown();  // Ensure shutdown even on failure
        });

        Thread decryptThread = new Thread(decryptTask);
        decryptThread.setDaemon(true);
        decryptThread.start();
    }

    /**
     * Generates three keys and writes them to a file.
     */
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

    /**
     * Retrieves the file extension from a file path.
     *
     * @param filePath the file path
     * @return the file extension, or an empty string if none is found
     */
    private String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filePath.length() - 1) {
            return filePath.substring(dotIndex);
        }
        return "";
    }

    /**
     * Opens a file chooser dialog for selecting a data file and sets the file path TextField with the chosen file path.
     */
    @FXML
    void readDataFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Data File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            filePathTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Validates if a key is a valid 16-character hexadecimal string.
     *
     * @param key the key to be validated
     * @return true if the key is valid, false otherwise
     */
    private boolean isValidHexKey(String key) {
        return key == null || key.length() != 32 || !key.matches("[0-9a-f]{32}");
    }

    /**
     * Checks if the input fields are invalid and shows appropriate error messages.
     *
     * @return true if any input field is invalid, false otherwise
     */
    private boolean isInvalidInput() {
        if (validateTextField(filePathTextField, "Enter file path") ||
                validateTextField(key1, "First key") || validateTextField(key2, "Second key") ||
                validateTextField(key3, "Third key")) {
            return true;
        }

        if (isValidHexKey(key1.getText()) || isValidHexKey(key2.getText()) || isValidHexKey(key3.getText())) {
            showMessage("Keys must be 16-character hexadecimal strings!", "Invalid Key", Alert.AlertType.ERROR);
            return true;
        }
        return false;
    }
}