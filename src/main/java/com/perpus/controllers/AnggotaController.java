package com.perpus.controllers;

import java.util.ArrayList;
import com.perpus.data.AnggotaData;
import com.perpus.model.Anggota;

/*
 ==============================================
  FILE       : AnggotaController.java
  FITUR      : Kontrol logika data anggota
  FUNGSI     : Menghubungkan proses logika antara UI dan data anggota
  DIBUAT OLEH: - Muhammad Radya Iftikhar (202410370110370)
               - Ramanda Bagus Prawobo (202410370110380)
               - Athallah Rasyad Zaidan (202410370110361)
               - Anggara Aribawa Paramesti (202410370110346)
               - Rifky Septian Kusuma (202410370110351)
 ==============================================
*/

public class AnggotaController {
    private AnggotaData dataAnggota;

    // Inisialisasi controller dan data anggota
    public AnggotaController() {
        dataAnggota = new AnggotaData();
    }

    // Menambahkan anggota baru
    public void tambahAnggota(int id, String nama, int nomor, String alamat) {
        Anggota anggota = new Anggota(id, nama, nomor, alamat);
        dataAnggota.tambahAnggota(anggota);
    }

    // Memperbarui data anggota
    public void updateAnggota(Anggota anggota) {
        dataAnggota.updateAnggota(anggota);
    }

    // Menghapus anggota berdasarkan ID
    public boolean deleteAnggota(int id) {
        return dataAnggota.deleteAnggota(id);
    }

    // Mengambil semua anggota dari data
    public ArrayList<Anggota> getSemuaAnggota() {
        return dataAnggota.getDaftarAnggota();
    }

    // Mencari anggota berdasarkan ID
    public Anggota cariAnggota(int id) {
        return dataAnggota.cariAnggotaById(id);
    }

    // Verifikasi login anggota berdasarkan ID dan nama
    public boolean login(int id, String nama) {
        AnggotaData dataAnggota = new AnggotaData();
        for (Anggota a : dataAnggota.getDaftarAnggota()) {
            if (a.getId_anggota() == id && a.getNama().equalsIgnoreCase(nama)) {
                return true;
            }
        }
        return false;
    }

    // Registrasi anggota baru jika ID belum terdaftar
    public boolean register(int id, String nama, int noHp, String alamat) {
        Anggota existing = dataAnggota.cariAnggotaById(id);
        if (existing != null) {
            return false;
        }
        Anggota newAnggota = new Anggota(id, nama, noHp, alamat);
        dataAnggota.tambahAnggota(newAnggota);
        return true;
    }
}
