package com.perpus.views;

import com.perpus.model.Anggota;
import com.perpus.model.Buku;
import com.perpus.views.components.AddBukuModal;
import com.perpus.views.components.PeminjamanModal;
import com.perpus.views.components.PinjamBukuModal;
import com.perpus.views.components.UpdateBukuModal;
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

public class DashboardView extends BorderPane {

    private Anggota loggedInUser;
    private TableView<Buku> tableBuku;
    private BukuController bukuController;
    private PeminjamanController peminjamanController;

    public DashboardView(Stage stage, Anggota user) {
        this.loggedInUser = user;
        this.bukuController = new BukuController();
        this.peminjamanController = new PeminjamanController();

        HBox navbar = createNavbar(stage);
        tableBuku = createTableBuku(stage);

        Button btnTambah = new Button("Tambah Buku");
        btnTambah.getStyleClass().add("btn-tambah");
        btnTambah.setOnAction(e -> handleTambahBuku(stage));
        btnTambah.setPrefHeight(35);

        Button btnCekPinjam = new Button("Peminjaman");
        btnCekPinjam.getStyleClass().add("btn-cekpinjam");
        btnCekPinjam.setOnAction(e -> handleCekBuku(stage));
        btnCekPinjam.setPrefHeight(35);

        HBox bottomBar = new HBox(10, btnTambah, btnCekPinjam);
        bottomBar.setPadding(new Insets(10));
        bottomBar.setAlignment(Pos.CENTER_RIGHT);

        if (loggedInUser.getId_anggota() != 123) {
            btnTambah.setVisible(false);
            btnTambah.setManaged(false);
        }

        setTop(navbar);
        setCenter(tableBuku);
        setBottom(bottomBar);

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
            private final Button btnPinjam;
            private final Button btnEdit;
            private final Button btnDelete;
            private final HBox box = new HBox(5);

            {
                btnPinjam = new Button("📖");
                btnEdit = new Button("🖉");
                btnDelete = new Button("🗑");

                int buttonWidth = 35;

                for (Button btn : new Button[] { btnPinjam, btnEdit, btnDelete }) {
                    btn.setMinWidth(buttonWidth);
                    btn.setPrefWidth(buttonWidth);
                    btn.setMaxWidth(buttonWidth);
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

        kodeCol.getStyleClass().add("table-column");
        judulCol.getStyleClass().add("table-column");
        penulisCol.getStyleClass().add("table-column");
        tahunCol.getStyleClass().add("table-column");
        statusCol.getStyleClass().add("table-column");
        aksiCol.getStyleClass().add("table-column");

        kodeCol.setPrefWidth(100);
        judulCol.setPrefWidth(150);
        penulisCol.setPrefWidth(150);
        tahunCol.setPrefWidth(120);
        statusCol.setPrefWidth(100);
        aksiCol.setPrefWidth(200);

        table.getColumns().addAll(kodeCol, judulCol, penulisCol, tahunCol, statusCol, aksiCol);
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        return table;
    }

    private void refreshTable() {
        bukuController = new BukuController();
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

        PinjamBukuModal modal = new PinjamBukuModal(buku, loggedInUser, peminjamanController, bukuController);
        modal.show(stage, this::refreshTable);
    }

    private void handleEdit(Buku buku, Stage stage) {
        System.out.println("Edit buku: " + buku.getJudul_buku());
        UpdateBukuModal modal = new UpdateBukuModal(bukuController);
        modal.show(stage, this::refreshTable, buku);
        refreshTable();
    }

    private void handleDelete(Buku buku) {
        System.out.println("Delete buku: " + buku.getJudul_buku());
        bukuController.deleteBuku(buku.getKode_buku());
        refreshTable();
    }

    private void handleTambahBuku(Stage stage) {
        AddBukuModal modal = new AddBukuModal(bukuController);
        modal.show(stage, this::refreshTable);
    }

    private void handleCekBuku(Stage stage) {
        new PeminjamanModal(loggedInUser, peminjamanController, bukuController).show(stage);
        refreshTable();
    }

    private void handleLogout(Stage stage) {
        System.out.println("Logout");
        com.perpus.views.auth.LoginView loginView = new com.perpus.views.auth.LoginView(stage);
        Scene loginScene = new Scene(loginView, 700, 500);
        stage.setScene(loginScene);
    }

}
