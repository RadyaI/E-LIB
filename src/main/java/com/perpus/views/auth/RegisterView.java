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

/*
 ======================================================
  FILE       : RegisterView.java
  FITUR      : Antarmuka registrasi anggota baru
  FUNGSI     : - Menyediakan form input ID, nama, nomor HP, dan alamat
               - Menambahkan anggota ke dalam data jika valid
               - Navigasi ke dashboard jika berhasil
  DIBUAT OLEH: - Muhammad Radya Iftikhar (202410370110370)
               - Ramanda Bagus Prawobo (202410370110380)
               - Athallah Rasyad Zaidan (202410370110361)
               - Anggara Aribawa Paramesti (202410370110346)
               - Rifky Septian Kusuma (202410370110351)
 ======================================================
*/

public class RegisterView extends StackPane {

    private TextField idField;
    private TextField namaField;
    private TextField nomorHandphoneField;
    private TextField alamatField;
    private Button loginButton, RegisterButton;

    private AnggotaController anggotaController;

    public RegisterView(Stage stage) {
        anggotaController = new AnggotaController();

        VBox card = new VBox(10);
        card.setPadding(new Insets(30));
        card.setMaxWidth(550);
        card.getStyleClass().add("login-card");

        Label labelTitle = new Label("PERPUSTAKAAN REGISTER");
        labelTitle.getStyleClass().add("login-title");

        // Input ID
        Label labelId = new Label("ID Anggota");
        idField = new TextField();
        idField.setPromptText("Contoh: 123");
        labelId.getStyleClass().add("login-label");
        idField.getStyleClass().add("login-input");

        // Input Nama
        Label labelNama = new Label("Nama Anggota");
        namaField = new TextField();
        namaField.setPromptText("Contoh: admin");
        labelNama.getStyleClass().add("login-label");
        namaField.getStyleClass().add("login-input");

        // Input No HP
        Label labelNoHp = new Label("Nomor Handphone");
        nomorHandphoneField = new TextField();
        nomorHandphoneField.setPromptText("Contoh: 08123");
        labelNoHp.getStyleClass().add("login-label");
        nomorHandphoneField.getStyleClass().add("login-input");

        // Input Alamat
        Label labelAlamat = new Label("Alamat");
        alamatField = new TextField();
        alamatField.setPromptText("Contoh: Jl. Mawar No.10");
        labelAlamat.getStyleClass().add("login-label");
        alamatField.getStyleClass().add("login-input");

        // Tombol login
        loginButton = new Button("Login");
        loginButton.setOnAction(e -> handleLogin(stage));
        loginButton.getStyleClass().add("login-button");
        loginButton.setPrefWidth(100);
        loginButton.setPrefHeight(35);

        // Tombol register
        RegisterButton = new Button("Register");
        RegisterButton.setOnAction(e -> handleRegister(stage));
        RegisterButton.getStyleClass().add("register-button");
        RegisterButton.setPrefWidth(100);
        RegisterButton.setPrefHeight(35);

        HBox ActionButton = new HBox(10, loginButton, RegisterButton);

        card.getChildren().addAll(labelTitle, labelId, idField, labelNama, namaField, labelNoHp, nomorHandphoneField,
                labelAlamat, alamatField, ActionButton);

        VBox container = new VBox(card);
        container.setAlignment(Pos.TOP_CENTER);
        container.setPadding(new Insets(30, 0, 0, 0));

        Circle circle = new Circle(80);
        circle.setFill(new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#3b82f6", 0.6)), new Stop(1, Color.TRANSPARENT)));
        circle.setEffect(new GaussianBlur(100));

        StackPane circleContainer = new StackPane(circle);
        circleContainer.setPrefSize(150, 150);
        circleContainer.setTranslateX(250);
        circleContainer.setTranslateY(150);

        this.getChildren().addAll(circleContainer, container);
        this.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());
    }

    // Navigasi ke halaman login
    private void handleLogin(Stage stage) {
        LoginView login = new LoginView(stage);
        Scene loginScene = new Scene(login, 850, 500);
        stage.setScene(loginScene);
    }

    // Menangani proses registrasi
    private void handleRegister(Stage stage) {
        try {
            int id = Integer.parseInt(idField.getText());
            String nama = namaField.getText();
            int nomorHp = Integer.parseInt(nomorHandphoneField.getText());
            String alamat = alamatField.getText();

            if (nomorHandphoneField.getText().length() != 5) {
                showAlert("Error", "Nomor Handphone harus 5 digit!");
                return;
            }

            boolean success = anggotaController.register(id, nama, nomorHp, alamat);

            if (success) {
                Anggota anggota = new Anggota(id, nama, nomorHp, alamat);
                idField.clear();
                namaField.clear();
                nomorHandphoneField.clear();
                alamatField.clear();

                DashboardView dashboard = new DashboardView(stage, anggota);
                Scene dashboardScene = new Scene(dashboard, 700, 500);
                stage.setScene(dashboardScene);
            } else {
                showAlert("Gagal", "ID sudah terdaftar. Gunakan ID lain.");
            }
        } catch (NumberFormatException ex) {
            showAlert("Error", "ID dan Nomor Handphone harus berupa angka!");
        }
    }

    // Menampilkan alert informasi
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
