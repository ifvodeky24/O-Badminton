package com.hero.o_badminton.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hero.o_badminton.R;
import com.hero.o_badminton.activity.MainActivity;
import com.hero.o_badminton.activity.PilihanIsiSaldoActivity;
import com.hero.o_badminton.activity.UbahAkunActivity;
import com.hero.o_badminton.activity.UbahPasswordActivity;
import com.hero.o_badminton.config.ServerConfig;
import com.hero.o_badminton.response.PengelolaDetailResponse;
import com.hero.o_badminton.response.PenggunaDetailResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AkunFragment extends Fragment implements View.OnClickListener {

    private CircleImageView iv_user_images;
    private TextView tv_namalengkap, tv_email, tv_noHp;
    private SessionManager sessionManager;
    private ApiInterface apiInterface;

    public AkunFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_akun, container, false);

        Objects.requireNonNull(getActivity()).setTitle("Akun");

        LinearLayout ll_pengaturan_akun = view.findViewById(R.id.ll_pengaturan_akun);
        LinearLayout ll_pengaturan_password = view.findViewById(R.id.ll_pengaturan_password);
        LinearLayout keluar = view.findViewById(R.id.keluar);

        iv_user_images = view.findViewById(R.id.iv_user_images);
        tv_namalengkap = view.findViewById(R.id.tv_namalengkap);
        tv_email = view.findViewById(R.id.tv_email);
        tv_noHp = view.findViewById(R.id.tv_noHp);

        sessionManager = new SessionManager(getActivity());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if (sessionManager.isLoggedin()) {
            tv_namalengkap.setText(sessionManager.getLoginDetail().get(SessionManager.NAMA_LENGKAP));
            tv_email.setText(sessionManager.getLoginDetail().get(SessionManager.EMAIL));
            tv_noHp.setText(sessionManager.getLoginDetail().get(SessionManager.NO_HP));

            if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGGUNA)) {
                apiInterface.penggunaDetail(sessionManager.getLoginDetail().get(SessionManager.ID)).enqueue(new Callback<PenggunaDetailResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<PenggunaDetailResponse> call, @NonNull Response<PenggunaDetailResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getMaster().size() > 0) {
                                    String saldo = response.body().getMaster().get(0).getTotalSaldo();

                                    Glide.with(requireActivity())
                                            .load(ServerConfig.PENGGUNA_IMAGE + response.body().getMaster().get(0).getFoto())
                                            .into(iv_user_images);

                                } else {
                                    Toast.makeText(getContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PenggunaDetailResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });



            } else if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGELOLA)) {
                apiInterface.pengelolaDetail(sessionManager.getLoginDetail().get(SessionManager.ID)).enqueue(new Callback<PengelolaDetailResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<PengelolaDetailResponse> call, @NonNull Response<PengelolaDetailResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getMaster().size() > 0) {
                                    String saldo = response.body().getMaster().get(0).getFoto();

                                    Glide.with(Objects.requireNonNull(getActivity()))
                                            .load(ServerConfig.PENGELOLA_IMAGE + response.body().getMaster().get(0).getFoto())
                                            .into(iv_user_images);

                                } else {
                                    Toast.makeText(getContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PengelolaDetailResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
        }

        //set on click
        ll_pengaturan_akun.setOnClickListener(this);
        ll_pengaturan_password.setOnClickListener(this);
        keluar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_pengaturan_akun) {
            Intent intent = new Intent(getActivity(), UbahAkunActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.ll_pengaturan_password) {
            Intent intent = new Intent(getActivity(), UbahPasswordActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.keluar) {
            sessionManager.logout();
            Intent intent_logout = new Intent(getActivity(), MainActivity.class);
            intent_logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            Objects.requireNonNull(getActivity()).finish();
            startActivity(intent_logout);
            Toast.makeText(getActivity(), "Anda Berhasil Keluar", Toast.LENGTH_SHORT).show();
        }

    }

}
