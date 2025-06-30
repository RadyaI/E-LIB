package com.perpus;

import com.perpus.controllers.AnggotaController;
import com.perpus.views.auth.LoginView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 =========================================================
  FILE       : MainApp.java
  FITUR      : Entry Point Aplikasi Perpustakaan
  FUNGSI     : - Inisialisasi data admin jika belum tersedia
               - Menjalankan tampilan awal LoginView
  DIBUAT OLEH: - Muhammad Radya Iftikhar (202410370110370)
               - Ramanda Bagus Prawobo (202410370110380)
               - Athallah Rasyad Zaidan (202410370110361)
               - Anggara Aribawa Paramesti (202410370110346)
               - Rifky Septian Kusuma (202410370110351)
 =========================================================
*/

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnggotaController anggotaController = new AnggotaController();

        // Cek apakah akun admin sudah ada
        boolean exists = anggotaController.login(123, "admin");
        if (!exists) {
            System.out.println("Data admin belum ada, menambah data...");
            anggotaController.tambahAnggota(123, "admin", 927364023, "Admin Street");
        }

        // Tampilkan halaman login
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
