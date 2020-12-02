package com.hero.o_badminton.fragment;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hero.o_badminton.R;
import com.hero.o_badminton.activity.DetailGorActivity;
import com.hero.o_badminton.model.Gor;
import com.hero.o_badminton.response.GorResponse;
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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapGorFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ApiInterface apiService;

    private Double lat;
    private Double lng;
    private String nama;
    private int id;
    private SessionManager sessionManager;
    String status;

    private ArrayList<Gor> gorArrayList = new ArrayList<>();
    ArrayList<Status> statusList = new ArrayList<>();


    public MapGorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_map_gor, container, false);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(getActivity());

        final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.maps_gor);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        getGor();


        LocationManager locationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(LOCATION_SERVICE);

        if (locationManager != null) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(getActivity(), "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
            } else {
                showGPSDisabledAlertToUser();
            }
        }

        return mView;
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void getGor() {
        apiService.gorGetAll().enqueue(new Callback<GorResponse>() {
            @Override
            public void onResponse(@NonNull Call<GorResponse> call, @NonNull Response<GorResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {


                        for (int i = 0; i < response.body().getMaster().size(); i++) {
                            id = response.body().getMaster().get(i).getIdGor();
                            nama = response.body().getMaster().get(i).getNamaGor();
                            lat = response.body().getMaster().get(i).getLatitude();
                            lng = response.body().getMaster().get(i).getLongitude();
                            status = response.body().getMaster().get(i).getStatus();

                            Status statusModel = new Status();
                            statusModel.setId_gor(id);
                            statusModel.setNama_gor(nama);
                            statusModel.setStatus(status);
                            statusList.add(statusModel);

                            System.out.println("statusList" + statusList.toString());

                            addMarker(nama, status, new LatLng(lat, lng));


                            System.out.println("Menambahkan Marker " + nama + " " + id);

                        }

                    }
                } else {
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GorResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Gagal Konek ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addMarker(String nama, String status_gor, LatLng latLng) {
//        BitmapDescriptor icon;
//
//        icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_kos);

        // Membuat marker
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(nama);

        System.out.println("statusList2" + statusList.size());

        for (int i = 0; i < statusList.size(); i++) {
//            System.out.println("namaaa"+ statusList.get(i).nama_gor);

            if (statusList.get(i).status.equals("Tersedia")) {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            } else {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            }


        }

        // Placing a marker on the touched position
        Marker marker = mMap.addMarker(markerOptions);
        marker.setTag(status_gor);


        mMap.setOnInfoWindowClickListener(markers -> {
            if (markers.getTag().equals("Tersedia")) {
                System.out.println("status latLng" + markers.getTag() + markers.getTitle());

                Intent intent = new Intent(getContext(), DetailGorActivity.class);
                intent.putExtra("nama_gor", markers.getTitle());
                startActivity(intent);
            } else {
                System.out.println("status latLng" + status + nama);

                Toast.makeText(getContext(), "Jadwal Penuh", Toast.LENGTH_SHORT).show();
            }

        });


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        mMap.setMinZoomPreference(25);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(0.507068, 101.447779)));


//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                if(marker.getTitle().equals(nama)){
//                    Toast.makeText(getContext(), "klik"+id, Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                return false;
//            }
//        });

        Dexter.withActivity(getActivity())
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
                            mMap.setMyLocationEnabled(true);
                            mMap.getUiSettings().setMyLocationButtonEnabled(true);

                            mMap.getUiSettings().setZoomControlsEnabled(true);
                            mMap.getUiSettings().setCompassEnabled(true);
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
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


}

class Status {
    Integer id_gor;
    String nama_gor;
    String status;

    public Integer getId_gor() {
        return id_gor;
    }

    public void setId_gor(Integer id_gor) {
        this.id_gor = id_gor;
    }

    public String getNama_gor() {
        return nama_gor;
    }

    public void setNama_gor(String nama_gor) {
        this.nama_gor = nama_gor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
