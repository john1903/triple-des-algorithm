module cryptography.tripledes {
    requires javafx.controls;
    requires javafx.fxml;
    exports cryptography.tripledes.gui;
    opens cryptography.tripledes.gui to javafx.fxml;
}