package com.hero.o_badminton.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hero.o_badminton.model.Jadwal;

public class JadwalResponse {

    @SerializedName("master")
    @Expose
    private List<Jadwal> master = null;

    public List<Jadwal> getMaster() {
        return master;
    }

    public void setMaster(List<Jadwal> master) {
        this.master = master;
    }

}
