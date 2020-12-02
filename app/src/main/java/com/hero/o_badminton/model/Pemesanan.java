package com.hero.o_badminton.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pemesanan {

    @SerializedName("id_pengguna")
    @Expose
    private String idPengguna;
    @SerializedName("id_lapangan")
    @Expose
    private String idLapangan;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("id_jadwal")
    @Expose
    private String idJadwal;
    @SerializedName("id_pemesanan")
    @Expose
    private Integer idPemesanan;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public Pemesanan(String idPengguna, String idLapangan, String status, String idJadwal, Integer idPemesanan, String createdAt, String updatedAt) {
        this.idPengguna = idPengguna;
        this.idLapangan = idLapangan;
        this.status = status;
        this.idJadwal = idJadwal;
        this.idPemesanan = idPemesanan;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(String idPengguna) {
        this.idPengguna = idPengguna;
    }

    public String getIdLapangan() {
        return idLapangan;
    }

    public void setIdLapangan(String idLapangan) {
        this.idLapangan = idLapangan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(String idJadwal) {
        this.idJadwal = idJadwal;
    }

    public Integer getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(Integer idPemesanan) {
        this.idPemesanan = idPemesanan;
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

}
