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
                Alert alert = new Alert(Alert.AlertType.WARNING, "Tanggal kembali wajib diisi.");
                alert.setHeaderText(null);
                alert.showAndWait();
                return;
            }

            long daysBetween = ChronoUnit.DAYS.between(today, tglKembali);
            if (daysBetween < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Tanggal kembali tidak boleh sebelum hari ini.");
                alert.setHeaderText(null);
                alert.showAndWait();
                return;
            }
            if (daysBetween > 10) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Maksimal peminjaman hanya boleh 10 hari.");
                alert.setHeaderText(null);
                alert.showAndWait();
                return;
            }

            int jumlahPinjamanUser = peminjamanController.hitungPeminjamanUser(user.getId_anggota());
            if (jumlahPinjamanUser >= 3) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Kamu sudah meminjam maksimal 3 buku.");
                alert.setHeaderText(null);
                alert.showAndWait();
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
            if (onClose != null) {
                onClose.run();
            }

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
}
