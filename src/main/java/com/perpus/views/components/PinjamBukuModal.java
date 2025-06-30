package com.perpus.views.components;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.perpus.controllers.BukuController;
import com.perpus.controllers.PeminjamanController;
import com.perpus.model.Anggota;
import com.perpus.model.Buku;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 =========================================================
  FILE       : PinjamBukuModal.java
  FITUR      : Modal Form Peminjaman Buku
  FUNGSI     : - Menampilkan form untuk meminjam buku
               - Validasi batas waktu peminjaman (maks 10 hari)
               - Validasi jumlah buku yang sedang dipinjam (maks 3)
               - Menyimpan data peminjaman & ubah status buku
  DIBUAT OLEH: - Muhammad Radya Iftikhar (202410370110370)
               - Ramanda Bagus Prawobo (202410370110380)
               - Athallah Rasyad Zaidan (202410370110361)
               - Anggara Aribawa Paramesti (202410370110346)
               - Rifky Septian Kusuma (202410370110351)
 =========================================================
*/

public class PinjamBukuModal {
    private final Buku buku;
    private final Anggota user;
    private final PeminjamanController peminjamanController;
    private final BukuController bukuController;

    public PinjamBukuModal(Buku buku, Anggota user, PeminjamanController peminjamanController,
                           BukuController bukuController) {
        this.buku = buku;
        this.user = user;
        this.peminjamanController = peminjamanController;
        this.bukuController = bukuController;
    }

    public void show(Stage owner, Runnable onClose) {
        Stage modal = new Stage();
        modal.initOwner(owner);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle("Pinjam Buku");

        VBox card = new VBox(15);
        card.setPadding(new Insets(25));
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("modal-card");

        Label titleLabel = new Label("📚 Pinjam Buku");
        titleLabel.getStyleClass().add("modal-title");

        Label bukuLabel = new Label("Judul Buku: " + buku.getJudul_buku());
        bukuLabel.getStyleClass().add("modal-subtitle");

        Label tglLabel = new Label("Pilih Tanggal Kembali:");
        tglLabel.getStyleClass().add("modal-label");

        DatePicker datePicker = new DatePicker(LocalDate.now().plusDays(7));
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date.isBefore(LocalDate.now()) || date.isAfter(LocalDate.now().plusDays(10))) {
                    setDisable(true);
                }
            }
        });
        datePicker.getStyleClass().add("date-picker");

        Button btnPinjam = new Button("✅ Pinjam Buku");
        btnPinjam.getStyleClass().add("btn-submit");

        btnPinjam.setOnAction(e -> {
            LocalDate today = LocalDate.now();
            LocalDate tglKembali = datePicker.getValue();

            if (tglKembali == null) {
                showWarning("Tanggal kembali wajib diisi.");
                return;
            }

            long daysBetween = ChronoUnit.DAYS.between(today, tglKembali);
            if (daysBetween < 0) {
                showWarning("Tanggal kembali tidak boleh sebelum hari ini.");
                return;
            }
            if (daysBetween > 10) {
                showWarning("Maksimal peminjaman hanya boleh 10 hari.");
                return;
            }

            int jumlahPinjamanUser = peminjamanController.hitungPeminjamanUser(user.getId_anggota());
            if (jumlahPinjamanUser >= 3) {
                showWarning("Kamu sudah meminjam maksimal 3 buku.");
                return;
            }

            int nextId = peminjamanController.getSemuaPeminjaman().size() + 1;
            peminjamanController.tambahPeminjaman(
                    nextId,
                    user.getId_anggota(),
                    buku.getKode_buku(),
                    today,
                    tglKembali);

            buku.setStatus("Unavailable");
            bukuController.updateBuku(buku);

            modal.close();
            if (onClose != null) onClose.run();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Buku berhasil dipinjam!");
            alert.setHeaderText(null);
            alert.showAndWait();
        });

        card.getChildren().addAll(titleLabel, bukuLabel, tglLabel, datePicker, btnPinjam);

        StackPane root = new StackPane(card);
        root.setPadding(new Insets(30));

        Scene scene = new Scene(root, 420, 300);
        scene.getStylesheets().add(getClass().getResource("/styles/pinjamBuku.css").toExternalForm());

        modal.setScene(scene);
        modal.showAndWait();
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
