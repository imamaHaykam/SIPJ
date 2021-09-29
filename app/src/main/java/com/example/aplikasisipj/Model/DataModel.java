package com.example.aplikasisipj.Model;

public class DataModel {
    private int Id;
    private String Nama, Tanggal, Alamat, Fasilitas, Status;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String Nama) {
        this.Nama = Nama;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String Alamat) {
        this.Alamat = Alamat;
    }

    public String getFasilitas() {
        return Fasilitas;
    }

    public void setFasilitas(String Fasilitas) {
        this.Fasilitas = Fasilitas;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
