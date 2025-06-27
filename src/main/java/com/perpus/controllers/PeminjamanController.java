package com.perpus.controllers;

import com.perpus.data.PeminjamanData;
import com.perpus.model.Peminjaman;

import java.time.LocalDate;
import java.util.ArrayList;

public class PeminjamanController {

    private PeminjamanData dataPeminjaman = new PeminjamanData();

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

    public ArrayList<Peminjaman> getSemuaPeminjaman() {
        return dataPeminjaman.getDaftarPeminjaman();
    }

    public Peminjaman cariPeminjaman(int id) {
        return dataPeminjaman.cariPeminjamanId(id);
    }

    public void deletePeminjaman(int idPeminjam) {
        dataPeminjaman.deletePeminjaman(idPeminjam);
    }

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
