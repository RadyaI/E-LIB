package com.perpus;

import com.perpus.controllers.AnggotaController;
import com.perpus.views.auth.LoginView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnggotaController anggotaController = new AnggotaController();
        boolean exists = anggotaController.login(123, "admin");
        if (!exists) {
            System.out.println("Data admin belum ada, menambah data...");
            anggotaController.tambahAnggota(123, "admin", 927364023, "Admin Street");
        }

        LoginView loginView = new LoginView(primaryStage);
        Scene scene = new Scene(loginView, 700, 500);
        primaryStage.setTitle("Perpustakaan - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}