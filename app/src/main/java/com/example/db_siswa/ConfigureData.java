package com.example.db_siswa;

public class ConfigureData {
    private String id, nik, name, alamat, kota, jk, umur;

    public ConfigureData() {
    }

    public ConfigureData(String id, String nik, String nama, String alamat, String kota, String jk, String umur){
        this.id = id;
        this.nik = nik;
        this.name = nama;
        this.alamat = alamat;
        this.kota = kota;
        this.jk = jk;
        this.umur = umur;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) { this.nik = nik; }

    public String getName() { return name; }

    public void setName(String nama) { this.name = nama; }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) { this.kota = kota; }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) { this.jk = jk; }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) { this.umur = umur; }


}

