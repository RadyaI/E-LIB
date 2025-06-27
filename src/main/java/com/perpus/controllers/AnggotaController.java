package com.perpus.controllers;

import java.util.ArrayList;
import com.perpus.data.AnggotaData;
import com.perpus.model.Anggota;

public class AnggotaController {
    private AnggotaData dataAnggota;

    public AnggotaController() {
        dataAnggota = new AnggotaData();
    }

    public void tambahAnggota(int id, String nama, int nomor, String alamat) {
        Anggota anggota = new Anggota(id, nama, nomor, alamat);
        dataAnggota.tambahAnggota(anggota);
    }

    public void updateAnggota(Anggota anggota) {
        dataAnggota.updateAnggota(anggota);
    }

    public boolean deleteAnggota(int id) {
        return dataAnggota.deleteAnggota(id);
    }

    public ArrayList<Anggota> getSemuaAnggota() {
        return dataAnggota.getDaftarAnggota();
    }

    public Anggota cariAnggota(int id) {
        return dataAnggota.cariAnggotaById(id);
    }

    public boolean login(int id, String nama) {
        AnggotaData dataAnggota = new AnggotaData();
        for (Anggota a : dataAnggota.getDaftarAnggota()) {
            if (a.getId_anggota() == id && a.getNama().equalsIgnoreCase(nama)) {
                return true;
            }
        }
        return false;
    }

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
