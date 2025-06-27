package com.perpus.views.auth;

import com.perpus.controllers.AnggotaController;
import com.perpus.model.Anggota;
import com.perpus.views.DashboardView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class LoginView extends StackPane {

    private TextField idField;
    private TextField namaField;
    private Button loginButton, RegisterButton;

    private AnggotaController anggotaController;

    public LoginView(Stage stage) {
        anggotaController = new AnggotaController();

        VBox card = new VBox(10);
        card.setPadding(new Insets(30));
        card.setMaxWidth(550);
        card.getStyleClass().add("login-card");

        Label labelTitle = new Label("PERPUSTAKAAN LOGIN");
        labelTitle.getStyleClass().add("login-title");

        Label labelId = new Label("ID Anggota");
        labelId.getStyleClass().add("login-label");
        idField = new TextField();
        idField.setPromptText("Contoh: 123");
        idField.getStyleClass().add("login-input");

        Label labelNama = new Label("Nama Anggota");
        labelNama.getStyleClass().add("login-label");
        namaField = new TextField();
        namaField.setPromptText("Contoh: admin");
        namaField.getStyleClass().add("login-input");

        loginButton = new Button("Login");
        loginButton.getStyleClass().add("login-button");
        loginButton.setPrefWidth(100);
        loginButton.setPrefHeight(35);
        loginButton.setOnAction(e -> handleLogin(stage));

        RegisterButton = new Button("Register");
        RegisterButton.getStyleClass().add("register-button");
        RegisterButton.setPrefWidth(100);
        RegisterButton.setPrefHeight(35);
        RegisterButton.setOnAction(e -> handleRegister(stage));

        HBox ActionButton = new HBox(10, loginButton, RegisterButton);

        card.getChildren().addAll(labelTitle, labelId, idField, labelNama, namaField, ActionButton);

        VBox container = new VBox(card);
        container.setAlignment(Pos.TOP_CENTER);
        container.setPadding(new Insets(80, 0, 0, 0));

        Circle circle = new Circle(80);
        circle.setFill(new RadialGradient(
                0, 0,
                0.5, 0.5,
                1,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#3b82f6", 0.6)),
                new Stop(1, Color.TRANSPARENT)));
        circle.setEffect(new GaussianBlur(100));

        StackPane circleContainer = new StackPane(circle);
        circleContainer.setPrefSize(150, 150);
        circleContainer.setTranslateX(250);
        circleContainer.setTranslateY(150);

        this.getChildren().addAll(circleContainer, container);

        this.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());
    }

    private void handleLogin(Stage stage) {
        try {
            int id = Integer.parseInt(idField.getText());
            String nama = namaField.getText();

            boolean isLogin = anggotaController.login(id, nama);

            if (isLogin) {
                System.out.println("Login berhasil!");

                Anggota anggota = anggotaController.cariAnggota(id);

                DashboardView dashboard = new DashboardView(stage, anggota);
                Scene dashboardScene = new Scene(dashboard, 850, 500);
                stage.setScene(dashboardScene);
            } else {
                showAlert("Login Gagal", "ID atau Nama tidak cocok!");
            }
        } catch (NumberFormatException ex) {
            showAlert("Error", "ID harus berupa angka!");
        }
    }

    private void handleRegister(Stage stage) {
        RegisterView register = new RegisterView(stage);
        Scene registerScene = new Scene(register, 700, 500);
        stage.setScene(registerScene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
