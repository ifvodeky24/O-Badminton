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
import com.hero.o_badminton.adapter.DaftarGorPengelolaAdapter;
import com.hero.o_badminton.model.Gor;
import com.hero.o_badminton.response.GorResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LihatDataGorPengelolaActivity extends AppCompatActivity {
    private RecyclerView rvGor;
    private FloatingActionButton fab_tambah_gor;
    private ArrayList<Gor> gorArrayList = new ArrayList<>();

    private ApiInterface apiInterface;
    private SessionManager sessionManager;

    String id_pengelola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_gor_pengelola);
        setTitle("Data Gor");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        rvGor = findViewById(R.id.rvGor);
        fab_tambah_gor = findViewById(R.id.fab_tambah_gor);

        fab_tambah_gor.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), TambahGorActivity.class)));

        if (sessionManager.isLoggedin()) {
            if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGELOLA)) {

                id_pengelola = sessionManager.getLoginDetail().get(SessionManager.ID);

                System.out.println("id iniiii" + id_pengelola);

                getDataGorPengelola();
            }
        }

    }

    private void getDataGorPengelola() {
        apiInterface.gorDetailbyIdPengelola(id_pengelola).enqueue(new Callback<GorResponse>() {
            @Override
            public void onResponse(@NonNull Call<GorResponse> call, @NonNull Response<GorResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {
                            gorArrayList.addAll(response.body().getMaster());
                            DaftarGorPengelolaAdapter adapter = new DaftarGorPengelolaAdapter(LihatDataGorPengelolaActivity.this, gorArrayList);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LihatDataGorPengelolaActivity.this,
                                    RecyclerView.VERTICAL, false);
                            rvGor.setLayoutManager(layoutManager);
                            rvGor.setItemAnimator(new DefaultItemAnimator());
                            rvGor.setAdapter(adapter);

                        } else {
                            Toast.makeText(LihatDataGorPengelolaActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LihatDataGorPengelolaActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GorResponse> call, @NonNull Throwable t) {
                Toast.makeText(LihatDataGorPengelolaActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
