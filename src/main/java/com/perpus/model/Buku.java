package com.perpus.model;

public class Buku {
    private String kode_buku;
    private String judul_buku;
    private String nama_penulis;
    private String status;
    private int tahun_terbit;

    public Buku(String kode_buku, String judul_buku, String nama_penulis, int tahun_terbit) {
        this.kode_buku = kode_buku;
        this.judul_buku = judul_buku;
        this.nama_penulis = nama_penulis;
        this.status = "Available";
        this.tahun_terbit = tahun_terbit;
    }
    
    public String getJudul_buku() {
        return judul_buku;
    }

    public void setJudul_buku(String judul_buku) {
        this.judul_buku = judul_buku;
    }

    public String getKode_buku() {
        return kode_buku;
    }

    public void setKode_buku(String kode_buku) {
        this.kode_buku = kode_buku;
    }

    public int getTahun_terbit() {
        return tahun_terbit;
    }

    public void setTahun_terbit(int tahun_terbit) {
        this.tahun_terbit = tahun_terbit;
    }

    public String getNama_penulis() {
        return nama_penulis;
    }

    public void setNama_penulis(String nama_penulis) {
        this.nama_penulis = nama_penulis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toCSV() {
        return escapeCommas(kode_buku) + "," +
               escapeCommas(judul_buku) + "," +
               escapeCommas(nama_penulis) + "," +
               status + "," +
               tahun_terbit;
    }

    public static Buku fromCSV(String csvLine) {
        String[] parts = csvLine.split(",", -1);
        String kode_buku = unescapeCommas(parts[0]);
        String judul_buku = unescapeCommas(parts[1]);
        String nama_penulis = unescapeCommas(parts[2]);
        String status = parts[3];
        int tahun_terbit = Integer.parseInt(parts[4]);

        Buku buku = new Buku(kode_buku, judul_buku, nama_penulis, tahun_terbit);
        buku.setStatus(status);
        return buku;
    }

    private static String escapeCommas(String input) {
        if (input.contains(",")) {
            return "\"" + input + "\"";
        }
        return input;
    }

    private static String unescapeCommas(String input) {
        if (input.startsWith("\"") && input.endsWith("\"")) {
            return input.substring(1, input.length() - 1);
        }
        return input;
    }
}
