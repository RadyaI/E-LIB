package com.perpus.data;

import com.perpus.model.Buku;
import java.util.*;
import java.io.*;

public class BukuData {
    private ArrayList<Buku> daftarBuku = new ArrayList<>();
    private String filePath = "buku.csv";

    public BukuData() {
        loadFromFile();
    }

    public void tambahBuku(Buku buku) {
        daftarBuku.add(buku);
        saveToFile();
    }

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

    public void deleteBuku(String kode_buku) {
        daftarBuku.removeIf(b -> b.getKode_buku().equals(kode_buku));
        saveToFile();
    }

    public ArrayList<Buku> getDaftarBuku() {
        return daftarBuku;
    }

    public Buku cariBukuId(String id) {
        for (Buku b : daftarBuku) {
            if (b.getKode_buku().equalsIgnoreCase(id)) {
                return b;
            }
        }
        return null;
    }

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
