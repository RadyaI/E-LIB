package com.perpus.data;

import com.perpus.model.Anggota;
import java.util.*;
import java.io.*;

public class AnggotaData {
    private ArrayList<Anggota> daftarAnggota = new ArrayList<>();
    private String filePath = "anggota.csv";

    public AnggotaData() {
        loadFromFile();
    }

    public void tambahAnggota(Anggota anggota) {
        daftarAnggota.add(anggota);
        saveToFile();
    }

    public void updateAnggota(Anggota anggota) {
        for (int i = 0; i < daftarAnggota.size(); i++) {
            if (daftarAnggota.get(i).getId_anggota() == anggota.getId_anggota()) {
                daftarAnggota.set(i, anggota);
                saveToFile();
                return;
            }
        }
    }

    public boolean deleteAnggota(int id) {
        Anggota target = cariAnggotaById(id);
        if (target != null) {
            daftarAnggota.remove(target);
            saveToFile();
            return true;
        }
        return false;
    }

    public ArrayList<Anggota> getDaftarAnggota() {
        return daftarAnggota;
    }

    public Anggota cariAnggotaById(int id) {
        for (Anggota a : daftarAnggota) {
            if (a.getId_anggota() == id) {
                return a;
            }
        }
        return null;
    }

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
