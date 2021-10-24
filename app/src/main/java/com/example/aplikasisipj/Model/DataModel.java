package com.example.aplikasisipj.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class DataModel implements Parcelable {
    private int Id;
    private String Nama, Tanggal, Alamat, Fasilitas, Status, Image;

    protected DataModel(Parcel in) {
        Id = in.readInt();
        Nama = in.readString();
        Tanggal = in.readString();
        Alamat = in.readString();
        Fasilitas = in.readString();
        Status = in.readString();
        Image = in.readString();
    }

    public static final Creator<DataModel> CREATOR = new Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel in) {
            return new DataModel(in);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };

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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Id);
        parcel.writeString(Nama);
        parcel.writeString(Tanggal);
        parcel.writeString(Alamat);
        parcel.writeString(Fasilitas);
        parcel.writeString(Status);
        parcel.writeString(Image);
    }
}
