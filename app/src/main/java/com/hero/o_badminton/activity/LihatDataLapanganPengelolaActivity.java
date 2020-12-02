package com.hero.o_badminton.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hero.o_badminton.R;
import com.hero.o_badminton.adapter.DaftarLapanganPengelolaAdapter;
import com.hero.o_badminton.model.Lapangan;
import com.hero.o_badminton.response.LapanganResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LihatDataLapanganPengelolaActivity extends AppCompatActivity {
    private RecyclerView rvLapangan;
    private FloatingActionButton fab_tambah_gor;
    private ArrayList<Lapangan> lapanganArrayList = new ArrayList<>();

    private ApiInterface apiInterface;
    private SessionManager sessionManager;

    String id_pengelola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_lapangan_pengelola);
        setTitle("Data Lapangan");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        rvLapangan = findViewById(R.id.rvGor);
        fab_tambah_gor = findViewById(R.id.fab_tambah_gor);

        fab_tambah_gor.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), TambahLapanganActivity.class)));

        if (sessionManager.isLoggedin()) {
            if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGELOLA)) {

                id_pengelola = sessionManager.getLoginDetail().get(SessionManager.ID);

                System.out.println("id iniiii" + id_pengelola);

                getDataGorLapangan();
            }
        }
    }

    private void getDataGorLapangan() {
        apiInterface.lapanganDetailbyIdPengelola(id_pengelola).enqueue(new Callback<LapanganResponse>() {
            @Override
            public void onResponse(@NonNull Call<LapanganResponse> call, @NonNull Response<LapanganResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {
                            lapanganArrayList.addAll(response.body().getMaster());
                            DaftarLapanganPengelolaAdapter adapter = new DaftarLapanganPengelolaAdapter(LihatDataLapanganPengelolaActivity.this, lapanganArrayList);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LihatDataLapanganPengelolaActivity.this,
                                    RecyclerView.VERTICAL, false);
                            rvLapangan.setLayoutManager(layoutManager);
                            rvLapangan.setItemAnimator(new DefaultItemAnimator());
                            rvLapangan.setAdapter(adapter);

                        } else {
                            Toast.makeText(LihatDataLapanganPengelolaActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LihatDataLapanganPengelolaActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LapanganResponse> call, @NonNull Throwable t) {
                Toast.makeText(LihatDataLapanganPengelolaActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
