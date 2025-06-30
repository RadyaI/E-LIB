package com.perpus.views;

import java.util.Optional;

import com.perpus.controllers.AnggotaController;
import com.perpus.model.Anggota;
import com.perpus.views.auth.LoginView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/*
 =========================================================
  FILE       : AnggotaView.java
  FITUR      : Halaman profil anggota (view dan edit data)
  FUNGSI     : - Menampilkan data anggota yang sedang login
               - Mengizinkan update dan delete akun anggota
               - Navigasi ke halaman dashboard atau login
  DIBUAT OLEH: - Muhammad Radya Iftikhar (202410370110370)
               - Ramanda Bagus Prawobo (202410370110380)
               - Athallah Rasyad Zaidan (202410370110361)
               - Anggara Aribawa Paramesti (202410370110346)
               - Rifky Septian Kusuma (202410370110351)
 =========================================================
*/

public class AnggotaView extends BorderPane {

    private TextField namaField;
    private TextField noHpField;
    private TextField alamatField;
    private Button updateButton, deleteButton;

    private Anggota loggedInUser;
    private AnggotaController anggotaController;

    public AnggotaView(Stage stage, Anggota loggedInUser) {
        this.loggedInUser = loggedInUser;
        this.anggotaController = new AnggotaController();

        // Navbar atas
        HBox navbar = createNavbar(stage);
        this.setTop(navbar);

        // Kartu profil di tengah
        VBox card = createProfileCard(stage);
        StackPane cardContainer = new StackPane(card);
        cardContainer.setAlignment(Pos.CENTER);
        this.setCenter(cardContainer);

        this.getStylesheets().add(getClass().getResource("/styles/anggota.css").toExternalForm());
    }

    private HBox createNavbar(Stage stage) {
        Label labelTitle = new Label("E-LIB");
        labelTitle.getStyleClass().add("navbar-title");

        Button btnDashboard = new Button("Dashboard");
        btnDashboard.getStyleClass().add("btn-dashboard");
        btnDashboard.setOnAction(e -> {
            DashboardView dashboard = new DashboardView(stage, loggedInUser);
            Scene scene = new Scene(dashboard, 850, 500);
            stage.setScene(scene);
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox navbar = new HBox(20, labelTitle, spacer, btnDashboard);
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPadding(new Insets(10));
        navbar.getStyleClass().add("navbar");

        return navbar;
    }

    private VBox createProfileCard(Stage stage) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(30));
        card.setMaxWidth(500);
        card.getStyleClass().add("profile-card");
        card.setAlignment(Pos.TOP_LEFT);

        Label title = new Label("Profile Anggota");
        title.getStyleClass().add("profile-title");

        // Form field
        Label labelNama = new Label("Nama");
        namaField = new TextField(loggedInUser.getNama());
        namaField.getStyleClass().add("profile-input");

        Label labelNoHp = new Label("Nomor Handphone");
        noHpField = new TextField(String.valueOf(loggedInUser.getNomor_handphone()));
        noHpField.getStyleClass().add("profile-input");

        Label labelAlamat = new Label("Alamat");
        alamatField = new TextField(loggedInUser.getAlamat());
        alamatField.getStyleClass().add("profile-input");

        // Tombol aksi
        updateButton = new Button("Update");
        updateButton.getStyleClass().add("btn-update");
        updateButton.setOnAction(e -> handleUpdate(stage));

        deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("btn-delete");
        deleteButton.setOnAction(e -> handleDelete(stage));

        HBox btn = new HBox(10, updateButton, deleteButton);

        card.getChildren().addAll(
                title,
                labelNama, namaField,
                labelNoHp, noHpField,
                labelAlamat, alamatField,
                btn);

        return card;
    }

    // Handler update data anggota
    private void handleUpdate(Stage stage) {
        loggedInUser.setNama(namaField.getText());
        loggedInUser.setNomor_handphone(Integer.parseInt(noHpField.getText()));
        loggedInUser.setAlamat(alamatField.getText());
        anggotaController.updateAnggota(loggedInUser);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukses");
        alert.setHeaderText(null);
        alert.setContentText("Data anggota berhasil diperbarui!");
        alert.showAndWait();
    }

    // Handler hapus akun
    private void handleDelete(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Hapus Akun");
        alert.setHeaderText(null);
        alert.setContentText("Yakin ingin menghapus akun ini? Data tidak bisa dikembalikan!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = anggotaController.deleteAnggota(loggedInUser.getId_anggota());
            if (success) {
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Berhasil");
                info.setHeaderText(null);
                info.setContentText("Akun berhasil dihapus.");
                info.showAndWait();

                LoginView login = new LoginView(stage);
                Scene loginScene = new Scene(login, 700, 500);
                stage.setScene(loginScene);
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Gagal");
                error.setHeaderText(null);
                error.setContentText("Gagal menghapus akun. Coba lagi.");
                error.showAndWait();
            }
        }
    }
}
