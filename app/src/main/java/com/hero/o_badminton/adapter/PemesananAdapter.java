package com.hero.o_badminton.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hero.o_badminton.R;
import com.hero.o_badminton.activity.DetailPemesananActivity;
import com.hero.o_badminton.config.ServerConfig;
import com.hero.o_badminton.model.PemesananGetAll;

import java.util.ArrayList;

public class PemesananAdapter extends RecyclerView.Adapter<PemesananAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PemesananGetAll> pemesananGetAlls;

    public PemesananAdapter(Context context, ArrayList<PemesananGetAll> pemesananGetAlls) {
        this.context = context;
        this.pemesananGetAlls = pemesananGetAlls;
    }

    @NonNull
    @Override
    public PemesananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pemesanan, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PemesananAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.tvIdPemesanan.setText("Pemesanan ke- "+pemesananGetAlls.get(position).getIdPemesanan());
        viewHolder.tvNamaGor.setText("Nama Gor: "+pemesananGetAlls.get(position).getNamaGor());
        viewHolder.tvNomorLapangan.setText("Nomor Lapangan: "+pemesananGetAlls.get(position).getNomorLapangan());
        viewHolder.tvStatus.setText(pemesananGetAlls.get(position).getStatus());

        Glide.with(context)
                .load(ServerConfig.GOR_IMAGE + pemesananGetAlls.get(position).getFoto())
                .into(viewHolder.ivGor);

        System.out.println("cek" + ServerConfig.GOR_IMAGE + pemesananGetAlls.get(position).getFoto());

        viewHolder.ivGor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailPemesananActivity.class);
                intent.putExtra("id_pemesanan", pemesananGetAlls.get(position).getIdPemesanan());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (pemesananGetAlls != null) ? pemesananGetAlls.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGor;
        TextView tvIdPemesanan, tvNamaGor, tvNomorLapangan, tvStatus;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivGor = itemView.findViewById(R.id.ivGor);
            tvIdPemesanan = itemView.findViewById(R.id.tvIdPemesanan);
            tvNamaGor = itemView.findViewById(R.id.tvNamaGor);
            tvNomorLapangan = itemView.findViewById(R.id.tvNomorLapangan);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
