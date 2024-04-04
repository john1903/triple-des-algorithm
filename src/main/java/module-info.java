module cryptography.tripledes {
    requires javafx.controls;
    requires javafx.fxml;


    opens cryptography.tripledes to javafx.fxml;
    exports cryptography.tripledes;
    exports cryptography.tripledes.gui;
    opens cryptography.tripledes.gui to javafx.fxml;
}