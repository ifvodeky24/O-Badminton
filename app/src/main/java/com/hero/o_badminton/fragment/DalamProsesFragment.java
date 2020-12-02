package com.hero.o_badminton.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hero.o_badminton.R;
import com.hero.o_badminton.adapter.PemesananAdapter;
import com.hero.o_badminton.model.PemesananGetAll;
import com.hero.o_badminton.response.PemesananDetailResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DalamProsesFragment extends Fragment {
    private RecyclerView recylerView;
    private PemesananAdapter pemesananAdapter;
    private ArrayList<PemesananGetAll> pemesananGetAllArrayList = new ArrayList<>();

    private ApiInterface apiInterface;
    private SessionManager sessionManager;
    private LinearLayout ll_data_pesanan_kosong;

    String id_pengguna, id_pengelola;


    public DalamProsesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dalam_proses, container, false);

        recylerView = view.findViewById(R.id.recylerView1);
        ll_data_pesanan_kosong = view.findViewById(R.id.ll_data_pesanan_kosong);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(getActivity());


        if (sessionManager.isLoggedin()) {
            if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGGUNA)) {
                id_pengguna = sessionManager.getLoginDetail().get(SessionManager.ID);

                getDataPesananMenungguKonfirmasiPengguna(id_pengguna);

                getDataPesananSedangMemesanPengguna(id_pengguna);
            }else if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGELOLA)){
                id_pengelola = sessionManager.getLoginDetail().get(SessionManager.ID);

                getDataPesananMenungguKonfirmasiPengelola(id_pengelola);

                getDataPesananSedangMemesanPengelola(id_pengelola);

            }
        }

        return view;
    }

    private void getDataPesananMenungguKonfirmasiPengelola(String id_pengelola) {
        apiInterface.pemesananGetAllByPengelolaMenungguKonfirmasi(id_pengelola).enqueue(new Callback<PemesananDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<PemesananDetailResponse> call, @NonNull Response<PemesananDetailResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {
                            ll_data_pesanan_kosong.setVisibility(View.GONE);
                            pemesananGetAllArrayList.addAll(response.body().getMaster());

                            System.out.println(response.body().getMaster().get(0).getIdPemesanan());

                            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                            recylerView.setLayoutManager(manager);
                            recylerView.setHasFixedSize(true);
                            pemesananAdapter = new PemesananAdapter(getActivity(), pemesananGetAllArrayList);
                            recylerView.setAdapter(pemesananAdapter);
                        } else {
//                            Toast.makeText(getActivity(), "Data Pesanan Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(getActivity(), "terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PemesananDetailResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataPesananSedangMemesanPengelola(String id_pengelola) {
        apiInterface.pemesananGetAllByPengelolaSedangMemesan(id_pengelola).enqueue(new Callback<PemesananDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<PemesananDetailResponse> call, @NonNull Response<PemesananDetailResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {
                            ll_data_pesanan_kosong.setVisibility(View.GONE);
                            pemesananGetAllArrayList.addAll(response.body().getMaster());

                            System.out.println(response.body().getMaster().get(0).getIdPemesanan());

                            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                            recylerView.setLayoutManager(manager);
                            recylerView.setHasFixedSize(true);
                            pemesananAdapter = new PemesananAdapter(getActivity(), pemesananGetAllArrayList);
                            recylerView.setAdapter(pemesananAdapter);
                        } else {
//                            Toast.makeText(getActivity(), "Data Pesanan Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(getActivity(), "terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PemesananDetailResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataPesananSedangMemesanPengguna(String id_pengguna) {
        apiInterface.pemesananGetAllByPenggunaSedangMemesan(id_pengguna).enqueue(new Callback<PemesananDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<PemesananDetailResponse> call, @NonNull Response<PemesananDetailResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {
                            ll_data_pesanan_kosong.setVisibility(View.GONE);
                            pemesananGetAllArrayList.addAll(response.body().getMaster());

                            System.out.println(response.body().getMaster().get(0).getIdPemesanan());

                            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                            recylerView.setLayoutManager(manager);
                            recylerView.setHasFixedSize(true);
                            pemesananAdapter = new PemesananAdapter(getActivity(), pemesananGetAllArrayList);
                            recylerView.setAdapter(pemesananAdapter);
                        } else {
//                            Toast.makeText(getActivity(), "Data Pesanan Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(getActivity(), "terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PemesananDetailResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataPesananMenungguKonfirmasiPengguna(String id_pengguna) {
        apiInterface.pemesananGetAllByPenggunaMenungguKonfirmasi(id_pengguna).enqueue(new Callback<PemesananDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<PemesananDetailResponse> call, @NonNull Response<PemesananDetailResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {
                            ll_data_pesanan_kosong.setVisibility(View.GONE);
                            pemesananGetAllArrayList.addAll(response.body().getMaster());

                            System.out.println(response.body().getMaster().get(0).getIdPemesanan());

                            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                            recylerView.setLayoutManager(manager);
                            recylerView.setHasFixedSize(true);
                            pemesananAdapter = new PemesananAdapter(getActivity(), pemesananGetAllArrayList);
                            recylerView.setAdapter(pemesananAdapter);
                        } else {
//                            Toast.makeText(getActivity(), "Data Pesanan Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(getActivity(), "terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PemesananDetailResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
