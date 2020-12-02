package com.hero.o_badminton.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hero.o_badminton.R;
import com.hero.o_badminton.response.PenggunaResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPenggunaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btn_register;
    EditText et_username, et_password, et_namalengkap, et_email, et_nomorhp, et_alamat, et_nomor_rekening;
    Spinner spinner1;
    ApiInterface apiInterface;

    String nama_bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pengguna);

        this.setTitle("Registrasi Pengguna");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_email = findViewById(R.id.et_email);
        et_namalengkap = findViewById(R.id.et_namalengkap);
        et_nomorhp = findViewById(R.id.et_nomorhp);
        et_alamat = findViewById(R.id.et_alamat);
        btn_register = findViewById(R.id.btn_register);
        et_nomor_rekening = findViewById(R.id.et_nomor_rekening);
        spinner1 = findViewById(R.id.spinner1);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);

        btn_register.setOnClickListener(view -> register());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        nama_bank = adapterView.getItemAtPosition(i).toString();

        Toast.makeText(getApplicationContext(), nama_bank, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void register() {
        final String username = et_username.getText(). toString();
        final String password = et_password.getText(). toString();
        final String email = et_email.getText(). toString();
        final String namalengkap = et_namalengkap.getText(). toString();
        final String nomorhp = et_nomorhp.getText(). toString();
        final String alamat = et_alamat.getText(). toString();
        final String no_rekening = et_nomor_rekening.getText(). toString();

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

        if(no_rekening.equalsIgnoreCase ("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Nomor rekening masih kosong", Toast.LENGTH_SHORT).show();
        }

        if (!isEmpty){
            apiInterface.registerPengguna(username, password, email, namalengkap, alamat, nomorhp, no_rekening, nama_bank, "0").enqueue(new Callback<PenggunaResponse>() {
                @Override
                public void onResponse(@NonNull Call<PenggunaResponse> call, @NonNull Response<PenggunaResponse> response) {
                    System.out.println("response registrasi"+response);
                    if (response.isSuccessful()){
                        if (response.body() != null) {
                            if (response.body().getCode() == 1) {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), LoginPenggunaActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            }else {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PenggunaResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
