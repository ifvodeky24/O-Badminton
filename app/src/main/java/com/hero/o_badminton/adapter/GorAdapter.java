package com.hero.o_badminton.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.florent37.shapeofview.shapes.RoundRectView;
import com.hero.o_badminton.R;
import com.hero.o_badminton.activity.DetailGorActivity;
import com.hero.o_badminton.config.ServerConfig;
import com.hero.o_badminton.model.Gor;
import com.hero.o_badminton.model.Jadwal;
import com.hero.o_badminton.response.JadwalResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GorAdapter extends RecyclerView.Adapter<GorAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Gor> gors;

    public GorAdapter(Context context, ArrayList<Gor> gors) {
        this.context = context;
        this.gors = gors;
    }

    @NonNull
    @Override
    public GorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gor, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GorAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.setIsRecyclable(false);
        viewHolder.tv_nama_gor.setText(gors.get(viewHolder.getAdapterPosition()).getNamaGor());

        Glide.with(context)
                .load(ServerConfig.GOR_IMAGE + gors.get(viewHolder.getAdapterPosition()).getFoto())
                .into(viewHolder.iv_gor);

        Log.d("status", gors.get(position).getStatus());

        if (gors.get(viewHolder.getAdapterPosition()).getStatus().equals("Tersedia")) {
            viewHolder.rrv_full.setVisibility(View.GONE);

            ApiInterface apiInterface;

            apiInterface = ApiClient.getClient().create(ApiInterface.class);

            apiInterface.jadwalByIdGor(String.valueOf(gors.get(viewHolder.getAdapterPosition()).getIdGor())).enqueue(new Callback<JadwalResponse>() {
                @Override
                public void onResponse(Call<JadwalResponse> call, Response<JadwalResponse> response) {
                    if (response.isSuccessful()) {

                        if (response.body() != null && response.body().getMaster().size() > 0) {

                            viewHolder.ll_jadwal.removeAllViews();

                            for (int i = 0; i < response.body().getMaster().size(); i++) {
                                LayoutInflater inflater = LayoutInflater.from(context);
                                View view = inflater.inflate(R.layout.item_jadwal_tersedia, null, true);

                                ArrayList<Jadwal> jadwalArrayList = new ArrayList<>();

                                TextView tv_tanggal = view.findViewById(R.id.tv_tanggal);
                                String id_jadwal = response.body().getMaster().get(i).getIdJadwal();
                                System.out.println("id_jadwal " + id_jadwal);
                                jadwalArrayList.add(response.body().getMaster().get(i));
                                System.out.println("cek jadwalArrayList " + jadwalArrayList);

                                String jadwal = "Hari: " + response.body().getMaster().get(i).getHari() + " " + response.body().getMaster().get(i).getJam() + " WIB No Lap: " + response.body().getMaster().get(i).getNomorLapangan();

                                tv_tanggal.setText(jadwal);

                                viewHolder.ll_jadwal.addView(view);
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<JadwalResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            viewHolder.rrv_full.setVisibility(View.VISIBLE);
        }

        viewHolder.iv_gor.setOnClickListener(view -> {
            if (gors.get(viewHolder.getAdapterPosition()).getStatus().equals("Tersedia")) {
                System.out.println("cek status" + gors.get(viewHolder.getAdapterPosition()).getStatus());
                System.out.println("cek id gor" + gors.get(viewHolder.getAdapterPosition()).getIdGor());
                Intent intent = new Intent(context, DetailGorActivity.class);
                intent.putExtra("nama_gor", gors.get(viewHolder.getAdapterPosition()).getNamaGor());
                context.startActivity(intent);
            } else {

            }

        });


    }

    @Override
    public int getItemCount() {
        return (gors != null) ? gors.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_gor;
        TextView tv_nama_gor;
        RoundRectView rrv_full;
        LinearLayout ll_jadwal;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_gor = itemView.findViewById(R.id.iv_gor);
            tv_nama_gor = itemView.findViewById(R.id.tv_nama_gor);
            rrv_full = itemView.findViewById(R.id.rrv_full);
            ll_jadwal = itemView.findViewById(R.id.ll_jadwal);
        }
    }
}
