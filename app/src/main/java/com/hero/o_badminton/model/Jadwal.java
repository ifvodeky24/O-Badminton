package com.hero.o_badminton.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Jadwal implements Parcelable {

    @SerializedName("id_jadwal")
    @Expose
    private String idJadwal;
    @SerializedName("id_lapangan")
    @Expose
    private String idLapangan;
    @SerializedName("hari")
    @Expose
    private String hari;
    @SerializedName("jam")
    @Expose
    private String jam;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("id_gor")
    @Expose
    private String idGor;
    @SerializedName("nama_gor")
    @Expose
    private String namaGor;
    @SerializedName("alamat_gor")
    @Expose
    private String alamatGor;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("jumlah_lapangan")
    @Expose
    private String jumlahLapangan;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("fasilitas")
    @Expose
    private String fasilitas;
    @SerializedName("id_pengelola")
    @Expose
    private String idPengelola;
    @SerializedName("nomor_lapangan")
    @Expose
    private String nomorLapangan;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("jenis")
    @Expose
    private String jenis;

    protected Jadwal(Parcel in) {
        idJadwal = in.readString();
        idLapangan = in.readString();
        hari = in.readString();
        jam = in.readString();
        status = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        idGor = in.readString();
        namaGor = in.readString();
        alamatGor = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        deskripsi = in.readString();
        jumlahLapangan = in.readString();
        foto = in.readString();
        fasilitas = in.readString();
        idPengelola = in.readString();
        nomorLapangan = in.readString();
        harga = in.readString();
        jenis = in.readString();
    }

    public static final Creator<Jadwal> CREATOR = new Creator<Jadwal>() {
        @Override
        public Jadwal createFromParcel(Parcel in) {
            return new Jadwal(in);
        }

        @Override
        public Jadwal[] newArray(int size) {
            return new Jadwal[size];
        }
    };

    public String getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(String idJadwal) {
        this.idJadwal = idJadwal;
    }

    public String getIdLapangan() {
        return idLapangan;
    }

    public void setIdLapangan(String idLapangan) {
        this.idLapangan = idLapangan;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getIdGor() {
        return idGor;
    }

    public void setIdGor(String idGor) {
        this.idGor = idGor;
    }

    public String getNamaGor() {
        return namaGor;
    }

    public void setNamaGor(String namaGor) {
        this.namaGor = namaGor;
    }

    public String getAlamatGor() {
        return alamatGor;
    }

    public void setAlamatGor(String alamatGor) {
        this.alamatGor = alamatGor;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getJumlahLapangan() {
        return jumlahLapangan;
    }

    public void setJumlahLapangan(String jumlahLapangan) {
        this.jumlahLapangan = jumlahLapangan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public String getIdPengelola() {
        return idPengelola;
    }

    public void setIdPengelola(String idPengelola) {
        this.idPengelola = idPengelola;
    }

    public String getNomorLapangan() {
        return nomorLapangan;
    }

    public void setNomorLapangan(String nomorLapangan) {
        this.nomorLapangan = nomorLapangan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idJadwal);
        dest.writeString(idLapangan);
        dest.writeString(hari);
        dest.writeString(jam);
        dest.writeString(status);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(idGor);
        dest.writeString(namaGor);
        dest.writeString(alamatGor);
        dest.writeString(longitude);
        dest.writeString(latitude);
        dest.writeString(deskripsi);
        dest.writeString(jumlahLapangan);
        dest.writeString(foto);
        dest.writeString(fasilitas);
        dest.writeString(idPengelola);
        dest.writeString(nomorLapangan);
        dest.writeString(harga);
        dest.writeString(jenis);
    }
}
