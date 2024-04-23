module me.jangluzniewicz.tripledes {
    requires javafx.controls;
    requires javafx.fxml;
    opens me.jangluzniewicz.tripledes.gui to javafx.fxml;
    exports me.jangluzniewicz.tripledes.gui;
}
