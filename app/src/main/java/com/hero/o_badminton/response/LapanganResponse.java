package com.hero.o_badminton.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hero.o_badminton.model.Lapangan;

public class LapanganResponse {

    @SerializedName("master")
    @Expose
    private List<Lapangan> master = null;

    public List<Lapangan> getMaster() {
        return master;
    }

    public void setMaster(List<Lapangan> master) {
        this.master = master;
    }

}
