package com.perpus.data;

import com.perpus.model.Anggota;
import java.util.*;
import java.io.*;

/*
 ==============================================
  FILE       : AnggotaData.java
  FITUR      : Manajemen data anggota perpustakaan
  FUNGSI     : Menambah, memperbarui, menghapus, mencari, dan menyimpan data anggota ke file CSV.
  DIBUAT OLEH: - Muhammad Radya Iftikhar (202410370110370)
               - Ramanda Bagus Prawobo (202410370110380)
               - Athallah Rasyad Zaidan (202410370110361)
               - Anggara Aribawa Paramesti (202410370110346)
               - Rifky Septian Kusuma (202410370110351)
 ==============================================
*/

public class AnggotaData {
    private ArrayList<Anggota> daftarAnggota = new ArrayList<>();
    private String filePath = "anggota.csv";

    // Konstruktor: langsung memuat data dari file saat objek dibuat
    public AnggotaData() {
        loadFromFile();
    }

    // Menambahkan anggota baru ke dalam daftar dan menyimpan ke file
    public void tambahAnggota(Anggota anggota) {
        daftarAnggota.add(anggota);
        saveToFile();
    }

    // Memperbarui data anggota berdasarkan ID
    public void updateAnggota(Anggota anggota) {
        for (int i = 0; i < daftarAnggota.size(); i++) {
            if (daftarAnggota.get(i).getId_anggota() == anggota.getId_anggota()) {
                daftarAnggota.set(i, anggota);
                saveToFile();
                return;
            }
        }
    }

    // Menghapus anggota berdasarkan ID
    public boolean deleteAnggota(int id) {
        Anggota target = cariAnggotaById(id);
        if (target != null) {
            daftarAnggota.remove(target);
            saveToFile();
            return true;
        }
        return false;
    }

    // Mengambil semua data anggota
    public ArrayList<Anggota> getDaftarAnggota() {
        return daftarAnggota;
    }

    // Mencari anggota berdasarkan ID
    public Anggota cariAnggotaById(int id) {
        for (Anggota a : daftarAnggota) {
            if (a.getId_anggota() == id) {
                return a;
            }
        }
        return null;
    }

    // Membaca data dari file CSV
    private void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Anggota anggota = Anggota.fromCSV(line);
                daftarAnggota.add(anggota);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Menyimpan data anggota ke file CSV
    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Anggota anggota : daftarAnggota) {
                bw.write(anggota.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
