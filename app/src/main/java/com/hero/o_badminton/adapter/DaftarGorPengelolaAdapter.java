package com.hero.o_badminton.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hero.o_badminton.R;
import com.hero.o_badminton.activity.DetailGorActivity;
import com.hero.o_badminton.activity.UpdateGorActivity;
import com.hero.o_badminton.config.ServerConfig;
import com.hero.o_badminton.model.Gor;
import com.hero.o_badminton.response.HapusResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarGorPengelolaAdapter extends RecyclerView.Adapter<DaftarGorPengelolaAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Gor> gors;

    public DaftarGorPengelolaAdapter(Context context, ArrayList<Gor> gors) {
        this.context = context;
        this.gors = gors;
    }

    @NonNull
    @Override
    public DaftarGorPengelolaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_daftar_gor_pengelola, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarGorPengelolaAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.tvNamaGor.setText(gors.get(position).getNamaGor());
        viewHolder.tvAlamat.setText(gors.get(position).getAlamatGor());
        viewHolder.tvJumlahLapangan.setText("Jumlah Lapangan: "+gors.get(position).getJumlahLapangan());

        Glide.with(context)
                .load(ServerConfig.GOR_IMAGE + gors.get(position).getFoto())
                .into(viewHolder.ivGor);

        System.out.println("cek" + ServerConfig.GOR_IMAGE + gors.get(position).getFoto());

        viewHolder.ivGor.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailGorActivity.class);
            intent.putExtra("nama_gor", gors.get(position).getNamaGor());
            context.startActivity(intent);
        });

        viewHolder.btn_perbarui.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateGorActivity.class);
            intent.putExtra("id_gor", String.valueOf(gors.get(position).getIdGor()));
            intent.putExtra("latitude", String.valueOf(gors.get(position).getLatitude()));
            intent.putExtra("longitude", String.valueOf(gors.get(position).getLongitude()));

            System.out.println("hmmmm"+gors.get(position).getIdGor());
            context.startActivity(intent);
        });

        viewHolder.iv_hapus.setOnClickListener(view -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            //set pesan dari dialog
            alertDialogBuilder
                    .setMessage("Anda ingin menghapus item ini?")
                    .setCancelable(false)
                    .setPositiveButton("Hapus", (dialog, which) -> {
                        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        SessionManager sessionManager = new SessionManager(context);

                        apiInterface.hapusGor(String.valueOf(gors.get(position).getIdGor())).enqueue(new Callback<HapusResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<HapusResponse> call, @NonNull Response<HapusResponse> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(context, "Data Berhasil dihapus", Toast.LENGTH_SHORT).show();

                                    gors.remove(position);
                                    notifyItemRemoved(position);
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<HapusResponse> call, @NonNull Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    })
                    .setNegativeButton("Batal", (dialog, which) -> dialog.cancel());

            //membuat alert dialog dari builder
            AlertDialog alertDialog = alertDialogBuilder.create();

            //menampilkan alert dialog
            alertDialog.show();
        });

    }

    @Override
    public int getItemCount() {
        return (gors != null) ? gors.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGor, iv_hapus;
        TextView tvNamaGor, tvAlamat, tvJumlahLapangan;
        Button btn_perbarui;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivGor = itemView.findViewById(R.id.ivGor);
            tvNamaGor = itemView.findViewById(R.id.tvNamaGor);
            tvAlamat = itemView.findViewById(R.id.tvAlamat);
            tvJumlahLapangan = itemView.findViewById(R.id.tvJumlahLapangan);
            iv_hapus = itemView.findViewById(R.id.iv_hapus);
            btn_perbarui = itemView.findViewById(R.id.btn_perbarui);
        }
    }
}
