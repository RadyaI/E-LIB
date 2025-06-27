package com.perpus.views.components;

import com.perpus.controllers.BukuController;
import com.perpus.model.Buku;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class UpdateBukuModal {

    private BukuController controller;

    public UpdateBukuModal(BukuController controller) {
        this.controller = controller;
    }

    public void show(Stage owner, Runnable onSuccess, Buku buku) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Update Buku");

        Label lblTitle = new Label("Update Buku");
        lblTitle.getStyleClass().add("modal-title");

        TextField tfKodeBuku = new TextField(buku.getKode_buku());
        tfKodeBuku.setDisable(true);
        tfKodeBuku.getStyleClass().add("modal-textfield");

        TextField tfJudulBuku = new TextField(buku.getJudul_buku());
        tfJudulBuku.getStyleClass().add("modal-textfield");

        TextField tfPenulis = new TextField(buku.getNama_penulis());
        tfPenulis.getStyleClass().add("modal-textfield");

        TextField tfTahun = new TextField(String.valueOf(buku.getTahun_terbit()));
        tfTahun.getStyleClass().add("modal-textfield");

        Button btnSimpan = new Button("Update");
        btnSimpan.getStyleClass().add("modal-button");

        btnSimpan.setOnAction(e -> {
            String judul = tfJudulBuku.getText().trim();
            String penulis = tfPenulis.getText().trim();
            String tahunStr = tfTahun.getText().trim();

            if (judul.isEmpty() || penulis.isEmpty() || tahunStr.isEmpty()) {
                showAlert("Semua field harus diisi!");
                return;
            }

            int tahun;
            try {
                tahun = Integer.parseInt(tahunStr);
            } catch (NumberFormatException ex) {
                showAlert("Tahun Terbit harus berupa angka!");
                return;
            }

            buku.setJudul_buku(judul);
            buku.setNama_penulis(penulis);
            buku.setTahun_terbit(tahun);

            controller.updateBuku(buku);
            onSuccess.run();
            dialog.close();
        });

        VBox layout = new VBox(15, lblTitle, tfKodeBuku, tfJudulBuku, tfPenulis, tfTahun, btnSimpan);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("modal-container");

        Scene scene = new Scene(layout, 400, 450);
        scene.getStylesheets().add(getClass().getResource("/styles/tambahBuku.css").toExternalForm());
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }
}
