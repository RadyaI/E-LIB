package com.perpus.views.components;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.perpus.controllers.BukuController;
import com.perpus.controllers.PeminjamanController;
import com.perpus.model.Anggota;
import com.perpus.model.Buku;
import com.perpus.model.Peminjaman;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PeminjamanModal {
    private final Anggota user;
    private final PeminjamanController peminjamanController;
    private final BukuController bukuController;

    public PeminjamanModal(Anggota user, PeminjamanController peminjamanController, BukuController bukuController) {
        this.user = user;
        this.peminjamanController = peminjamanController;
        this.bukuController = bukuController;
    }

    public void show(Stage owner) {
        Stage modal = new Stage();
        modal.initOwner(owner);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle("📖 Daftar Peminjaman");

        VBox root = new VBox(20);
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.CENTER);

        Label title = new Label("📚 Buku yang Kamu Pinjam");
        title.setStyle("-fx-font-size: 20px;");
        title.getStyleClass().add("modal-title");

        TableView<Peminjaman> table = new TableView<>();
        table.getStyleClass().add("cool-table");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(300);

        TableColumn<Peminjaman, String> judulCol = new TableColumn<>("Judul Buku");
        judulCol.setCellValueFactory(cellData -> {
            Buku buku = bukuController.cariBuku(cellData.getValue().getKode_buku());
            return new SimpleStringProperty(buku != null ? buku.getJudul_buku() : "Buku tidak ditemukan");
        });

        TableColumn<Peminjaman, LocalDate> tglPinjamCol = new TableColumn<>("Tanggal Pinjam");
        tglPinjamCol.setCellValueFactory(new PropertyValueFactory<>("tanggal_Peminjaman"));

        TableColumn<Peminjaman, LocalDate> tglKembaliCol = new TableColumn<>("Tanggal Kembali");
        tglKembaliCol.setCellValueFactory(new PropertyValueFactory<>("tanggal_Kembali"));

        TableColumn<Peminjaman, String> dendaCol = new TableColumn<>("Denda");
        dendaCol.setCellValueFactory(cellData -> {
            LocalDate tglKembali = cellData.getValue().getTanggal_Kembali();
            long terlambat = ChronoUnit.DAYS.between(tglKembali, LocalDate.now());
            long denda = terlambat > 0 ? terlambat * 2000 : 0;
            return new SimpleStringProperty("Rp " + denda);
        });

        TableColumn<Peminjaman, Void> aksiCol = new TableColumn<>("Aksi");
        aksiCol.setCellFactory(col -> new TableCell<>() {
            private final Button btnKembali = new Button("Kembalikan");

            {
                btnKembali.getStyleClass().add("btn-kembali");
                btnKembali.setOnAction(e -> {
                    Peminjaman peminjaman = getTableView().getItems().get(getIndex());
                    Buku buku = bukuController.cariBuku(peminjaman.getKode_buku());

                    peminjamanController.deletePeminjaman(peminjaman.getId_Peminjam());
                    if (buku != null) {
                        buku.setStatus("Available");
                        bukuController.updateBuku(buku);
                    }

                    getTableView().getItems().remove(peminjaman);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Buku berhasil dikembalikan!");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnKembali);
            }
        });

        table.getColumns().addAll(judulCol, tglPinjamCol, tglKembaliCol, dendaCol, aksiCol);

        List<Peminjaman> semua = peminjamanController.getSemuaPeminjaman();
        ObservableList<Peminjaman> data = FXCollections.observableArrayList();
        for (Peminjaman p : semua) {
            if (p.getId_Anggota() == user.getId_anggota()) {
                data.add(p);
            }
        }
        table.setItems(data);

        root.getChildren().addAll(title, table);

        Scene scene = new Scene(root, 700, 450);
        scene.getStylesheets().add(getClass().getResource("/styles/peminjamanModal.css").toExternalForm());
        modal.setScene(scene);
        modal.showAndWait();
    }
}
