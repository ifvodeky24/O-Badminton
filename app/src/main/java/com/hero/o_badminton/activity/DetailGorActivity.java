package com.hero.o_badminton.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hero.o_badminton.R;
import com.hero.o_badminton.adapter.DaftarLapanganAdapter;
import com.hero.o_badminton.config.ServerConfig;
import com.hero.o_badminton.model.Lapangan;
import com.hero.o_badminton.response.GorResponse;
import com.hero.o_badminton.response.LapanganResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailGorActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView tvNamaGor, tvJumlahLapangan, tvDeskripsi, tvAlamat, tvFasilitas;
    private ImageView ivGor;

    private SessionManager sessionManager;
    private ApiInterface apiInterface;

    private String alamat_gor, deskripsi, foto, fasilitas, nama_gor, id_gor;
    private double latitude, longitude;
    private Integer jumlah_lapangan, id_pengelola;

    private GoogleMap mMap;

    private RecyclerView rv_lapangan;

    private ArrayList<Lapangan> lapanganArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gor);
        setTitle("Detail Gor");

        tvNamaGor = findViewById(R.id.tvNamaGor);
        tvJumlahLapangan = findViewById(R.id.tvJumlahLapangan);
        tvDeskripsi = findViewById(R.id.tvDeskripsi);
        tvFasilitas = findViewById(R.id.tvFasilitas);
        tvAlamat = findViewById(R.id.tvAlamat);
        ivGor = findViewById(R.id.ivGor);
        rv_lapangan = findViewById(R.id.rv_lapangan);

        sessionManager = new SessionManager(getApplicationContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            nama_gor = extras.getString("nama_gor");
            System.out.println("kucing" + nama_gor);
        }

        getDetailGor(nama_gor);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps_gor);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

    }

    private void getDetailGor(final String nama_gor) {
        apiInterface.gorDetail(nama_gor).enqueue(new Callback<GorResponse>() {
            @Override
            public void onResponse(@NonNull Call<GorResponse> call, @NonNull Response<GorResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getMaster().size() > 0) {
                        id_gor = String.valueOf(response.body().getMaster().get(0).getIdGor());
                        alamat_gor = response.body().getMaster().get(0).getAlamatGor();
                        deskripsi = response.body().getMaster().get(0).getDeskripsi();
                        foto = response.body().getMaster().get(0).getFoto();
                        fasilitas = response.body().getMaster().get(0).getFasilitas();
                        latitude = response.body().getMaster().get(0).getLatitude();
                        longitude = response.body().getMaster().get(0).getLongitude();
                        id_pengelola = response.body().getMaster().get(0).getIdPengelola();
                        jumlah_lapangan = response.body().getMaster().get(0).getJumlahLapangan();

                        tvNamaGor.setText(nama_gor);
                        tvJumlahLapangan.setText("Jumlah Lapangan: " + jumlah_lapangan);
                        tvDeskripsi.setText(deskripsi);
                        tvAlamat.setText(alamat_gor);
                        tvFasilitas.setText("Fasilitas: "+fasilitas);


                        Glide.with(getApplicationContext())
                                .load(ServerConfig.GOR_IMAGE + foto)
                                .into(ivGor);

                        addMarker(nama_gor, new LatLng(latitude, longitude));


                        getAllLapangan(id_gor);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GorResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void getAllLapangan(String id_gor) {
        apiInterface.lapanganGetAll(id_gor).enqueue(new Callback<LapanganResponse>() {
            @Override
            public void onResponse(@NonNull Call<LapanganResponse> call, @NonNull Response<LapanganResponse> response) {
                System.out.println("responsenya"+response);
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        if (response.body().getMaster().size()>0){
                            lapanganArrayList.addAll(response.body().getMaster());

                            System.out.println("cek aja"+response.body().getMaster().size());

                            DaftarLapanganAdapter adapter = new DaftarLapanganAdapter(DetailGorActivity.this, lapanganArrayList);

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetailGorActivity.this,
                                    RecyclerView.HORIZONTAL, false);
                            rv_lapangan.setLayoutManager(layoutManager);
                            rv_lapangan.setItemAnimator(new DefaultItemAnimator());
                            rv_lapangan.setAdapter(adapter);
                        }else {
                            Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LapanganResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void addMarker(String nama_gor, LatLng latLng) {

        // Membuat marker
        MarkerOptions markerOptions = new MarkerOptions();

        // Mengatur posisi marker
        markerOptions.position(latLng);

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        System.out.println("latLng"+latLng);
        // Mengatur gambar ikon marker
        // Mengatur judul dari marker
        // Akan ditampilkan saat marker di klik
        markerOptions.title(nama_gor);

        // Placing a marker on the touched position
        mMap.addMarker(markerOptions);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(15);

        apiInterface.gorDetail(nama_gor).enqueue(new Callback<GorResponse>() {
            @Override
            public void onResponse(@NonNull Call<GorResponse> call, @NonNull Response<GorResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getMaster().size() > 0) {
                        latitude = response.body().getMaster().get(0).getLatitude();
                        longitude = response.body().getMaster().get(0).getLongitude();

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude,longitude)));

                        System.out.println("haiii"+latitude+" "+longitude);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GorResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                mMap.setMyLocationEnabled(true);
                                mMap.getUiSettings().setMyLocationButtonEnabled(true);

                                mMap.getUiSettings().setZoomControlsEnabled(true);
                                mMap.getUiSettings().setCompassEnabled(true);
                                return;
                            }

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }

                }).
                withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }
}
