package com.hero.o_badminton.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gor {

    @SerializedName("id_gor")
    @Expose
    private Integer idGor;
    @SerializedName("nama_gor")
    @Expose
    private String namaGor;
    @SerializedName("alamat_gor")
    @Expose
    private String alamatGor;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("jumlah_lapangan")
    @Expose
    private Integer jumlahLapangan;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("fasilitas")
    @Expose
    private String fasilitas;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("id_pengelola")
    @Expose
    private Integer idPengelola;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public Integer getIdGor() {
        return idGor;
    }

    public void setIdGor(Integer idGor) {
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

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Integer getJumlahLapangan() {
        return jumlahLapangan;
    }

    public void setJumlahLapangan(Integer jumlahLapangan) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdPengelola() {
        return idPengelola;
    }

    public void setIdPengelola(Integer idPengelola) {
        this.idPengelola = idPengelola;
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

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;



}
