package com.hero.o_badminton.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hero.o_badminton.R;
import com.hero.o_badminton.adapter.DaftarJadwalPengelolaAdapter;
import com.hero.o_badminton.model.Jadwal;
import com.hero.o_badminton.model.Lapangan;
import com.hero.o_badminton.response.JadwalResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LihatDataJadwalPengelolaActivity extends AppCompatActivity {
    private RecyclerView rvJadwal;
    private FloatingActionButton fab_tambah_jadwal;
    private ArrayList<Jadwal> jadwalArrayList = new ArrayList<>();

    private ApiInterface apiInterface;
    private SessionManager sessionManager;

    String id_pengelola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_jadwal_pengelola);
        setTitle("Data Jadwal");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        rvJadwal = findViewById(R.id.rvJadwal);
        fab_tambah_jadwal = findViewById(R.id.fab_tambah_jadwal);

        fab_tambah_jadwal.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), TambahJadwalActivity.class));
        });

        if (sessionManager.isLoggedin()) {
            if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGELOLA)) {

                id_pengelola = sessionManager.getLoginDetail().get(SessionManager.ID);

                System.out.println("id iniiii" + id_pengelola);

                getDataJadwal();
            }
        }
    }

    private void getDataJadwal() {
        apiInterface.jadwalDetailbyIdPengelola(id_pengelola).enqueue(new Callback<JadwalResponse>() {
            @Override
            public void onResponse(@NonNull Call<JadwalResponse> call, @NonNull Response<JadwalResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {
                            jadwalArrayList.addAll(response.body().getMaster());
                            DaftarJadwalPengelolaAdapter adapter = new DaftarJadwalPengelolaAdapter(LihatDataJadwalPengelolaActivity.this, jadwalArrayList);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LihatDataJadwalPengelolaActivity.this,
                                    RecyclerView.VERTICAL, false);
                            rvJadwal.setLayoutManager(layoutManager);
                            rvJadwal.setItemAnimator(new DefaultItemAnimator());
                            rvJadwal.setAdapter(adapter);
                        }else {
                            Toast.makeText(LihatDataJadwalPengelolaActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Log.e("Cek errtor", response.toString());
                    Toast.makeText(LihatDataJadwalPengelolaActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JadwalResponse> call, @NonNull Throwable t) {
                Toast.makeText(LihatDataJadwalPengelolaActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
