package com.hero.o_badminton.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hero.o_badminton.R;
import com.hero.o_badminton.response.PengelolaResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPengelolaActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    TextView tvRegisterPengelola;
    Button btnLoginPengelola;
    ApiInterface apiInterface;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pengelola);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        sessionManager = new SessionManager(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLoginPengelola = findViewById(R.id.btnLoginPengelola);
        tvRegisterPengelola = findViewById(R.id.tvRegisterPengelola);


        btnLoginPengelola.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            boolean isEmpty = false;

            if (email.equalsIgnoreCase("")) {
                isEmpty = true;
                Toast.makeText(getApplicationContext(), "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
            }

            if (password.equalsIgnoreCase("")) {
                isEmpty = true;
                Toast.makeText(getApplicationContext(), "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            }

            if (!isEmpty) {
                apiInterface.loginPengelola(email, password).enqueue(new Callback<PengelolaResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<PengelolaResponse> call, @NonNull Response<PengelolaResponse> response) {
                        System.out.println("responsenya" + response);

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getCode() == 1) {
                                    final Integer strId = response.body().getData().getIdPengelola();
                                    String strUsername = response.body().getData().getUsername();
                                    String strNamaLengkap = response.body().getData().getNamaLengkap();
                                    String strEmail = response.body().getData().getEmail();
                                    String strPassword = response.body().getData().getPassword();
                                    String strNomorHp = String.valueOf(response.body().getData().getNoHp());
                                    String strAlamat = response.body().getData().getAlamat();
                                    String strFoto = response.body().getData().getFoto();

                                    sessionManager.creatLoginSession(String.valueOf(strId),
                                            strUsername,
                                            strPassword,
                                            strEmail,
                                            strNamaLengkap,
                                            strAlamat,
                                            strNomorHp,
                                            strFoto,
                                            SessionManager.LEVEL_PENGELOLA);

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<PengelolaResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        tvRegisterPengelola.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegisterPengelolaActivity.class);
            startActivity(intent);
        });

    }

    public void onBackPressed() {
        super.onBackPressed();
        goToMainActivity();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(LoginPengelolaActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}
