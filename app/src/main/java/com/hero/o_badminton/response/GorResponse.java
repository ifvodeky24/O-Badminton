package com.hero.o_badminton.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hero.o_badminton.model.Gor;

public class GorResponse {

    @SerializedName("master")
    @Expose
    private List<Gor> master = null;

    public List<Gor> getMaster() {
        return master;
    }

    public void setMaster(List<Gor> master) {
        this.master = master;
    }

}
