package com.perpus.views.components;

import com.perpus.controllers.BukuController;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class AddBukuModal {

    private BukuController controller;

    public AddBukuModal(BukuController controller) {
        this.controller = controller;
    }

    public void show(Stage owner, Runnable onSuccess) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Tambah Buku");

        Label lblTitle = new Label("Tambah Buku");
        lblTitle.getStyleClass().add("modal-title");

        TextField tfKodeBuku = new TextField();
        tfKodeBuku.setPromptText("Kode Buku");
        tfKodeBuku.getStyleClass().add("modal-textfield");

        TextField tfJudulBuku = new TextField();
        tfJudulBuku.setPromptText("Judul Buku");
        tfJudulBuku.getStyleClass().add("modal-textfield");

        TextField tfPenulis = new TextField();
        tfPenulis.setPromptText("Nama Penulis");
        tfPenulis.getStyleClass().add("modal-textfield");

        TextField tfTahun = new TextField();
        tfTahun.setPromptText("Tahun Terbit");
        tfTahun.getStyleClass().add("modal-textfield");

        Button btnSimpan = new Button("Simpan");
        btnSimpan.getStyleClass().add("modal-button");

        btnSimpan.setOnAction(e -> {
            String kode = tfKodeBuku.getText().trim();
            String judul = tfJudulBuku.getText().trim();
            String penulis = tfPenulis.getText().trim();
            String tahunStr = tfTahun.getText().trim();

            if (kode.isEmpty() || judul.isEmpty() || penulis.isEmpty() || tahunStr.isEmpty()) {
                showAlert("Semua field harus diisi!");
                return;
            }

            if (controller.cariBuku(kode) != null) {
                showAlert("Kode buku sudah terdaftar!");
                return;
            }

            int tahun;
            try {
                tahun = Integer.parseInt(tahunStr);
            } catch (NumberFormatException ex) {
                showAlert("Tahun Terbit harus berupa angka!");
                return;
            }

            controller.tambahBuku(kode, judul, penulis, tahun);
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
