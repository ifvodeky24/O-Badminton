package com.hero.o_badminton.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hero.o_badminton.R;
import com.hero.o_badminton.response.PengelolaResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPengelolaActivity extends AppCompatActivity {

    Button btn_register;
    EditText et_username, et_password, et_namalengkap, et_email, et_nomorhp, et_alamat;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pengelola);
        this.setTitle("Registrasi Pengelola");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_email = findViewById(R.id.et_email);
        et_namalengkap = findViewById(R.id.et_namalengkap);
        et_nomorhp = findViewById(R.id.et_nomorhp);
        et_alamat = findViewById(R.id.et_alamat);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        final String username = et_username.getText(). toString();
        final String password = et_password.getText(). toString();
        final String email = et_email.getText(). toString();
        final String namalengkap = et_namalengkap.getText(). toString();
        final String nomorhp = et_nomorhp.getText(). toString();
        final String alamat = et_alamat.getText(). toString();

        boolean isEmpty = false;

        if(username.equalsIgnoreCase ("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }

        if(password.equalsIgnoreCase ("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Password masih kosong", Toast.LENGTH_SHORT).show();
        }

        if(password.length() < 6 ){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Password minimal 6 karakter", Toast.LENGTH_SHORT).show();
        }

        if(email.equalsIgnoreCase ("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Email masih kosong", Toast.LENGTH_SHORT).show();
        }

        if(namalengkap.equalsIgnoreCase ("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Nama lengkap masih kosong", Toast.LENGTH_SHORT).show();
        }

        if(nomorhp.equalsIgnoreCase ("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Nomor HP masih kosong", Toast.LENGTH_SHORT).show();
        }

        if(alamat.equalsIgnoreCase ("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Alamat masih kosong", Toast.LENGTH_SHORT).show();
        }

        if (!isEmpty){
            apiInterface.registerPengelola(username, password, email, namalengkap, alamat, nomorhp).enqueue(new Callback<PengelolaResponse>() {
                @Override
                public void onResponse(@NonNull Call<PengelolaResponse> call, @NonNull Response<PengelolaResponse> response) {
                    System.out.println("response registrasi"+response);
                    if (response.isSuccessful()){
                        if (response.body() != null) {
                            if (response.body().getCode() == 1) {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), LoginPengelolaActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            }else {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PengelolaResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
