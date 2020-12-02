package com.hero.o_badminton.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hero.o_badminton.model.PemesananGetAll;

public class PemesananDetailResponse {

    @SerializedName("master")
    @Expose
    private List<PemesananGetAll> master = null;

    public List<PemesananGetAll> getMaster() {
        return master;
    }

    public void setMaster(List<PemesananGetAll> master) {
        this.master = master;
    }

}
