package com.hero.o_badminton.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hero.o_badminton.R;
import com.hero.o_badminton.response.PengelolaResponse;
import com.hero.o_badminton.response.PenggunaResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahPasswordActivity extends AppCompatActivity {

    EditText edt_kata_sandi_lama, edt_kata_sandi_baru, edt_kata_sandi_ulang;
    Button btn_ubah_kata_sandi;
    String kata_sandi_lama, kata_sandi_baru, kata_sandi_ulang;
    private SessionManager sessionManager;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);
        setTitle("Ubah Password");

        sessionManager = new SessionManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        edt_kata_sandi_lama = findViewById(R.id.edt_kata_sandi_lama);
        edt_kata_sandi_baru = findViewById(R.id.edt_kata_sandi_baru);
        edt_kata_sandi_ulang = findViewById(R.id.edt_kata_sandi_ulang);
        btn_ubah_kata_sandi = findViewById(R.id.btn_ubah_kata_sandi);

        if (sessionManager.isLoggedin()) {
            if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGGUNA)) {
                btn_ubah_kata_sandi.setOnClickListener(view -> ubah_password_pengguna());

            } else if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGELOLA)) {
                btn_ubah_kata_sandi.setOnClickListener(view -> ubah_password_pengelola());
            }
        }
    }

    private void ubah_password_pengelola() {
        intialize();
        if (!validate()) {
            Toast.makeText(getApplicationContext(), "Gagal Ubah Kata Sandi", Toast.LENGTH_SHORT).show();
        } else {
            apiInterface.ubahPasswordPengelola(sessionManager.getLoginDetail().get(SessionManager.ID), kata_sandi_baru).enqueue(new Callback<PengelolaResponse>() {
                @Override
                public void onResponse(@NonNull Call<PengelolaResponse> call, @NonNull Response<PengelolaResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Berhasil Ubah Kata Sandi", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(UbahPasswordActivity.this, MainActivity.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        sessionManager.logout();
                        finish();
                        startActivity(intent);

                        if (response.body() != null) {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast
                                    .LENGTH_SHORT).show();
                        }

                        Toast.makeText(getApplicationContext(), "Silahkan login kembali", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UbahPasswordActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PengelolaResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    private void ubah_password_pengguna() {
        intialize();
        if (!validate()) {
            Toast.makeText(getApplicationContext(), "Gagal Ubah Kata Sandi", Toast.LENGTH_SHORT).show();
        } else {
            apiInterface.ubahPasswordPengguna(sessionManager.getLoginDetail().get(SessionManager.ID), kata_sandi_baru).enqueue(new Callback<PenggunaResponse>() {
                @Override
                public void onResponse(@NonNull Call<PenggunaResponse> call, @NonNull Response<PenggunaResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Berhasil Ubah Kata Sandi", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(UbahPasswordActivity.this, MainActivity.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        sessionManager.logout();
                        finish();
                        startActivity(intent);

                        if (response.body() != null) {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast
                                    .LENGTH_SHORT).show();
                        }

                        Toast.makeText(getApplicationContext(), "Silahkan login kembali", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UbahPasswordActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PenggunaResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    private boolean validate() {
        boolean valid = true;
        if (kata_sandi_lama.isEmpty()) {
            edt_kata_sandi_lama.setError("Tolong Isi Kata Sandi Lama");
            valid = false;
        } else if (!kata_sandi_lama.equals(sessionManager.getLoginDetail().get(SessionManager.PASSWORD))) {
            edt_kata_sandi_lama.setError("Kata Sandi Lama Salah");
            valid = false;
        }

        if (kata_sandi_baru.isEmpty() | kata_sandi_baru.length() > 30) {
            edt_kata_sandi_baru.setError("Tolong Isi Kata Sandi Baru");
            valid = false;
        }

        if (kata_sandi_ulang.isEmpty()) {
            edt_kata_sandi_ulang.setError("Tolong Isi Kata Sandi Ulang");
            valid = false;
        } else if (!kata_sandi_ulang.equals(kata_sandi_baru)) {
            edt_kata_sandi_ulang.setError("Kata Sandi Ulang Tidak Sama");
            valid = false;
        }
        return valid;
    }

    private void intialize() {
        kata_sandi_lama = edt_kata_sandi_lama.getText().toString().trim();
        kata_sandi_baru = edt_kata_sandi_baru.getText().toString().trim();
        kata_sandi_ulang = edt_kata_sandi_ulang.getText().toString().trim();
    }
}
