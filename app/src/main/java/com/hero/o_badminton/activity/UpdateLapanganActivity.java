package com.hero.o_badminton.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hero.o_badminton.R;
import com.hero.o_badminton.response.LapanganResponse;
import com.hero.o_badminton.response.TambahLapanganResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateLapanganActivity extends AppCompatActivity {
    TextView tvNamaGor;
    Button btnUpdateLapangan;
    EditText edtNomorLapangan, edtHarga, edtJenis;

    private ApiInterface apiService;
    private SessionManager sessionManager;

    String nama_gor, id_gor, id_lapangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_lapangan);
        setTitle("Perbarui Data Lapangan");

        apiService = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        tvNamaGor = findViewById(R.id.tvNamaGor);
        btnUpdateLapangan = findViewById(R.id.btnUpdateLapangan);
        edtNomorLapangan = findViewById(R.id.edtNomorLapangan);
        edtHarga = findViewById(R.id.edtHarga);
        edtJenis = findViewById(R.id.edtJenis);

        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            id_gor = extras.getString("id_gor");
            nama_gor = extras.getString("nama_gor");
            id_lapangan = extras.getString("id_lapangan");
            System.out.println("garong" + id_gor + " " + nama_gor);
        }

        tvNamaGor.setText(nama_gor);

        apiService.lapanganDetail(id_lapangan).enqueue(new Callback<LapanganResponse>() {
            @Override
            public void onResponse(@NonNull Call<LapanganResponse> call, @NonNull Response<LapanganResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        if (response.body().getMaster().size()>0){
                            edtNomorLapangan.setText(response.body().getMaster().get(0).getNomorLapangan());
                            edtHarga.setText(response.body().getMaster().get(0).getHarga());
                            edtJenis.setText(response.body().getMaster().get(0).getJenis());
                        }else {
                            Toast.makeText(getApplicationContext(), "Data Lapangan Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LapanganResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

        btnUpdateLapangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                if (!isEmpty){
                    apiService.updateLapangan(id_lapangan, id_gor, nomor_lapangan, harga, jenis).enqueue(new Callback<TambahLapanganResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<TambahLapanganResponse> call, @NonNull Response<TambahLapanganResponse> response) {
                            if (response.isSuccessful()){
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);

                                Toast.makeText(getApplicationContext(), "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<TambahLapanganResponse> call, @NonNull Throwable t) {
                            Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });
                }

            }
        });
    }
}
