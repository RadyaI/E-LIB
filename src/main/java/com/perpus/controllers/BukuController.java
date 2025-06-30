package com.perpus.controllers;

import java.util.ArrayList;
import com.perpus.data.BukuData;
import com.perpus.model.Buku;

/*
 ==============================================
  FILE       : BukuController.java
  FITUR      : Kontrol logika data buku
  FUNGSI     : Mengelola proses antara UI dan data buku
  DIBUAT OLEH: - Muhammad Radya Iftikhar (202410370110370)
               - Ramanda Bagus Prawobo (202410370110380)
               - Athallah Rasyad Zaidan (202410370110361)
               - Anggara Aribawa Paramesti (202410370110346)
               - Rifky Septian Kusuma (202410370110351)
 ==============================================
*/

public class BukuController {
    private BukuData dataBuku;

    public BukuController() {
        dataBuku = new BukuData();
    }

    // Menambahkan buku baru
    public void tambahBuku(String kodeBuku, String judulBuku, String namaPenulis, int tahunTerbit) {
        Buku buku = new Buku(kodeBuku, judulBuku, namaPenulis, tahunTerbit);
        dataBuku.tambahBuku(buku);
    }

    // Memperbarui data buku
    public void updateBuku(Buku buku) {
        dataBuku.updateBuku(buku);
    }

    // Menghapus buku berdasarkan kode
    public void deleteBuku(String kode_buku) {
        dataBuku.deleteBuku(kode_buku);
    }

    // Mengambil semua data buku
    public ArrayList<Buku> getSemuaBuku() {
        return dataBuku.getDaftarBuku();
    }

    // Mencari buku berdasarkan kode
    public Buku cariBuku(String kode) {
        return dataBuku.cariBukuId(kode);
    }
}
