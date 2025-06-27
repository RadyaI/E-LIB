package com.perpus.data;

import com.perpus.model.Peminjaman;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PeminjamanData {
    private static final String FILE_PATH = "peminjaman.csv";

    public void tambahPeminjaman(Peminjaman peminjaman) {
        ArrayList<Peminjaman> list = getDaftarPeminjaman();
        list.add(peminjaman);
        saveAll(list);
    }

    public ArrayList<Peminjaman> getDaftarPeminjaman() {
        ArrayList<Peminjaman> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine();
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

    public Peminjaman cariPeminjamanId(int id) {
        ArrayList<Peminjaman> list = getDaftarPeminjaman();
        for (Peminjaman p : list) {
            if (p.getId_Peminjam() == id) {
                return p;
            }
        }
        return null;
    }

    public void deletePeminjaman(int idPeminjam) {
        ArrayList<Peminjaman> list = getDaftarPeminjaman();
        list.removeIf(p -> p.getId_Peminjam() == idPeminjam);
        saveAll(list);
    }

    private void saveAll(ArrayList<Peminjaman> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
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
