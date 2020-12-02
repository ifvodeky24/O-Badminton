package com.hero.o_badminton.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JumlahLapangan {

    @SerializedName("jumlah_lapangan")
    @Expose
    private String jumlahLapangan;

    public String getJumlahLapangan() {
        return jumlahLapangan;
    }

    public void setJumlahLapangan(String jumlahLapangan) {
        this.jumlahLapangan = jumlahLapangan;
    }
}
