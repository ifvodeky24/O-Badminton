package com.hero.o_badminton.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hero.o_badminton.R;
import com.hero.o_badminton.response.PenggunaDetailResponse;
import com.hero.o_badminton.response.TopupResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PilihanIsiSaldoActivity extends AppCompatActivity {

    TextView tvSaldo;
    LinearLayout ll_25000, ll_50000, ll_100000;

    private SessionManager sessionManager;
    private ApiInterface apiInterface;

    String id_pengguna, saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihan_isi_saldo);
        setTitle("Top Up");

        tvSaldo = findViewById(R.id.tvSaldo);
        ll_25000 = findViewById(R.id.ll_25000);
        ll_50000 = findViewById(R.id.ll_50000);
        ll_100000 = findViewById(R.id.ll_100000);

        sessionManager = new SessionManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        id_pengguna = sessionManager.getLoginDetail().get(SessionManager.ID);

        if (sessionManager.isLoggedin()) {
            if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGGUNA)) {
                apiInterface.penggunaDetail(sessionManager.getLoginDetail().get(SessionManager.ID)).enqueue(new Callback<PenggunaDetailResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<PenggunaDetailResponse> call, @NonNull Response<PenggunaDetailResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getMaster().size() > 0) {
                                    saldo = response.body().getMaster().get(0).getTotalSaldo();

                                    tvSaldo.setText("Rp. " + saldo);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PenggunaDetailResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });

                ll_25000.setOnClickListener(view -> apiInterface.tambahTopup(id_pengguna, "25000", "", "Pending").enqueue(new Callback<TopupResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TopupResponse> call, @NonNull Response<TopupResponse> response) {
                        if (response.isSuccessful()) {

                            Intent intent1 = new Intent(PilihanIsiSaldoActivity.this, MainActivity.class);
                            startActivity(intent1);
                            Toast.makeText(getApplicationContext(), "Top Up Sedang diprose, silahkan cek menu Top Up", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TopupResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                }));

                ll_50000.setOnClickListener(view -> apiInterface.tambahTopup(id_pengguna, "50000", "", "Pending").enqueue(new Callback<TopupResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TopupResponse> call, @NonNull Response<TopupResponse> response) {
                        if (response.isSuccessful()) {
                            Intent intent1 = new Intent(PilihanIsiSaldoActivity.this, MainActivity.class);
                            startActivity(intent1);
                            Toast.makeText(getApplicationContext(), "Top Up Sedang diprose, silahkan cek menu Top Up", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TopupResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                }));

                ll_100000.setOnClickListener(view -> apiInterface.tambahTopup(id_pengguna, "10000", "", "Pending").enqueue(new Callback<TopupResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TopupResponse> call, @NonNull Response<TopupResponse> response) {
                        if (response.isSuccessful()) {
                            Intent intent1 = new Intent(PilihanIsiSaldoActivity.this, MainActivity.class);
                            startActivity(intent1);
                            Toast.makeText(getApplicationContext(), "Top Up Sedang diprose, silahkan cek menu Top Up", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TopupResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                }));
            }
        }


    }
}
