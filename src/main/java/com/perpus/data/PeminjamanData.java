package com.perpus.data;

import com.perpus.model.Peminjaman;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

/*
 ==============================================
  FILE       : PeminjamanData.java
  FITUR      : Manajemen data peminjaman buku
  FUNGSI     : Menambah, mencari, menghapus, dan menyimpan data peminjaman dari dan ke file CSV.
  DIBUAT OLEH: - Muhammad Radya Iftikhar (202410370110370)
               - Ramanda Bagus Prawobo (202410370110380)
               - Athallah Rasyad Zaidan (202410370110361)
               - Anggara Aribawa Paramesti (202410370110346)
               - Rifky Septian Kusuma (202410370110351)
 ==============================================
*/

public class PeminjamanData {
    private static final String FILE_PATH = "peminjaman.csv";

    // Menambahkan data peminjaman ke dalam file
    public void tambahPeminjaman(Peminjaman peminjaman) {
        ArrayList<Peminjaman> list = getDaftarPeminjaman();
        list.add(peminjaman);
        saveAll(list);
    }

    // Mengambil seluruh daftar peminjaman dari file
    public ArrayList<Peminjaman> getDaftarPeminjaman() {
        ArrayList<Peminjaman> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int idPeminjam = Integer.parseInt(data[0]);
                int idAnggota = Integer.parseInt(data[1]);
                String kodeBuku = data[2];
                LocalDate tglPinjam = LocalDate.parse(data[3]);
                LocalDate tglKembali = LocalDate.parse(data[4]);

                list.add(new Peminjaman(idPeminjam, idAnggota, kodeBuku, tglPinjam, tglKembali));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Mencari data peminjaman berdasarkan ID
    public Peminjaman cariPeminjamanId(int id) {
        ArrayList<Peminjaman> list = getDaftarPeminjaman();
        for (Peminjaman p : list) {
            if (p.getId_Peminjam() == id) {
                return p;
            }
        }
        return null;
    }

    // Menghapus data peminjaman berdasarkan ID
    public void deletePeminjaman(int idPeminjam) {
        ArrayList<Peminjaman> list = getDaftarPeminjaman();
        list.removeIf(p -> p.getId_Peminjam() == idPeminjam);
        saveAll(list);
    }

    // Menyimpan ulang seluruh data peminjaman ke file (overwrite)
    private void saveAll(ArrayList<Peminjaman> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            // Menulis header CSV
            pw.println("id_Peminjam,id_Anggota,kode_buku,tanggal_Peminjaman,tanggal_Kembali");
            for (Peminjaman p : list) {
                pw.println(p.getId_Peminjam() + "," +
                        p.getId_Anggota() + "," +
                        p.getKode_buku() + "," +
                        p.getTanggal_Peminjaman() + "," +
                        p.getTanggal_Kembali());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
