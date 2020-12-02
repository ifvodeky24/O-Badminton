package com.hero.o_badminton.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hero.o_badminton.R;
import com.hero.o_badminton.model.Pemesanan;
import com.hero.o_badminton.response.GorResponse;
import com.hero.o_badminton.response.JadwalResponse;
import com.hero.o_badminton.response.LapanganResponse;
import com.hero.o_badminton.response.PemesananResponse;
import com.hero.o_badminton.response.PenggunaDetailResponse;
import com.hero.o_badminton.response.PenggunaResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemesananActivity extends AppCompatActivity {
    private TextView tvNamaGor, tvNomorLapangan, tvTarifLapangan;
    private Button btnPesan;

    private Spinner spJadwal;
    private ApiInterface apiService;
    ArrayList<String> jadwals = new ArrayList<>();

    String id_gor, id_lapangan, jadwal, jam, id_pengguna, saldo, harga;

    private SessionManager sessionManager;

    private DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);
        setTitle("Pemesanan");

        df = new DecimalFormat("#,###");

        apiService = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        spJadwal = findViewById(R.id.spJadwal);
        tvNamaGor = findViewById(R.id.tvNamaGor);
        tvNomorLapangan = findViewById(R.id.tvNomorLapangan);
        tvTarifLapangan = findViewById(R.id.tvTarifLapangan);
        btnPesan = findViewById(R.id.btnPesan);

        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            id_gor = extras.getString("id_gor");
            id_lapangan = extras.getString("id_lapangan");
            System.out.println("garong" + id_gor + " " + id_lapangan);
        }

        if (sessionManager.isLoggedin()) {
            if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGGUNA)) {
                id_pengguna = sessionManager.getLoginDetail().get(SessionManager.ID);
            }
        }

        apiService.lapanganDetail(id_lapangan).enqueue(new Callback<LapanganResponse>() {
            @Override
            public void onResponse(@NonNull Call<LapanganResponse> call, @NonNull Response<LapanganResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {
                            tvNomorLapangan.setText(String.valueOf(response.body().getMaster().get(0).getNomorLapangan()));
                            tvTarifLapangan.setText("Rp. " + df.format(Double.valueOf(response.body().getMaster().get(0).getHarga())) + " / Jam");

                            harga = String.valueOf(response.body().getMaster().get(0).getHarga());
                        } else {
                            Toast.makeText(getApplicationContext(), "Data Lapangan Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LapanganResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

        apiService.penggunaDetail(id_pengguna).enqueue(new Callback<PenggunaDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<PenggunaDetailResponse> call, @NonNull Response<PenggunaDetailResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {
                            saldo = response.body().getMaster().get(0).getTotalSaldo();
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

        apiService.gorDetailbyId(id_gor).enqueue(new Callback<GorResponse>() {
            @Override
            public void onResponse(@NonNull Call<GorResponse> call, @NonNull Response<GorResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {
                            tvNamaGor.setText(response.body().getMaster().get(0).getNamaGor());
                        } else {
                            Toast.makeText(getApplicationContext(), "Data Gor Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GorResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
            }
        });

        apiService.jadwalGetAll(id_lapangan).enqueue(new Callback<JadwalResponse>() {
            @Override
            public void onResponse(@NonNull Call<JadwalResponse> call, @NonNull Response<JadwalResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {
                            for (int i = 0; i < response.body().getMaster().size(); i++) {
                                jadwals.add(response.body().getMaster().get(i).getIdJadwal() + " " + response.body().getMaster().get(i).getHari() + " " + response.body().getMaster().get(i).getJam());

                                System.out.println("apaaa" + response.body().getMaster().get(i).getHari());
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(PemesananActivity.this, android.R.layout.simple_spinner_item, jadwals);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spJadwal.setAdapter(adapter);
                            spJadwal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    jadwal = jadwals.get(i);

                                    String[] kata = jadwal.split(" ");

                                    final String id_jadwal = kata[0];
                                    String hari = kata[1];
                                    String jam = kata[2];

                                    System.out.println("adaa apaaa" + saldo + " " + harga);

                                    btnPesan.setOnClickListener(view1 -> {
                                        if (Integer.parseInt(saldo) < Integer.parseInt(harga)) {
                                            Toast.makeText(getApplicationContext(), "Saldo anda tidak cukup", Toast.LENGTH_SHORT).show();
                                        } else {
                                            apiService.pesanLapangan(id_pengguna, id_lapangan, "Menunggu Konfirmasi", id_jadwal).enqueue(new Callback<PemesananResponse>() {
                                                @Override
                                                public void onResponse(@NonNull Call<PemesananResponse> call1, @NonNull Response<PemesananResponse> response1) {
                                                    if (response1.isSuccessful()) {

                                                        System.out.println("adaa apaaa222" + saldo + " " + harga);

                                                        int sisa_saldo = Integer.parseInt(saldo) - Integer.parseInt(harga);

                                                        String saldonyaa = String.valueOf(sisa_saldo);

                                                        System.out.println("haiii" + saldonyaa);

                                                        apiService.simpanTotalSaldo(id_pengguna, saldonyaa).enqueue(new Callback<PenggunaResponse>() {
                                                            @Override
                                                            public void onResponse(@NonNull Call<PenggunaResponse> call1, @NonNull Response<PenggunaResponse> response1) {
                                                                if (response1.isSuccessful()) {
                                                                    Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                                                                    Intent intent1 = new Intent(PemesananActivity.this, MainActivity.class);
                                                                    startActivity(intent1);
                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(@NonNull Call<PenggunaResponse> call1, @NonNull Throwable t) {
                                                                Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                                                                t.printStackTrace();
                                                            }
                                                        });

                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(@NonNull Call<PemesananResponse> call1, @NonNull Throwable t) {
                                                    Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                                                    t.printStackTrace();
                                                }
                                            });
                                        }

                                    });

//                                    Toast.makeText(PemesananActivity.this, "Anda Memilih " + id_jadwal, Toast.LENGTH_SHORT).show();
                                }


                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Maaf, Tidak Ada Jadwal yang Tersedia", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JadwalResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }

}
