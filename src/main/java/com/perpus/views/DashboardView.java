package com.perpus.views;

import com.perpus.model.Anggota;
import com.perpus.model.Buku;
import com.perpus.views.components.*;
import com.perpus.controllers.BukuController;
import com.perpus.controllers.PeminjamanController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/*
 =========================================================
  FILE       : DashboardView.java
  FITUR      : Halaman utama setelah login
  FUNGSI     : - Menampilkan daftar buku yang tersedia
               - Fitur pinjam, tambah, edit, dan hapus buku
               - Navigasi ke halaman profil dan logout
               - Hanya admin (ID 123) yang bisa edit dan tambah buku
  DIBUAT OLEH: - Muhammad Radya Iftikhar (202410370110370)
               - Ramanda Bagus Prawobo (202410370110380)
               - Athallah Rasyad Zaidan (202410370110361)
               - Anggara Aribawa Paramesti (202410370110346)
               - Rifky Septian Kusuma (202410370110351)
 =========================================================
*/

public class DashboardView extends BorderPane {

    private Anggota loggedInUser;
    private TableView<Buku> tableBuku;
    private BukuController bukuController;
    private PeminjamanController peminjamanController;

    public DashboardView(Stage stage, Anggota user) {
        this.loggedInUser = user;
        this.bukuController = new BukuController();
        this.peminjamanController = new PeminjamanController();

        setTop(createNavbar(stage));
        tableBuku = createTableBuku(stage);
        setCenter(tableBuku);
        setBottom(createBottomBar(stage));

        getStylesheets().add(getClass().getResource("/styles/dashboard.css").toExternalForm());

        refreshTable();
    }

    private HBox createNavbar(Stage stage) {
        Label labelTitle = new Label("E-LIB");
        labelTitle.getStyleClass().add("navbar-title");

        Button btnLogout = new Button("Logout");
        btnLogout.getStyleClass().add("btn-logout");
        btnLogout.setOnAction(e -> handleLogout(stage));

        Label profileLabel = new Label("PROFILE");
        profileLabel.getStyleClass().add("navbar-profile");
        profileLabel.setOnMouseClicked(e -> {
            AnggotaView anggotaView = new AnggotaView(stage, loggedInUser);
            Scene scene = new Scene(anggotaView, 700, 500);
            stage.setScene(scene);
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox navbar = new HBox(20, labelTitle, spacer, btnLogout, profileLabel);
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPadding(new Insets(10, 30, 10, 30));
        navbar.getStyleClass().add("navbar");

        return navbar;
    }

    private HBox createBottomBar(Stage stage) {
        Button btnTambah = new Button("Tambah Buku");
        btnTambah.getStyleClass().add("btn-tambah");
        btnTambah.setOnAction(e -> handleTambahBuku(stage));
        btnTambah.setPrefHeight(35);

        Button btnCekPinjam = new Button("Peminjaman");
        btnCekPinjam.getStyleClass().add("btn-cekpinjam");
        btnCekPinjam.setOnAction(e -> handleCekBuku(stage));
        btnCekPinjam.setPrefHeight(35);

        if (loggedInUser.getId_anggota() != 123) {
            btnTambah.setVisible(false);
            btnTambah.setManaged(false);
        }

        HBox bottomBar = new HBox(10, btnTambah, btnCekPinjam);
        bottomBar.setPadding(new Insets(10));
        bottomBar.setAlignment(Pos.CENTER_RIGHT);

        return bottomBar;
    }

    private TableView<Buku> createTableBuku(Stage stage) {
        TableView<Buku> table = new TableView<>();
        table.getStyleClass().add("modern-table");

        TableColumn<Buku, String> kodeCol = new TableColumn<>("Kode Buku");
        kodeCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("kode_buku"));

        TableColumn<Buku, String> judulCol = new TableColumn<>("Judul Buku");
        judulCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("judul_buku"));

        TableColumn<Buku, String> penulisCol = new TableColumn<>("Penulis");
        penulisCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nama_penulis"));

        TableColumn<Buku, Integer> tahunCol = new TableColumn<>("Tahun Terbit");
        tahunCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("tahun_terbit"));

        TableColumn<Buku, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("status"));

        TableColumn<Buku, Void> aksiCol = new TableColumn<>("Aksi");
        aksiCol.setCellFactory(col -> new TableCell<>() {
            private final Button btnPinjam = new Button("📖");
            private final Button btnEdit = new Button("🖉");
            private final Button btnDelete = new Button("🗑");
            private final HBox box = new HBox(5);

            {
                for (Button btn : new Button[] { btnPinjam, btnEdit, btnDelete }) {
                    btn.setPrefWidth(35);
                }

                btnPinjam.getStyleClass().add("btn-pinjam");
                btnEdit.getStyleClass().add("btn-edit");
                btnDelete.getStyleClass().add("btn-delete");

                btnPinjam.setOnAction(e -> handlePinjam(getTableView().getItems().get(getIndex()), stage));
                btnEdit.setOnAction(e -> handleEdit(getTableView().getItems().get(getIndex()), stage));
                btnDelete.setOnAction(e -> handleDelete(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    box.getChildren().clear();
                    Buku buku = getTableView().getItems().get(getIndex());

                    if ("Available".equalsIgnoreCase(buku.getStatus())) {
                        box.getChildren().add(btnPinjam);
                    }

                    if (loggedInUser.getId_anggota() == 123) {
                        box.getChildren().addAll(btnEdit, btnDelete);
                    }

                    setGraphic(box);
                }
            }
        });

        table.getColumns().addAll(kodeCol, judulCol, penulisCol, tahunCol, statusCol, aksiCol);
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        return table;
    }

    private void refreshTable() {
        ObservableList<Buku> list = FXCollections.observableArrayList(bukuController.getSemuaBuku());
        tableBuku.setItems(list);
    }

    private void handlePinjam(Buku buku, Stage stage) {
        int userId = loggedInUser.getId_anggota();
        int jumlahPinjaman = peminjamanController.hitungPeminjamanUser(userId);

        if (jumlahPinjaman >= 3) {
            Alert a = new Alert(AlertType.WARNING);
            a.setHeaderText(null);
            a.setContentText("Kamu cuma boleh pinjam maksimal 3 buku");
            a.showAndWait();
            return;
        }

        new PinjamBukuModal(buku, loggedInUser, peminjamanController, bukuController).show(stage, this::refreshTable);
    }

    private void handleEdit(Buku buku, Stage stage) {
        new UpdateBukuModal(bukuController).show(stage, this::refreshTable, buku);
    }

    private void handleDelete(Buku buku) {
        bukuController.deleteBuku(buku.getKode_buku());
        refreshTable();
    }

    private void handleTambahBuku(Stage stage) {
        new AddBukuModal(bukuController).show(stage, this::refreshTable);
    }

    private void handleCekBuku(Stage stage) {
        new PeminjamanModal(loggedInUser, peminjamanController, bukuController).show(stage);
        refreshTable();
    }

    private void handleLogout(Stage stage) {
        Scene loginScene = new Scene(new com.perpus.views.auth.LoginView(stage), 700, 500);
        stage.setScene(loginScene);
    }
}
