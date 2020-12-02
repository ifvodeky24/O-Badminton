package com.hero.o_badminton.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PemesananGetAll {

    @SerializedName("id_pemesanan")
    @Expose
    private String idPemesanan;
    @SerializedName("id_pengguna")
    @Expose
    private String idPengguna;
    @SerializedName("id_lapangan")
    @Expose
    private String idLapangan;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("id_jadwal")
    @Expose
    private String idJadwal;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("username_pengguna")
    @Expose
    private String usernamePengguna;
    @SerializedName("nama_lengkap_pengguna")
    @Expose
    private String namaLengkapPengguna;
    @SerializedName("email_pengguna")
    @Expose
    private String emailPengguna;
    @SerializedName("alamat_pengguna")
    @Expose
    private String alamatPengguna;
    @SerializedName("no_hp_pengguna")
    @Expose
    private String noHpPengguna;
    @SerializedName("foto_pengguna")
    @Expose
    private String fotoPengguna;
    @SerializedName("no_rekening")
    @Expose
    private String noRekening;
    @SerializedName("nama_bank")
    @Expose
    private String namaBank;
    @SerializedName("total_saldo")
    @Expose
    private String totalSaldo;
    @SerializedName("id_gor")
    @Expose
    private String idGor;
    @SerializedName("nomor_lapangan")
    @Expose
    private String nomorLapangan;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("jenis")
    @Expose
    private String jenis;
    @SerializedName("nama_gor")
    @Expose
    private String namaGor;
    @SerializedName("alamat_gor")
    @Expose
    private String alamatGor;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("jumlah_lapangan")
    @Expose
    private String jumlahLapangan;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("fasilitas")
    @Expose
    private String fasilitas;
    @SerializedName("id_pengelola")
    @Expose
    private String idPengelola;
    @SerializedName("username_pengelola")
    @Expose
    private String usernamePengelola;
    @SerializedName("email_pengelola")
    @Expose
    private String emailPengelola;
    @SerializedName("nama_lengkap_pengelola")
    @Expose
    private String namaLengkapPengelola;
    @SerializedName("alamat_pengelola")
    @Expose
    private String alamatPengelola;
    @SerializedName("no_hp_pengelola")
    @Expose
    private String noHpPengelola;
    @SerializedName("foto_pengelola")
    @Expose
    private String fotoPengelola;
    @SerializedName("hari")
    @Expose
    private String hari;
    @SerializedName("jam")
    @Expose
    private String jam;

    public String getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(String idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public String getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(String idPengguna) {
        this.idPengguna = idPengguna;
    }

    public String getIdLapangan() {
        return idLapangan;
    }

    public void setIdLapangan(String idLapangan) {
        this.idLapangan = idLapangan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(String idJadwal) {
        this.idJadwal = idJadwal;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUsernamePengguna() {
        return usernamePengguna;
    }

    public void setUsernamePengguna(String usernamePengguna) {
        this.usernamePengguna = usernamePengguna;
    }

    public String getNamaLengkapPengguna() {
        return namaLengkapPengguna;
    }

    public void setNamaLengkapPengguna(String namaLengkapPengguna) {
        this.namaLengkapPengguna = namaLengkapPengguna;
    }

    public String getEmailPengguna() {
        return emailPengguna;
    }

    public void setEmailPengguna(String emailPengguna) {
        this.emailPengguna = emailPengguna;
    }

    public String getAlamatPengguna() {
        return alamatPengguna;
    }

    public void setAlamatPengguna(String alamatPengguna) {
        this.alamatPengguna = alamatPengguna;
    }

    public String getNoHpPengguna() {
        return noHpPengguna;
    }

    public void setNoHpPengguna(String noHpPengguna) {
        this.noHpPengguna = noHpPengguna;
    }

    public String getFotoPengguna() {
        return fotoPengguna;
    }

    public void setFotoPengguna(String fotoPengguna) {
        this.fotoPengguna = fotoPengguna;
    }

    public String getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(String noRekening) {
        this.noRekening = noRekening;
    }

    public String getNamaBank() {
        return namaBank;
    }

    public void setNamaBank(String namaBank) {
        this.namaBank = namaBank;
    }

    public String getTotalSaldo() {
        return totalSaldo;
    }

    public void setTotalSaldo(String totalSaldo) {
        this.totalSaldo = totalSaldo;
    }

    public String getIdGor() {
        return idGor;
    }

    public void setIdGor(String idGor) {
        this.idGor = idGor;
    }

    public String getNomorLapangan() {
        return nomorLapangan;
    }

    public void setNomorLapangan(String nomorLapangan) {
        this.nomorLapangan = nomorLapangan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getNamaGor() {
        return namaGor;
    }

    public void setNamaGor(String namaGor) {
        this.namaGor = namaGor;
    }

    public String getAlamatGor() {
        return alamatGor;
    }

    public void setAlamatGor(String alamatGor) {
        this.alamatGor = alamatGor;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getJumlahLapangan() {
        return jumlahLapangan;
    }

    public void setJumlahLapangan(String jumlahLapangan) {
        this.jumlahLapangan = jumlahLapangan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public String getIdPengelola() {
        return idPengelola;
    }

    public void setIdPengelola(String idPengelola) {
        this.idPengelola = idPengelola;
    }

    public String getUsernamePengelola() {
        return usernamePengelola;
    }

    public void setUsernamePengelola(String usernamePengelola) {
        this.usernamePengelola = usernamePengelola;
    }

    public String getEmailPengelola() {
        return emailPengelola;
    }

    public void setEmailPengelola(String emailPengelola) {
        this.emailPengelola = emailPengelola;
    }

    public String getNamaLengkapPengelola() {
        return namaLengkapPengelola;
    }

    public void setNamaLengkapPengelola(String namaLengkapPengelola) {
        this.namaLengkapPengelola = namaLengkapPengelola;
    }

    public String getAlamatPengelola() {
        return alamatPengelola;
    }

    public void setAlamatPengelola(String alamatPengelola) {
        this.alamatPengelola = alamatPengelola;
    }

    public String getNoHpPengelola() {
        return noHpPengelola;
    }

    public void setNoHpPengelola(String noHpPengelola) {
        this.noHpPengelola = noHpPengelola;
    }

    public String getFotoPengelola() {
        return fotoPengelola;
    }

    public void setFotoPengelola(String fotoPengelola) {
        this.fotoPengelola = fotoPengelola;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

}
