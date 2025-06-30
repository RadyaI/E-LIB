package com.perpus.data;

import com.perpus.model.Buku;
import java.util.*;
import java.io.*;

/*
 ==============================================
  FILE       : BukuData.java
  FITUR      : Manajemen data buku perpustakaan
  FUNGSI     : Menambah, menghapus, memperbarui, mencari, dan menyimpan data buku ke file CSV.
  DIBUAT OLEH: - Muhammad Radya Iftikhar (202410370110370)
               - Ramanda Bagus Prawobo (202410370110380)
               - Athallah Rasyad Zaidan (202410370110361)
               - Anggara Aribawa Paramesti (202410370110346)
               - Rifky Septian Kusuma (202410370110351)
 ==============================================
*/

public class BukuData {
    private ArrayList<Buku> daftarBuku = new ArrayList<>();
    private String filePath = "buku.csv";

    public BukuData() {
        loadFromFile();
    }

    // Menambahkan buku baru
    public void tambahBuku(Buku buku) {
        daftarBuku.add(buku);
        saveToFile();
    }

    // Memperbarui data buku berdasarkan kode
    public void updateBuku(Buku bukuBaru) {
        for (int i = 0; i < daftarBuku.size(); i++) {
            Buku b = daftarBuku.get(i);
            if (b.getKode_buku().equals(bukuBaru.getKode_buku())) {
                daftarBuku.set(i, bukuBaru);
                saveToFile();
                return;
            }
        }
    }

    // Menghapus buku berdasarkan kode
    public void deleteBuku(String kode_buku) {
        daftarBuku.removeIf(b -> b.getKode_buku().equals(kode_buku));
        saveToFile();
    }

    // Mengembalikan daftar seluruh buku
    public ArrayList<Buku> getDaftarBuku() {
        return daftarBuku;
    }

    // Mencari buku berdasarkan kode
    public Buku cariBukuId(String id) {
        for (Buku b : daftarBuku) {
            if (b.getKode_buku().equalsIgnoreCase(id)) {
                return b;
            }
        }
        return null;
    }

    // Memuat data buku dari file CSV
    private void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Buku buku = Buku.fromCSV(line);
                daftarBuku.add(buku);
            }
        } catch (IOException e) {
            System.out.println("Error reading buku file: " + e.getMessage());
        }
    }

    // Menyimpan semua data buku ke file CSV
    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Buku buku : daftarBuku) {
                bw.write(buku.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing buku file: " + e.getMessage());
        }
    }
}
