package com.perpus.model;

import java.time.LocalDate;

public class Peminjaman {
    private int id_Peminjam;
    private int id_Anggota;
    private String kode_buku;
    private LocalDate tanggal_Peminjaman;
    private LocalDate tanggal_Kembali;

    public Peminjaman(int id_Peminjam, int id_Anggota, String kode_buku, LocalDate tanggal_Peminjaman, LocalDate tanggal_Kembali) {
        this.id_Peminjam = id_Peminjam;
        this.id_Anggota = id_Anggota;
        this.kode_buku = kode_buku;
        this.tanggal_Peminjaman = tanggal_Peminjaman;
        this.tanggal_Kembali = tanggal_Kembali;
    }

    public int getId_Peminjam() {
        return id_Peminjam;
    }

    public void setId_Peminjam(int id_Peminjam) {
        this.id_Peminjam = id_Peminjam;
    }

    public int getId_Anggota() {
        return id_Anggota;
    }

    public void setId_Anggota(int id_Anggota) {
        this.id_Anggota = id_Anggota;
    }

    public String getKode_buku() {
        return kode_buku;
    }

    public void setKode_buku(String kode_buku) {
        this.kode_buku = kode_buku;
    }

    public LocalDate getTanggal_Peminjaman() {
        return tanggal_Peminjaman;
    }

    public void setTanggal_Peminjaman(LocalDate tanggal_Peminjaman) {
        this.tanggal_Peminjaman = tanggal_Peminjaman;
    }

    public LocalDate getTanggal_Kembali() {
        return tanggal_Kembali;
    }

    public void setTanggal_Kembali(LocalDate tanggal_Kembali) {
        this.tanggal_Kembali = tanggal_Kembali;
    }
}
