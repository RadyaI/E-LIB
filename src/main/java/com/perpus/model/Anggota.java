package com.perpus.model;

/*
 ==============================================
  FILE       : Anggota.java
  FITUR      : Representasi data anggota perpustakaan
  FUNGSI     : Menyimpan dan memproses data anggota seperti ID, nama, nomor HP, dan alamat.
  DIBUAT OLEH: - Muhammad Radya Iftikhar (202410370110370)
               - Ramanda Bagus Prawobo (202410370110380)
               - Athallah Rasyad Zaidan (202410370110361)
               - Anggara Aribawa Paramesti (202410370110346)
               - Rifky Septian Kusuma (202410370110351)
 ==============================================
*/

public class Anggota {
    private int id_anggota;
    private String nama;
    private int nomor_handphone;
    private String alamat;

    // Konstruktor untuk inisialisasi data anggota
    public Anggota(int id_anggota, String nama, int nomor_handphone, String alamat) {
        this.id_anggota = id_anggota;
        this.nama = nama;
        this.nomor_handphone = nomor_handphone;
        this.alamat = alamat;
    }

    // Getter dan Setter untuk masing-masing atribut
    public int getId_anggota() {
        return id_anggota;
    }

    public void setId_anggota(int id_anggota) {
        this.id_anggota = id_anggota;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getNomor_handphone() {
        return nomor_handphone;
    }

    public void setNomor_handphone(int nomor_handphone) {
        this.nomor_handphone = nomor_handphone;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    // Mengubah objek menjadi format CSV (Comma Separated Values)
    public String toCSV() {
        return id_anggota + "," + escapeCommas(nama) + "," + nomor_handphone + "," + escapeCommas(alamat);
    }

    // Membuat objek Anggota dari data CSV
    public static Anggota fromCSV(String csvLine) {
        String[] parts = csvLine.split(",", -1);
        int id = Integer.parseInt(parts[0]);
        String nama = unescapeCommas(parts[1]);
        int nomor = Integer.parseInt(parts[2]);
        String alamat = unescapeCommas(parts[3]);
        return new Anggota(id, nama, nomor, alamat);
    }

    // Menghindari konflik jika data mengandung koma
    private static String escapeCommas(String input) {
        if (input.contains(",")) {
            return "\"" + input + "\"";
        } else {
            return input;
        }
    }

    // Menghapus tanda kutip dari data yang sebelumnya di-escape
    private static String unescapeCommas(String input) {
        if (input.startsWith("\"") && input.endsWith("\"")) {
            return input.substring(1, input.length() - 1);
        } else {
            return input;
        }
    }
}
