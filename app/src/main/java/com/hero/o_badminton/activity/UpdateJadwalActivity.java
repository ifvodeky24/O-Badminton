package com.hero.o_badminton.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.hero.o_badminton.R;
import com.hero.o_badminton.model.Gor;
import com.hero.o_badminton.model.Lapangan;
import com.hero.o_badminton.response.GorResponse;
import com.hero.o_badminton.response.LapanganResponse;
import com.hero.o_badminton.response.TambahJadwalResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateJadwalActivity extends AppCompatActivity {
    private Spinner spGor, spNoLapangan, spHari, spJam;
    Button btnUpdateJadwal;

    private ApiInterface apiService;
    private List<Gor> gors;
    private List<Lapangan> lapangans;
    List<String> listSpinner = new ArrayList<>();
    List<String> listLapanganSpinner = new ArrayList<>();

    private SessionManager sessionManager;

    String nama_gor, no_lapangan, id_gor, id_lapangan, hari, jam, id_jadwal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_jadwal);
        setTitle("Perbarui Data Jadwal");

        apiService = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        spGor = findViewById(R.id.spGor);
        spNoLapangan = findViewById(R.id.spNoLapangan);
        spHari = findViewById(R.id.spHari);
        spJam = findViewById(R.id.spJam);
        btnUpdateJadwal = findViewById(R.id.btnUpdateJadwal);

        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            id_jadwal = extras.getString("id_jadwal");

            System.out.println("garong" + id_jadwal);
        }

        apiService.gorDetailbyIdPengelola(sessionManager.getLoginDetail().get(SessionManager.ID)).enqueue(new Callback<GorResponse>() {
            @Override
            public void onResponse(@NonNull Call<GorResponse> call, @NonNull Response<GorResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {

                            gors = response.body().getMaster();

                            for (int i = 0; i < gors.size(); i++) {
                                listSpinner.add(gors.get(i).getIdGor() + " - " + gors.get(i).getNamaGor());
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(UpdateJadwalActivity.this,
                                    android.R.layout.simple_spinner_item, listSpinner);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spGor.setAdapter(adapter);

                            spGor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    nama_gor = adapterView.getItemAtPosition(i).toString();

                                    String[] kata = nama_gor.split(" - ");

                                    id_gor = kata[0];

                                    listLapanganSpinner.clear();

                                    apiService.lapanganGetAll(id_gor).enqueue(new Callback<LapanganResponse>() {
                                        @Override
                                        public void onResponse(@NonNull Call<LapanganResponse> call, @NonNull Response<LapanganResponse> response) {
                                            if (response.isSuccessful()) {
                                                if (response.body() != null) {
                                                    if (response.body().getMaster().size() > 0) {

                                                        lapangans = response.body().getMaster();

                                                        for (int a = 0; a < lapangans.size(); a++) {
                                                            listLapanganSpinner.add(lapangans.get(a).getIdLapangan() + " - Nomor Lapangan: " + lapangans.get(a).getNomorLapangan());
                                                        }

                                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(UpdateJadwalActivity.this,
                                                                android.R.layout.simple_spinner_item, listLapanganSpinner);
                                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                        spNoLapangan.setAdapter(adapter);

                                                        spNoLapangan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                            @Override
                                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                no_lapangan = adapterView.getItemAtPosition(i).toString();

                                                                String[] kata = no_lapangan.split(" - Nomor Lapangan: ");

                                                                id_lapangan = kata[0];

                                                                Toast.makeText(getApplicationContext(), "anda milih" + id_lapangan, Toast.LENGTH_SHORT).show();

                                                                ArrayAdapter<CharSequence> adapterHari = ArrayAdapter.createFromResource(UpdateJadwalActivity.this, R.array.hari, android.R.layout.simple_spinner_item);
                                                                adapterHari.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                spHari.setAdapter(adapterHari);
                                                                spHari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                    @Override
                                                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                                                        hari = adapterView.getSelectedItem().toString();

                                                                        Toast.makeText(getApplicationContext(), "anda milih" + hari, Toast.LENGTH_SHORT).show();

                                                                        ArrayAdapter<CharSequence> adapterJam = ArrayAdapter.createFromResource(UpdateJadwalActivity.this, R.array.jam, android.R.layout.simple_spinner_item);
                                                                        adapterJam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                        spJam.setAdapter(adapterJam);
                                                                        spJam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                            @Override
                                                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                                                                jam = adapterView.getSelectedItem().toString();

                                                                                System.out.println("cekkkkkkkkk " + id_gor + " " + id_lapangan + " " + hari + " " + jam);

                                                                                Toast.makeText(getApplicationContext(), "anda milih" + jam, Toast.LENGTH_SHORT).show();

                                                                                btnUpdateJadwal.setOnClickListener(view1 -> {
                                                                                    apiService.updateJadwal(id_jadwal, id_lapangan, hari, jam, "Tersedia").enqueue(new Callback<TambahJadwalResponse>() {
                                                                                        @Override
                                                                                        public void onResponse(@NonNull Call<TambahJadwalResponse> call, @NonNull Response<TambahJadwalResponse> response) {
                                                                                            if (response.isSuccessful()) {
                                                                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                                                                startActivity(intent);

                                                                                                Toast.makeText(getApplicationContext(), "Data Jadwal berhasil diperbarui", Toast.LENGTH_SHORT).show();

                                                                                            } else {
                                                                                                Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        }

                                                                                        @Override
                                                                                        public void onFailure(@NonNull Call<TambahJadwalResponse> call, @NonNull Throwable t) {
                                                                                            Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                                                                                            t.printStackTrace();
                                                                                        }
                                                                                    });
                                                                                });
                                                                            }

                                                                            @Override
                                                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                                                            }
                                                                        });
                                                                    }

                                                                    @Override
                                                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                                                    }
                                                                });


                                                            }

                                                            @Override
                                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                                            }
                                                        });

                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Data Lapangan Kosong", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<LapanganResponse> call, @NonNull Throwable t) {
                                            Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                                            t.printStackTrace();
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
