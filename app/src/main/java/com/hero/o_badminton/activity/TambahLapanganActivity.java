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
import com.hero.o_badminton.model.Gor;
import com.hero.o_badminton.response.GorResponse;
import com.hero.o_badminton.response.TambahLapanganResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahLapanganActivity extends AppCompatActivity {
    EditText edtNomorLapangan, edtHarga, edtJenis;
    Button btnTambahLapangan;

    private Spinner spGor;
    private ApiInterface apiService;
    private List<Gor> gors;
    List<String> listSpinner = new ArrayList<>();

    private SessionManager sessionManager;

    String nama_gor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_lapangan);
        setTitle("Tambah Data Lapangan");

        apiService = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        spGor = findViewById(R.id.spGor);
        edtNomorLapangan = findViewById(R.id.edtNomorLapangan);
        edtHarga = findViewById(R.id.edtHarga);
        edtJenis = findViewById(R.id.edtJenis);
        btnTambahLapangan = findViewById(R.id.btnTambahLapangan);

        apiService.gorDetailbyIdPengelola(sessionManager.getLoginDetail().get(SessionManager.ID)).enqueue(new Callback<GorResponse>() {
            @Override
            public void onResponse(@NonNull Call<GorResponse> call, @NonNull Response<GorResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {

                            gors = response.body().getMaster();

                            for (int i = 0; i < gors.size(); i++){
                                listSpinner.add(gors.get(i).getIdGor() + " " +gors.get(i).getNamaGor());
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(TambahLapanganActivity.this,
                                    android.R.layout.simple_spinner_item, listSpinner);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spGor.setAdapter(adapter);

                            spGor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    nama_gor = adapterView.getItemAtPosition(i).toString();

                                    String[] kata = nama_gor.split(" ");

                                    final String id_gor = kata[0];

                                    btnTambahLapangan.setOnClickListener(view1 -> {
                                        boolean isEmpty = false;

                                        final String nomor_lapangan = edtNomorLapangan.getText().toString();
                                        final String harga = edtHarga.getText().toString();
                                        final String jenis = edtJenis.getText().toString();

                                        if (nomor_lapangan.equalsIgnoreCase("")) {
                                            isEmpty = true;
                                            Toast.makeText(getApplicationContext(), "Nomor Lapangan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                                        }

                                        if (harga.equalsIgnoreCase("")) {
                                            isEmpty = true;
                                            Toast.makeText(getApplicationContext(), "Tarif tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                                        }

                                        if (jenis.equalsIgnoreCase("")) {
                                            isEmpty = true;
                                            Toast.makeText(getApplicationContext(), "Jenis tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                                        }

                                        if (!isEmpty) {
                                            apiService.tambahLapangan(id_gor, nomor_lapangan, harga, jenis).enqueue(new Callback<TambahLapanganResponse>() {
                                                @Override
                                                public void onResponse(@NonNull Call<TambahLapanganResponse> call1, @NonNull Response<TambahLapanganResponse> response1) {
                                                    if (response1.isSuccessful()) {
                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                        startActivity(intent);

                                                        Toast.makeText(getApplicationContext(), "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(@NonNull Call<TambahLapanganResponse> call1, @NonNull Throwable t) {
                                                    Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                                                    t.printStackTrace();
                                                }
                                            });
                                        }


                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
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
                t.printStackTrace();
            }
        });


    }
}
