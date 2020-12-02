package com.hero.o_badminton.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hero.o_badminton.R;
import com.hero.o_badminton.activity.GorActivity;
import com.hero.o_badminton.activity.LihatDataGorPengelolaActivity;
import com.hero.o_badminton.activity.LihatDataJadwalPengelolaActivity;
import com.hero.o_badminton.activity.LihatDataLapanganPengelolaActivity;
import com.hero.o_badminton.activity.LoginPengelolaActivity;
import com.hero.o_badminton.activity.LoginPenggunaActivity;
import com.hero.o_badminton.activity.PilihanIsiSaldoActivity;
import com.hero.o_badminton.adapter.GorAdapter;
import com.hero.o_badminton.model.Gor;
import com.hero.o_badminton.response.GorResponse;
import com.hero.o_badminton.response.PenggunaDetailResponse;
import com.hero.o_badminton.response.SumJadwalResponse;
import com.hero.o_badminton.response.UpdateStatusGorResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {

    private SliderLayout sliderLayout;
    private TextView tvGreeting, tvMasukDaftar, tvLihatSemua, tvMenuPengelola, tvSaldo, tvTopUp;
    private SessionManager sessionManager;
    private ApiInterface apiInterface;
    private RecyclerView rv_gor;
    private final ArrayList<Gor> gorArrayList = new ArrayList<>();
    private LinearLayout ll_menu_pengelola, ll_data_gor, ll_data_lapangan, ll_data_jadwal;
    private RelativeLayout rlSaldo;
    private DecimalFormat df;
    ArrayList<Integer> id_gorList = new ArrayList<>();


    public BerandaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getActivity());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        df = new DecimalFormat("#,###");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);

        Objects.requireNonNull(getActivity()).setTitle("O-Badminton");

        sliderLayout = view.findViewById(R.id.imageSlider);
        tvGreeting = view.findViewById(R.id.tvGreeting);
        tvMasukDaftar = view.findViewById(R.id.tvMasukDaftar);
        rv_gor = view.findViewById(R.id.rv_gor);
        tvLihatSemua = view.findViewById(R.id.tvLihatSemua);
        tvMenuPengelola = view.findViewById(R.id.tvMenuPengelola);
        ll_menu_pengelola = view.findViewById(R.id.ll_menu_pengelola);
        ll_data_gor = view.findViewById(R.id.ll_data_gor);
        ll_data_lapangan = view.findViewById(R.id.ll_data_lapangan);
        ll_data_jadwal = view.findViewById(R.id.ll_data_jadwal);
        tvSaldo = view.findViewById(R.id.tvSaldo);
        tvTopUp = view.findViewById(R.id.tvTopUp);
        rlSaldo = view.findViewById(R.id.rlSaldo);

        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL);
        sliderLayout.setScrollTimeInSec(6);

        setSliderViews();

        getGor();
        getGorAll();

        return view;
    }

    private void getGor() {
        apiInterface.gorGetAll().enqueue(new Callback<GorResponse>() {
            @Override
            public void onResponse(@NonNull Call<GorResponse> call, @NonNull Response<GorResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {

                            for (int i = 0; i < response.body().getMaster().size(); i++) {
                                Log.d("id_gor", String.valueOf(response.body().getMaster().get(i).getIdGor()));
                                id_gorList.add(response.body().getMaster().get(i).getIdGor());

                                for (int j = 0; j < id_gorList.size(); j++) {
                                    Log.d("id_gorListSize", String.valueOf(id_gorList.size()));
                                    int id_gor = id_gorList.get(j);
                                    apiInterface.sumJadwal(String.valueOf(id_gor)).enqueue(new Callback<SumJadwalResponse>() {
                                        @Override
                                        public void onResponse(@NonNull Call<SumJadwalResponse> call, @NonNull Response<SumJadwalResponse> responses) {
                                            if (response.isSuccessful()) {
                                                if (response.body() != null) {
                                                    Log.d("jumlah lapangan", responses.body().getMaster().get(0).getJumlahLapangan());

                                                    if (Integer.parseInt(responses.body().getMaster().get(0).getJumlahLapangan()) == 0) {

                                                        apiInterface.updateStatusGor(String.valueOf(id_gor), "Tidak Tersedia").enqueue(new Callback<UpdateStatusGorResponse>() {
                                                            @Override
                                                            public void onResponse(@NonNull Call<UpdateStatusGorResponse> call, @NonNull Response<UpdateStatusGorResponse> responsed) {
                                                                if (responsed.isSuccessful()) {
                                                                    Log.d("hasil akhir", responsed.body().getMessage());
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(@NonNull Call<UpdateStatusGorResponse> call, @NonNull Throwable t) {

                                                            }
                                                        });
                                                    } else if (Integer.parseInt(responses.body().getMaster().get(0).getJumlahLapangan()) > 0) {
                                                        apiInterface.updateStatusGor(String.valueOf(id_gor), "Tersedia").enqueue(new Callback<UpdateStatusGorResponse>() {
                                                            @Override
                                                            public void onResponse(@NonNull Call<UpdateStatusGorResponse> call, @NonNull Response<UpdateStatusGorResponse> responsed) {
                                                                if (responsed.isSuccessful()) {
                                                                    Log.d("hasil akhir", responsed.body().getMessage());
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(@NonNull Call<UpdateStatusGorResponse> call, @NonNull Throwable t) {

                                                            }
                                                        });
                                                    }


                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<SumJadwalResponse> call, @NonNull Throwable t) {

                                        }
                                    });
                                }
                            }

//                            GorAdapter adapter = new GorAdapter(getContext(), gorArrayList);
//
//                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
//                                    RecyclerView.HORIZONTAL, false);
//                            rv_gor.setLayoutManager(layoutManager);
//                            rv_gor.setItemAnimator(new DefaultItemAnimator());
//                            rv_gor.setAdapter(adapter);
                        } else {
                            Toast.makeText(getContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }


                } else {
                    Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GorResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void getGorAll() {
        apiInterface.gorGetAll().enqueue(new Callback<GorResponse>() {
            @Override
            public void onResponse(Call<GorResponse> call, Response<GorResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        gorArrayList.addAll(response.body().getMaster());
                        if (response.body().getMaster().size() > 0) {


                            for (int i = 0; i < response.body().getMaster().size(); i++) {

                                GorAdapter adapter = new GorAdapter(getContext(), gorArrayList);

                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                                        RecyclerView.HORIZONTAL, false);
                                rv_gor.setLayoutManager(layoutManager);
                                rv_gor.setItemAnimator(new DefaultItemAnimator());
                                rv_gor.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GorResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvLihatSemua.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), GorActivity.class);
            startActivity(intent);
        });

        if (sessionManager.isLoggedin()) {
            if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGGUNA)) {
                tvMasukDaftar.setText("Lapangan seperti apa yang ingin anda cari hari ini?");
                ll_menu_pengelola.setVisibility(View.GONE);
                tvMenuPengelola.setVisibility(View.GONE);
                rlSaldo.setVisibility(View.VISIBLE);

                apiInterface.penggunaDetail(sessionManager.getLoginDetail().get(SessionManager.ID)).enqueue(new Callback<PenggunaDetailResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<PenggunaDetailResponse> call, @NonNull Response<PenggunaDetailResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getMaster().size() > 0) {
                                    tvSaldo.setText("Rp. " + df.format(Double.valueOf(response.body().getMaster().get(0).getTotalSaldo())));
                                } else {
                                    Toast.makeText(getContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PenggunaDetailResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });

                tvTopUp.setOnClickListener(view -> {
                    Intent intent = new Intent(getActivity(), PilihanIsiSaldoActivity.class);
                    startActivity(intent);
                });

            } else if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGELOLA)) {
                tvMasukDaftar.setText("Apa kabar anda pengelola?");
                tvLihatSemua.setVisibility(View.GONE);
                rv_gor.setVisibility(View.GONE);
                ll_menu_pengelola.setVisibility(View.VISIBLE);
                tvMenuPengelola.setVisibility(View.VISIBLE);
                rlSaldo.setVisibility(View.GONE);

                ll_data_gor.setOnClickListener(view -> {
                    Intent intent = new Intent(getActivity(), LihatDataGorPengelolaActivity.class);
                    startActivity(intent);
                });

                ll_data_lapangan.setOnClickListener(view -> {
                    Intent intent = new Intent(getActivity(), LihatDataLapanganPengelolaActivity.class);
                    startActivity(intent);
                });

                ll_data_jadwal.setOnClickListener(view -> {
                    Intent intent = new Intent(getActivity(), LihatDataJadwalPengelolaActivity.class);
                    startActivity(intent);
                });

            }

            tvGreeting.setText("Selamat Datang, " + sessionManager.getLoginDetail().get(SessionManager.NAMA_LENGKAP));

        } else if (!sessionManager.isLoggedin()) {
            tvMasukDaftar.setOnClickListener(view -> {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                alertDialogBuilder.setCancelable(true);

                LayoutInflater inflater = getLayoutInflater();

                View dialogView = inflater.inflate(R.layout.dialog_login, null);

                alertDialogBuilder.setView(dialogView);

                Button btn_pengguna = dialogView.findViewById(R.id.btn_pengguna);
                TextView btn_pengelola = dialogView.findViewById(R.id.btn_pengelola);

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                btn_pengguna.setOnClickListener(view1 -> {
                    Intent intent = new Intent(getActivity(), LoginPenggunaActivity.class);
                    getActivity().finish();
                    startActivity(intent);
                });

                btn_pengelola.setOnClickListener(view12 -> {
                    Intent intent = new Intent(getActivity(), LoginPengelolaActivity.class);
                    getActivity().finish();
                    startActivity(intent);
                });

                ll_menu_pengelola.setVisibility(View.GONE);
                tvMenuPengelola.setVisibility(View.GONE);
                rlSaldo.setVisibility(View.GONE);
            });
        }
    }

    private void setSliderViews() {
        for (int i = 0; i <= 3; i++) {
            SliderView sliderView = new SliderView(getActivity());

            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.slider1);
                    break;

                case 1:
                    sliderView.setImageDrawable(R.drawable.slider2);
                    break;

                case 2:
                    sliderView.setImageDrawable(R.drawable.slider3);
                    break;

                case 3:
                    sliderView.setImageDrawable(R.drawable.slider4);
                    break;

            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

            sliderView.setOnSliderClickListener(sliderView1 -> {

            });

            sliderLayout.addSliderView(sliderView);
        }
    }

}