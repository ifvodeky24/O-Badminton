package com.hero.o_badminton.response;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hero.o_badminton.model.Pengguna;

public class PenggunaDetailResponse {

    @SerializedName("master")
    @Expose
    private List<Pengguna> master = null;

    public List<Pengguna> getMaster() {
        return master;
    }

    public void setMaster(List<Pengguna> master) {
        this.master = master;
    }

}
