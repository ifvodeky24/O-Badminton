package com.hero.o_badminton.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hero.o_badminton.model.JumlahLapangan;

import java.util.List;

public class SumJadwalResponse {

    @SerializedName("master")
    @Expose
    private List<JumlahLapangan> master = null;

    public List<JumlahLapangan> getMaster() {
        return master;
    }

    public void setMaster(List<JumlahLapangan> master) {
        this.master = master;
    }
}
