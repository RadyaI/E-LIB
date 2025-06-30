package com.perpus.controllers;

import com.perpus.data.PeminjamanData;
import com.perpus.model.Peminjaman;

import java.time.LocalDate;
import java.util.ArrayList;

/*
 ==============================================
  FILE       : PeminjamanController.java
  FITUR      : Kontrol logika transaksi peminjaman buku
  FUNGSI     : Mengatur proses peminjaman, pengembalian, pencarian, dan perhitungan transaksi
  DIBUAT OLEH: - Muhammad Radya Iftikhar (202410370110370)
               - Ramanda Bagus Prawobo (202410370110380)
               - Athallah Rasyad Zaidan (202410370110361)
               - Anggara Aribawa Paramesti (202410370110346)
               - Rifky Septian Kusuma (202410370110351)
 ==============================================
*/

public class PeminjamanController {

    private PeminjamanData dataPeminjaman = new PeminjamanData();

    // Menambahkan data peminjaman baru
    public void tambahPeminjaman(int id_Peminjam, int id_Anggota, String kode_buku,
                                 LocalDate tanggal_Peminjaman, LocalDate tanggal_Kembali) {
        Peminjaman peminjaman = new Peminjaman(
                id_Peminjam,
                id_Anggota,
                kode_buku,
                tanggal_Peminjaman,
                tanggal_Kembali);
        dataPeminjaman.tambahPeminjaman(peminjaman);
    }

    // Mengambil semua data peminjaman
    public ArrayList<Peminjaman> getSemuaPeminjaman() {
        return dataPeminjaman.getDaftarPeminjaman();
    }

    // Mencari data peminjaman berdasarkan ID
    public Peminjaman cariPeminjaman(int id) {
        return dataPeminjaman.cariPeminjamanId(id);
    }

    // Menghapus data peminjaman berdasarkan ID
    public void deletePeminjaman(int idPeminjam) {
        dataPeminjaman.deletePeminjaman(idPeminjam);
    }

    // Menghitung jumlah buku yang sedang dipinjam oleh anggota tertentu
    public int hitungPeminjamanUser(int idAnggota) {
        int count = 0;
        for (Peminjaman p : getSemuaPeminjaman()) {
            if (p.getId_Anggota() == idAnggota) {
                count++;
            }
        }
        return count;
    }
}
