module com.perpus {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.perpus to javafx.fxml;
    exports com.perpus;
    exports com.perpus.controllers;
    exports com.perpus.data;
    exports com.perpus.model;
    exports com.perpus.views;
}
