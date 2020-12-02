package com.hero.o_badminton.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hero.o_badminton.R;
import com.hero.o_badminton.adapter.DaftarGorAdapter;
import com.hero.o_badminton.adapter.GorAdapter;
import com.hero.o_badminton.model.Gor;
import com.hero.o_badminton.response.GorResponse;
import com.hero.o_badminton.response.SumJadwalResponse;
import com.hero.o_badminton.response.UpdateStatusGorResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarGorFragment extends Fragment {
    private RecyclerView rv_gor;
    private DaftarGorAdapter adapter;
    private ArrayList<Gor> gorArrayList = new ArrayList<>();
    private ApiInterface apiService;
    ArrayList<Integer> id_gorList = new ArrayList<>();

    private SearchView searchView;

    public DaftarGorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daftar_gor, container, false);

        rv_gor = view.findViewById(R.id.recyclerView);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        getGor();
        getGorAll();

        setHasOptionsMenu(true);

        return view;
    }

    private void getGor() {
        apiService.gorGetAll().enqueue(new Callback<GorResponse>() {
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
                                    apiService.sumJadwal(String.valueOf(id_gor)).enqueue(new Callback<SumJadwalResponse>() {
                                        @Override
                                        public void onResponse(@NonNull Call<SumJadwalResponse> call, @NonNull Response<SumJadwalResponse> responses) {
                                            if (response.isSuccessful()) {
                                                if (response.body() != null) {
                                                    Log.d("jumlah lapangan", responses.body().getMaster().get(0).getJumlahLapangan());

                                                    if (Integer.parseInt(responses.body().getMaster().get(0).getJumlahLapangan()) == 0) {

                                                        apiService.updateStatusGor(String.valueOf(id_gor), "Tidak Tersedia").enqueue(new Callback<UpdateStatusGorResponse>() {
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
                                                        apiService.updateStatusGor(String.valueOf(id_gor), "Tersedia").enqueue(new Callback<UpdateStatusGorResponse>() {
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
        apiService.gorGetAll().enqueue(new Callback<GorResponse>() {
            @Override
            public void onResponse(Call<GorResponse> call, Response<GorResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        gorArrayList.addAll(response.body().getMaster());
                        if (response.body().getMaster().size() > 0) {

                            for (int i = 0; i < response.body().getMaster().size(); i++) {

                                DaftarGorAdapter adapter = new DaftarGorAdapter(getContext(), gorArrayList);

                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                                        RecyclerView.VERTICAL, false);
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.options_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);

        SearchManager searchManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);
        }

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }

        if (searchView != null) {
            assert searchManager != null;
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean onQueryTextSubmit(String query) {
                    gorArrayList.clear();

                    apiService.cariGor(query).enqueue(new Callback<GorResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<GorResponse> call, @NonNull Response<GorResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    if (response.body().getMaster().size() > 0) {
                                        gorArrayList.addAll(response.body().getMaster());

                                        adapter = new DaftarGorAdapter(getContext(), gorArrayList);

                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                        rv_gor.setLayoutManager(layoutManager);
                                        rv_gor.setItemAnimator(new DefaultItemAnimator());
                                        rv_gor.setAdapter(adapter);
                                    } else {
                                        Toast.makeText(getActivity(), "Data Gor Kosong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(getActivity(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<GorResponse> call, @NonNull Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(getActivity(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                        }
                    });

                    return true;
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean onQueryTextChange(String newText) {
                    gorArrayList.clear();

                    apiService.cariGor(newText).enqueue(new Callback<GorResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<GorResponse> call, @NonNull Response<GorResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    if (response.body().getMaster().size() > 0) {
                                        gorArrayList.addAll(response.body().getMaster());

                                        adapter = new DaftarGorAdapter(getContext(), gorArrayList);

                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                        rv_gor.setLayoutManager(layoutManager);
                                        rv_gor.setItemAnimator(new DefaultItemAnimator());
                                        rv_gor.setAdapter(adapter);
                                    } else {
                                        Toast.makeText(getActivity(), "Data Gor Kosong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(getActivity(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<GorResponse> call, @NonNull Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(getActivity(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                        }
                    });

                    return true;
                }
            });
        }

    }

}
