package com.hero.o_badminton.rest;

import com.hero.o_badminton.model.Pemesanan;
import com.hero.o_badminton.response.GetTopupResponse;
import com.hero.o_badminton.response.GorResponse;
import com.hero.o_badminton.response.HapusResponse;
import com.hero.o_badminton.response.JadwalResponse;
import com.hero.o_badminton.response.LapanganResponse;
import com.hero.o_badminton.response.PemesananDetailResponse;
import com.hero.o_badminton.response.PemesananResponse;
import com.hero.o_badminton.response.PengelolaDetailResponse;
import com.hero.o_badminton.response.PengelolaResponse;
import com.hero.o_badminton.response.PenggunaDetailResponse;
import com.hero.o_badminton.response.PenggunaResponse;
import com.hero.o_badminton.response.SumJadwalResponse;
import com.hero.o_badminton.response.TambahGorResponse;
import com.hero.o_badminton.response.TambahJadwalResponse;
import com.hero.o_badminton.response.TambahLapanganResponse;
import com.hero.o_badminton.response.TopupResponse;
import com.hero.o_badminton.response.UpdateStatusGorResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    //Untuk Login pengguna
    @FormUrlEncoded
    @POST("pengguna/login")
    Call<PenggunaResponse> loginPengguna(
            @Field("email") String email,
            @Field("password") String password
    );

    //Untuk Login pengelola
    @FormUrlEncoded
    @POST("pengelola/login")
    Call<PengelolaResponse> loginPengelola(
            @Field("email") String email,
            @Field("password") String password
    );

    //Untuk Registrasi pengguna
    @FormUrlEncoded
    @POST("pengguna/register")
    Call<PenggunaResponse> registerPengguna(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("alamat") String alamat,
            @Field("no_hp") String no_hp,
            @Field("no_rekening") String no_rekening,
            @Field("nama_bank") String nama_bank,
            @Field("total_saldo") String total_saldo
    );

    //Untuk Registrasi pengelola
    @FormUrlEncoded
    @POST("pengelola/register")
    Call<PengelolaResponse> registerPengelola(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("alamat") String alamat,
            @Field("no_hp") String no_hp
    );

    //untuk get seluruh data gor
    @GET("gor/get-all")
    Call<GorResponse> gorGetAll(
    );

    //untuk get detail gor berdasarkan nama
    @GET("gor/by-name")
    Call<GorResponse> gorDetail(
            @Query("nama_gor") String nama_gor
    );

    //untuk get detail gor berdasarkan id
    @GET("gor/by-id")
    Call<GorResponse> gorDetailbyId(
            @Query("id_gor") String id_gor
    );

    //untuk get detail gor berdasarkan id_pengelola
    @GET("gor/by-id-pengelola")
    Call<GorResponse> gorDetailbyIdPengelola(
            @Query("id_pengelola") String id_pengelola
    );

    //untuk get detail pengelola
    @GET("pengelola/by-id")
    Call<PengelolaDetailResponse> pengelolaDetail(
            @Query("id_pengelola") String id_pengelola
    );

    //untuk get detail pengguna
    @GET("pengguna/by-id")
    Call<PenggunaDetailResponse> penggunaDetail(
            @Query("id_pengguna") String id_pengguna
    );

    //Ubah Password pengelola
    @FormUrlEncoded
    @POST("pengelola/ubah-password")
    Call<PengelolaResponse> ubahPasswordPengelola(
            @Field("id_pengelola") String id_pengelola,
            @Field("password") String password
    );

    //Ubah Password pengguna
    @FormUrlEncoded
    @POST("pengguna/ubah-password")
    Call<PenggunaResponse> ubahPasswordPengguna(
            @Field("id_pengguna") String id_pengguna,
            @Field("password") String password
    );

    //Ubah profil pengelola
    @FormUrlEncoded
    @POST("pengelola/ubah-profil")
    Call<PengelolaResponse> ubahProfilPengelola(
            @Field("id_pengelola") String id_pengelola,
            @Field("username") String username,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("alamat") String alamat,
            @Field("email") String email,
            @Field("no_hp") String no_hp
    );

    //Ubah profil pengguna
    @FormUrlEncoded
    @POST("pengguna/ubah-profil")
    Call<PenggunaResponse> ubahProfilPengguna(
            @Field("id_pengguna") String id_pengguna,
            @Field("username") String username,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("alamat") String alamat,
            @Field("email") String email,
            @Field("no_hp") String no_hp,
            @Field("no_rekening") String no_rekening,
            @Field("nama_bank") String nama_bank
    );

    //Tambah data gor
    @FormUrlEncoded
    @POST("gor/tambah-gor")
    Call<TambahGorResponse> tambahGor(
            @Field("id_pengelola") String id_pengelola,
            @Field("nama_gor") String nama_gor,
            @Field("alamat_gor") String alamat_gor,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("deskripsi") String deskripsi,
            @Field("jumlah_lapangan") String jumlah_lapangan,
            @Field("fasilitas") String fasilitas,
            @Field("status") String status,
            @Field("foto") String foto
    );

    //Update data gor
    @FormUrlEncoded
    @POST("gor/update-gor")
    Call<TambahGorResponse> updateGor(
            @Field("id_gor") String id_gor,
            @Field("id_pengelola") String id_pengelola,
            @Field("nama_gor") String nama_gor,
            @Field("alamat_gor") String alamat_gor,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("deskripsi") String deskripsi,
            @Field("jumlah_lapangan") String jumlah_lapangan,
            @Field("fasilitas") String fasilitas,
            @Field("status") String status,
            @Field("foto") String foto
    );

    //Update data gor
    @FormUrlEncoded
    @POST("gor/update-status-gor")
    Call<UpdateStatusGorResponse> updateStatusGor(
            @Field("id_gor") String id_gor,
            @Field("status") String status
    );

    //Hapus data gor
    @FormUrlEncoded
    @POST("gor/delete-gor")
    Call<HapusResponse> hapusGor(
            @Field("id_gor") String id_gor
    );

    //Cari data gor
    @GET("gor/search")
    Call<GorResponse> cariGor(
            @Query("query") String query
    );

    //Tambah data lapangan
    @FormUrlEncoded
    @POST("lapangan/tambah-lapangan")
    Call<TambahLapanganResponse> tambahLapangan(
            @Field("id_gor") String id_gor,
            @Field("nomor_lapangan") String nomor_lapangan,
            @Field("harga") String harga,
            @Field("jenis") String jenis
    );

    //update data lapangan
    @FormUrlEncoded
    @POST("lapangan/update-lapangan")
    Call<TambahLapanganResponse> updateLapangan(
            @Field("id_lapangan") String id_lapangan,
            @Field("id_gor") String id_gor,
            @Field("nomor_lapangan") String nomor_lapangan,
            @Field("harga") String harga,
            @Field("jenis") String jenis
    );

    //tambah top up
    @FormUrlEncoded
    @POST("topup/tambah-topup")
    Call<TopupResponse> tambahTopup(
            @Field("id_pengguna") String id_pengguna,
            @Field("jumlah") String jumlah,
            @Field("bukti_transfer") String bukti_transfer,
            @Field("status") String status
    );

    //update top up
    @FormUrlEncoded
    @POST("topup/update-topup")
    Call<TopupResponse> updateTopup(
            @Field("id_topup") String id_topup,
            @Field("bukti_transfer") String bukti_transfer
    );

    //batal top up
    @FormUrlEncoded
    @POST("topup/batal-topup")
    Call<TopupResponse> batalTopup(
            @Field("id_topup") String id_topup
    );

    //batal top up
    @GET("topup/by-id-pengguna")
    Call<GetTopupResponse> getTopup(
            @Query("id_pengguna") String id_pengguna
    );

    //untuk get all gor
    @GET("lapangan/get-all")
    Call<LapanganResponse> lapanganGetAll(
            @Query("id_gor") String id_gor
    );

    //untuk detail lapangan
    @GET("lapangan/by-id")
    Call<LapanganResponse> lapanganDetail(
            @Query("id_lapangan") String id_lapangan
    );

    //untuk get detail lapangan berdasarkan id_pengelola
    @GET("lapangan/by-id-pengelola")
    Call<LapanganResponse> lapanganDetailbyIdPengelola(
            @Query("id_pengelola") String id_pengelola
    );

    //untuk get all lapangan berdasarkan id pengelola
    @GET("lapangan/get-all-pengelola")
    Call<LapanganResponse> lapanganGetAllPengelola(
            @Query("id_gor") String id_gor,
            @Query("id_pengelola") String id_pengelola
    );

    //Hapus data lapangan
    @FormUrlEncoded
    @POST("lapangan/delete-lapangan")
    Call<HapusResponse> hapusLapangan(
            @Field("id_lapangan") String id_lapangan
    );

    //untuk get all jadwal
    @GET("jadwal/get-all")
    Call<JadwalResponse> jadwalGetAll(
            @Query("id_lapangan") String id_lapangan
    );

    //untuk get detail jadwal berdasarkan id_pengelola
    @GET("jadwal/by-id-pengelola")
    Call<JadwalResponse> jadwalDetailbyIdPengelola(
            @Query("id_pengelola") String id_pengelola
    );

    //untuk get detail jadwal berdasarkan id_gor
    @GET("jadwal/get-jadwal-by-gor")
    Call<JadwalResponse> jadwalByIdGor(
            @Query("id_gor") String id_gor
    );

    //untuk get jumlah lapangan di tabel jadwal sesuai id gor
    @GET("jadwal/sum-jadwal")
    Call<SumJadwalResponse> sumJadwal(
            @Query("id_gor") String id_gor
    );

    //Tambah jadwal
    @FormUrlEncoded
    @POST("jadwal/tambah-jadwal")
    Call<TambahJadwalResponse> tambahJadwal(
            @Field("id_lapangan") String id_lapangan,
            @Field("hari") String hari,
            @Field("jam") String jam,
            @Field("status") String status
    );

    //Update jadwal
    @FormUrlEncoded
    @POST("jadwal/update-jadwal")
    Call<TambahJadwalResponse> updateJadwal(
            @Field("id_jadwal") String id_jadwal,
            @Field("id_lapangan") String id_lapangan,
            @Field("hari") String hari,
            @Field("jam") String jam,
            @Field("status") String status
    );

    //Hapus data jadwal
    @FormUrlEncoded
    @POST("jadwal/delete-jadwal")
    Call<HapusResponse> hapusJadwal(
            @Field("id_jadwal") String id_jadwal
    );

    //Pesan lapangan
    @FormUrlEncoded
    @POST("pemesanan/pesan")
    Call<PemesananResponse> pesanLapangan(
            @Field("id_pengguna") String id_pengguna,
            @Field("id_lapangan") String id_lapangan,
            @Field("status") String status,
            @Field("id_jadwal") String id_jadwal
    );

    //Batal Pesan lapangan
    @FormUrlEncoded
    @POST("pemesanan/batal-pesan")
    Call<PemesananResponse> batalPesanLapangan(
            @Field("id_pemesanan") String id_pemesanan
    );

    //Konfirmasi Pesan lapangan
    @FormUrlEncoded
    @POST("pemesanan/konfirmasi-pesan")
    Call<PemesananResponse> konfirmasiPesanLapangan(
            @Field("id_pemesanan") String id_pemesanan
    );

    //Selesai Pesan lapangan
    @FormUrlEncoded
    @POST("pemesanan/selesai-pesan")
    Call<Pemesanan> selesaiPesanLapangan(
            @Field("id_pemesanan") String id_pemesanan
    );

    //untuk get all pemesanan by pengguna menunggu konfirmasi
    @GET("pemesanan/get-all-by-pengguna-menunggu-konfirmasi")
    Call<PemesananDetailResponse> pemesananGetAllByPenggunaMenungguKonfirmasi(
            @Query("id_pengguna") String id_pengguna
    );

    //untuk get all pemesanan by pengguna sedang memesan
    @GET("pemesanan/get-all-by-pengguna-sedang-memesan")
    Call<PemesananDetailResponse> pemesananGetAllByPenggunaSedangMemesan(
            @Query("id_pengguna") String id_pengguna
    );

    //untuk get all pemesanan by pengguna selesai
    @GET("pemesanan/get-all-by-pengguna-selesai")
    Call<PemesananDetailResponse> pemesananGetAllByPenggunaSelesai(
            @Query("id_pengguna") String id_pengguna
    );

    //untuk get all pemesanan by pengguna batal
    @GET("pemesanan/get-all-by-pengguna-batal")
    Call<PemesananDetailResponse> pemesananGetAllByPenggunaBatal(
            @Query("id_pengguna") String id_pengguna
    );

    //untuk get all pemesanan by pengelola menunggu konfirmasi
    @GET("pemesanan/get-all-by-pengelola-menunggu-konfirmasi")
    Call<PemesananDetailResponse> pemesananGetAllByPengelolaMenungguKonfirmasi(
            @Query("id_pengelola") String id_pengelola
    );

    //untuk get all pemesanan by pengelola sedang memesan
    @GET("pemesanan/get-all-by-pengelola-sedang-memesan")
    Call<PemesananDetailResponse> pemesananGetAllByPengelolaSedangMemesan(
            @Query("id_pengelola") String id_pengelola
    );

    //untuk get all pemesanan by pengelola selesai
    @GET("pemesanan/get-all-by-pengelola-selesai")
    Call<PemesananDetailResponse> pemesananGetAllByPengelolaSelesai(
            @Query("id_pengelola") String id_pengelola
    );

    //untuk get all pemesanan by pengelola batal
    @GET("pemesanan/get-all-by-pengelola-batal")
    Call<PemesananDetailResponse> pemesananGetAllByPengelolaBatal(
            @Query("id_pengelola") String id_pengelola
    );

    //untuk detail pemesanan berdasarkan id pemesanan
    @GET("pemesanan/by-id")
    Call<PemesananDetailResponse> pemesananDetail(
            @Query("id_pemesanan") String id_pemesanan
    );

    //Upload foto pengguna
    @FormUrlEncoded
    @POST("pengguna/upload-foto-pengguna")
    Call<PenggunaResponse> uploadFotoPengguna(
            @Field("id_pengguna") String id_pengguna,
            @Field("foto") String foto
    );

    //Upload foto pengelola
    @FormUrlEncoded
    @POST("pengelola/upload-foto-pengelola")
    Call<PengelolaResponse> uploadFotoPengelola(
            @Field("id_pengelola") String id_pengelola,
            @Field("foto") String foto
    );

    //Simpan total saldo
    @FormUrlEncoded
    @POST("pengguna/simpan-saldo")
    Call<PenggunaResponse> simpanTotalSaldo(
            @Field("id_pengguna") String id_pengguna,
            @Field("total_saldo") String total_saldo
    );
}
