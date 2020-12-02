package com.hero.o_badminton.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Topup {

    @SerializedName("id_topup")
    @Expose
    private String idTopup;
    @SerializedName("id_pengguna")
    @Expose
    private String idPengguna;
    @SerializedName("jumlah")
    @Expose
    private String jumlah;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("nama_lengkap")
    @Expose
    private String namaLengkap;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public String getIdTopup() {
        return idTopup;
    }

    public void setIdTopup(String idTopup) {
        this.idTopup = idTopup;
    }

    public String getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(String idPengguna) {
        this.idPengguna = idPengguna;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
