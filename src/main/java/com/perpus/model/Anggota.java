package com.perpus.model;

public class Anggota {
    private int id_anggota;
    private String nama;
    private int nomor_handphone;
    private String alamat;

    public Anggota(int id_anggota, String nama, int nomor_handphone, String alamat) {
        this.id_anggota = id_anggota;
        this.nama = nama;
        this.nomor_handphone = nomor_handphone;
        this.alamat = alamat;
    }

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

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setNomor_handphone(int nomor_handphone) {
        this.nomor_handphone = nomor_handphone;
    }

    public String toCSV() {
        return id_anggota + "," + escapeCommas(nama) + "," + nomor_handphone + "," + escapeCommas(alamat);
    }

    public static Anggota fromCSV(String csvLine) {
        String[] parts = csvLine.split(",", -1);
        int id = Integer.parseInt(parts[0]);
        String nama = unescapeCommas(parts[1]);
        int nomor = Integer.parseInt(parts[2]);
        String alamat = unescapeCommas(parts[3]);
        return new Anggota(id, nama, nomor, alamat);
    }

    private static String escapeCommas(String input) {
        if (input.contains(",")) {
            return "\"" + input + "\"";
        } else {
            return input;
        }
    }

    private static String unescapeCommas(String input) {
        if (input.startsWith("\"") && input.endsWith("\"")) {
            return input.substring(1, input.length() - 1);
        } else {
            return input;
        }
    }

}
