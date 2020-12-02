package com.hero.o_badminton.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hero.o_badminton.model.Topup;

public class GetTopupResponse {

    @SerializedName("master")
    @Expose
    private List<Topup> master = null;

    public List<Topup> getMaster() {
        return master;
    }

    public void setMaster(List<Topup> master) {
        this.master = master;
    }

}
