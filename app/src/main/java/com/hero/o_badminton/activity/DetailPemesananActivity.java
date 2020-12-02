package com.hero.o_badminton.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hero.o_badminton.R;
import com.hero.o_badminton.config.ServerConfig;
import com.hero.o_badminton.model.Pemesanan;
import com.hero.o_badminton.response.PemesananResponse;
import com.hero.o_badminton.response.PemesananDetailResponse;
import com.hero.o_badminton.response.PenggunaDetailResponse;
import com.hero.o_badminton.response.PenggunaResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import java.text.DecimalFormat;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPemesananActivity extends AppCompatActivity {
    TextView tvNamaGor, tvJumlahLapangan, tvFasilitas, tvDeskripsi, tvNamaPengelola, tvNomorHpPengelola;
    TextView tvIdPemesanan, tvNamaPemesan, tvAlamatPemesan, tvNomorHpPemesan, tvStatusPemesanan, tvTarif, tvHari, tvJam;
    Button btnKonfirmasi, btnSelesai, btnBatal;
    ImageView ivGor;

    ApiInterface apiInterface;

    SessionManager sessionManager;

    String id_pemesanan, nama_gor, jumlah_lapangan, fasilitas, deskripsi, nama_pengelola, id_pengguna;
    String nomor_hp_pengelola, nama_pemesan, alamat_pemesan, nomor_hp_pemesan, status_pemesanan, foto_gor, tarif, hari, jam;

    private DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemesanan);
        setTitle("Detail Pemesanan");

        ivGor = findViewById(R.id.ivGor);
        tvNamaGor = findViewById(R.id.tvNamaGor);
        tvJumlahLapangan = findViewById(R.id.tvJumlahLapangan);
        tvFasilitas = findViewById(R.id.tvFasilitas);
        tvDeskripsi = findViewById(R.id.tvDeskripsi);
        tvNamaPengelola = findViewById(R.id.tvNamaPengelola);
        tvNomorHpPengelola = findViewById(R.id.tvNomorHpPengelola);
        tvIdPemesanan = findViewById(R.id.tvIdPemesanan);
        tvNamaPemesan = findViewById(R.id.tvNamaPemesan);
        tvAlamatPemesan = findViewById(R.id.tvAlamatPemesan);
        tvNomorHpPemesan = findViewById(R.id.tvNomorHpPemesan);
        tvTarif = findViewById(R.id.tvTarif);
        tvStatusPemesanan = findViewById(R.id.tvStatusPemesanan);
        btnKonfirmasi = findViewById(R.id.btnKonfirmasi);
        btnSelesai = findViewById(R.id.btnSelesai);
        btnBatal = findViewById(R.id.btnBatal);
        tvHari = findViewById(R.id.tvHari);
        tvJam = findViewById(R.id.tvJam);

        df = new DecimalFormat("#,###");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        sessionManager = new SessionManager(this);

        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            id_pemesanan = extras.getString("id_pemesanan");
            System.out.println("garong" + id_pemesanan);
        }

        apiInterface.pemesananDetail(id_pemesanan).enqueue(new Callback<PemesananDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<PemesananDetailResponse> call, @NonNull Response<PemesananDetailResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {
                            nama_gor = response.body().getMaster().get(0).getNamaGor();
                            jumlah_lapangan = String.valueOf(response.body().getMaster().get(0).getJumlahLapangan());
                            fasilitas = response.body().getMaster().get(0).getFasilitas();
                            deskripsi = response.body().getMaster().get(0).getDeskripsi();
                            nama_pengelola = response.body().getMaster().get(0).getNamaLengkapPengelola();
                            nomor_hp_pengelola = response.body().getMaster().get(0).getNoHpPengelola();
                            nama_pemesan = response.body().getMaster().get(0).getNamaLengkapPengguna();
                            alamat_pemesan = response.body().getMaster().get(0).getAlamatPengguna();
                            nomor_hp_pemesan = response.body().getMaster().get(0).getNoHpPengguna();
                            status_pemesanan = response.body().getMaster().get(0).getStatus();
                            foto_gor = response.body().getMaster().get(0).getFoto();
                            tarif = response.body().getMaster().get(0).getHarga();
                            id_pengguna = response.body().getMaster().get(0).getIdPengguna();
                            jam = response.body().getMaster().get(0).getJam();
                            hari = response.body().getMaster().get(0).getHari();

                            tvIdPemesanan.setText("Pemesanan ke- " + id_pemesanan);
                            tvNamaGor.setText(nama_gor);
                            tvJumlahLapangan.setText("Jumlah Lapangan: " + jumlah_lapangan);
                            tvFasilitas.setText("Fasilitas: " + fasilitas);
                            tvDeskripsi.setText("Deskripsi: " + deskripsi);
                            tvNamaPengelola.setText("Nama Pengelola: " + nama_pengelola);
                            tvNomorHpPengelola.setText("No Hp Pengelola: " + nomor_hp_pengelola);
                            tvNamaPemesan.setText("Nama Pemesan: " + nama_pemesan);
                            tvAlamatPemesan.setText("Alamat Pemesan: " + alamat_pemesan);
                            tvNomorHpPemesan.setText("No Hp Pemesan: " + nomor_hp_pemesan);
                            tvHari.setText("Hari: " + hari);
                            tvJam.setText("Jam: " + jam);
                            tvStatusPemesanan.setText(status_pemesanan);
                            tvTarif.setText("Tarif Lapangan: Rp. " + df.format(Double.valueOf(tarif)) + " / Jam");

                            Glide.with(DetailPemesananActivity.this)
                                    .load(ServerConfig.GOR_IMAGE + foto_gor)
                                    .into(ivGor);

                            if (sessionManager.isLoggedin()) {
                                if (sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN).equalsIgnoreCase(SessionManager.LEVEL_PENGGUNA) &&
                                        status_pemesanan.equalsIgnoreCase("Menunggu Konfirmasi")) {

                                    btnSelesai.setVisibility(View.GONE);
                                    btnKonfirmasi.setVisibility(View.GONE);


                                } else if (sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN).equalsIgnoreCase(SessionManager.LEVEL_PENGELOLA) &&
                                        status_pemesanan.equalsIgnoreCase("Menunggu Konfirmasi")) {

                                    btnSelesai.setVisibility(View.GONE);
                                }

                                if (sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN).equalsIgnoreCase(SessionManager.LEVEL_PENGGUNA) &&
                                        status_pemesanan.equalsIgnoreCase("Sedang Memesan")) {

                                    btnBatal.setVisibility(View.GONE);
                                    btnKonfirmasi.setVisibility(View.GONE);


                                } else if (sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN).equalsIgnoreCase(SessionManager.LEVEL_PENGELOLA) &&
                                        status_pemesanan.equalsIgnoreCase("Sedang Memesan")) {

                                    btnKonfirmasi.setVisibility(View.GONE);
                                    btnBatal.setVisibility(View.GONE);
                                }

                                if (sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN).equalsIgnoreCase(SessionManager.LEVEL_PENGGUNA) &&
                                        status_pemesanan.equalsIgnoreCase("Selesai")) {

                                    btnBatal.setVisibility(View.GONE);
                                    btnKonfirmasi.setVisibility(View.GONE);
                                    btnSelesai.setVisibility(View.GONE);


                                } else if (sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN).equalsIgnoreCase(SessionManager.LEVEL_PENGELOLA) &&
                                        status_pemesanan.equalsIgnoreCase("Selesai")) {

                                    btnBatal.setVisibility(View.GONE);
                                    btnKonfirmasi.setVisibility(View.GONE);
                                    btnSelesai.setVisibility(View.GONE);
                                }

                                if (sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN).equalsIgnoreCase(SessionManager.LEVEL_PENGGUNA) &&
                                        status_pemesanan.equalsIgnoreCase("Batal")) {

                                    btnBatal.setVisibility(View.GONE);
                                    btnKonfirmasi.setVisibility(View.GONE);
                                    btnSelesai.setVisibility(View.GONE);


                                } else if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGELOLA) &&
                                        status_pemesanan.equalsIgnoreCase("Batal")) {

                                    btnBatal.setVisibility(View.GONE);
                                    btnKonfirmasi.setVisibility(View.GONE);
                                    btnSelesai.setVisibility(View.GONE);
                                }

                            } else if (!sessionManager.isLoggedin()) {
                                btnSelesai.setVisibility(View.GONE);
                                btnKonfirmasi.setVisibility(View.GONE);
                                btnBatal.setVisibility(View.GONE);
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PemesananDetailResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiInterface.batalPesanLapangan(id_pemesanan).enqueue(new Callback<PemesananResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<PemesananResponse> call, @NonNull Response<PemesananResponse> response) {
                        if (response.isSuccessful()) {
                            Intent intent1 = new Intent(DetailPemesananActivity.this, MainActivity.class);
                            startActivity(intent1);

                            System.out.println("dimanaa" + tarif + " " + id_pengguna);

                            apiInterface.penggunaDetail(id_pengguna).enqueue(new Callback<PenggunaDetailResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<PenggunaDetailResponse> call, @NonNull Response<PenggunaDetailResponse> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body() != null) {
                                            if (response.body().getMaster().size() > 0) {

                                                String saldo = response.body().getMaster().get(0).getTotalSaldo();

                                                System.out.println("ooooiiiiii " + saldo);

                                                int sisa_saldo = Integer.parseInt(saldo) + Integer.parseInt(tarif);

                                                apiInterface.simpanTotalSaldo(id_pengguna, String.valueOf(sisa_saldo)).enqueue(new Callback<PenggunaResponse>() {
                                                    @Override
                                                    public void onResponse(@NonNull Call<PenggunaResponse> call, @NonNull Response<PenggunaResponse> response) {
                                                        if (response.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "Saldo Berhasil diupdate", Toast.LENGTH_SHORT).show();

                                                            Intent intent1 = new Intent(DetailPemesananActivity.this, MainActivity.class);
                                                            intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                            startActivity(intent1);
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(@NonNull Call<PenggunaResponse> call, @NonNull Throwable t) {
                                                        Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                                                        t.printStackTrace();
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<PenggunaDetailResponse> call, @NonNull Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                                    t.printStackTrace();
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PemesananResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
        });

        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiInterface.konfirmasiPesanLapangan(id_pemesanan).enqueue(new Callback<PemesananResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<PemesananResponse> call, @NonNull Response<PemesananResponse> response) {
                        if (response.isSuccessful()) {
                            Intent intent1 = new Intent(DetailPemesananActivity.this, MainActivity.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent1);
                        } else {
                            Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PemesananResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
        });

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiInterface.selesaiPesanLapangan(id_pemesanan).enqueue(new Callback<Pemesanan>() {
                    @Override
                    public void onResponse(@NonNull Call<Pemesanan> call, @NonNull Response<Pemesanan> response) {
                        if (response.isSuccessful()) {

                            Intent intent1 = new Intent(DetailPemesananActivity.this, MainActivity.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent1);

                        } else {
                            Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Pemesanan> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
        });


    }
}
