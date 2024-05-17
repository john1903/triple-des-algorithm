package me.jangluzniewicz.tripledes.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The GUI class serves as the entry point for the JavaFX application, setting up and displaying the main window.
 */
public class GUI extends Application {

    /**
     * Starts the JavaFX application by setting up the main stage and loading the FXML layout.
     *
     * @param stage the primary stage for this application
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("GUI-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Encryption/Decryption Tool");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * The main method serves as the entry point for the application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}