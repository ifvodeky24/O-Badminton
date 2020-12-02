package com.hero.o_badminton.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lapangan {

    @SerializedName("id_lapangan")
    @Expose
    private String idLapangan;
    @SerializedName("id_gor")
    @Expose
    private String idGor;
    @SerializedName("nomor_lapangan")
    @Expose
    private String nomorLapangan;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("jenis")
    @Expose
    private String jenis;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("nama_gor")
    @Expose
    private String namaGor;
    @SerializedName("id_pengelola")
    @Expose
    private String idPengelola;

    public String getIdLapangan() {
        return idLapangan;
    }

    public void setIdLapangan(String idLapangan) {
        this.idLapangan = idLapangan;
    }

    public String getIdGor() {
        return idGor;
    }

    public void setIdGor(String idGor) {
        this.idGor = idGor;
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

    public String getNamaGor() {
        return namaGor;
    }

    public void setNamaGor(String namaGor) {
        this.namaGor = namaGor;
    }

    public String getIdPengelola() {
        return idPengelola;
    }

    public void setIdPengelola(String idPengelola) {
        this.idPengelola = idPengelola;
    }

}
