package com.perpus.controllers;

import java.util.ArrayList;
import com.perpus.data.BukuData;
import com.perpus.model.Buku;

public class BukuController {
    private BukuData dataBuku;

    public BukuController() {
        dataBuku = new BukuData();
    }

    public void tambahBuku(String kodeBuku, String judulBuku, String namaPenulis, int tahunTerbit) {
        Buku buku = new Buku(kodeBuku, judulBuku, namaPenulis, tahunTerbit);
        dataBuku.tambahBuku(buku);
    }

    public void updateBuku(Buku buku) {
        dataBuku.updateBuku(buku);
    }

    public void deleteBuku(String kode_buku) {
        dataBuku.deleteBuku(kode_buku);
    }

    public ArrayList<Buku> getSemuaBuku() {
        return dataBuku.getDaftarBuku();
    }

    public Buku cariBuku(String kode) {
        return dataBuku.cariBukuId(kode);
    }
}
