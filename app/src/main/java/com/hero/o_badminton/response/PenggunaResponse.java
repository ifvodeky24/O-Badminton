package com.hero.o_badminton.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hero.o_badminton.model.Pengguna;

public class PenggunaResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Pengguna data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Pengguna getData() {
        return data;
    }

    public void setData(Pengguna data) {
        this.data = data;
    }

}
