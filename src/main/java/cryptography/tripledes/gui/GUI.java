package cryptography.tripledes.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("GUI-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 350);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(
                "/cryptography/tripledes/gui/style.css")).toExternalForm());
        stage.setTitle("Triple DES Encryption/Decryption Tool");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
