package com.hero.o_badminton.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hero.o_badminton.model.Pengelola;

public class PengelolaDetailResponse {

    @SerializedName("master")
    @Expose
    private List<Pengelola> master = null;

    public List<Pengelola> getMaster() {
        return master;
    }

    public void setMaster(List<Pengelola> master) {
        this.master = master;
    }

}
