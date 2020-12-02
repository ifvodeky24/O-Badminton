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
import com.hero.o_badminton.adapter.DaftarTopupAdapter;
import com.hero.o_badminton.model.Topup;
import com.hero.o_badminton.response.GetTopupResponse;
import com.hero.o_badminton.response.TopupResponse;
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
public class RiwayatTopupFragment extends Fragment {
    private RecyclerView recylerView;
    private DaftarTopupAdapter topupAdapter;
    private ArrayList<Topup> topupArrayList = new ArrayList<>();

    private ApiInterface apiInterface;
    private SessionManager sessionManager;
    private LinearLayout ll_data_pesanan_kosong;

    String id_pengguna;

    public RiwayatTopupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riwayat_topup, container, false);

        recylerView = view.findViewById(R.id.recylerView1);
        ll_data_pesanan_kosong = view.findViewById(R.id.ll_data_pesanan_kosong);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(getActivity());

        if (sessionManager.isLoggedin()) {
            if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGGUNA)) {
                id_pengguna = sessionManager.getLoginDetail().get(SessionManager.ID);

                getDataRiwayatTopup(id_pengguna);
            }
        }

        return view;
    }

    private void getDataRiwayatTopup(String id_pengguna) {
        apiInterface.getTopup(id_pengguna).enqueue(new Callback<GetTopupResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetTopupResponse> call, @NonNull Response<GetTopupResponse> response) {
                System.out.println("apa ini"+response);
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {
                            ll_data_pesanan_kosong.setVisibility(View.GONE);
                            topupArrayList.addAll(response.body().getMaster());

                            System.out.println(response.body().getMaster().get(0).getIdTopup());

                            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                            recylerView.setLayoutManager(manager);
                            recylerView.setHasFixedSize(true);
                            topupAdapter = new DaftarTopupAdapter(getActivity(), topupArrayList);
                            recylerView.setAdapter(topupAdapter);
                        } else {
                            Toast.makeText(getActivity(), "Data Topup Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(getActivity(), "terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetTopupResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
