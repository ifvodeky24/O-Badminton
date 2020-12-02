package com.hero.o_badminton.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hero.o_badminton.R;
import com.hero.o_badminton.activity.PendingTopupActivity;
import com.hero.o_badminton.activity.ToUpActivity;
import com.hero.o_badminton.model.Topup;
import com.hero.o_badminton.util.SessionManager;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DaftarTopupAdapter extends RecyclerView.Adapter<DaftarTopupAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Topup> topupArrayList;

    public DaftarTopupAdapter(Context context, ArrayList<Topup> topupArrayList) {
        this.context = context;
        this.topupArrayList = topupArrayList;
    }

    @NonNull
    @Override
    public DaftarTopupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_topup, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarTopupAdapter.ViewHolder viewHolder, final int position) {
        DecimalFormat df = new DecimalFormat("#,###");

        viewHolder.tvIdPengguna.setText(String.valueOf("Id Pengguna: "+topupArrayList.get(position).getIdPengguna()));
        viewHolder.tvJumlah.setText(String.valueOf("Jumlah: "+"Rp. " + df.format(Double.valueOf(topupArrayList.get(position).getJumlah()))));
        viewHolder.tvStatus.setText(String.valueOf(topupArrayList.get(position).getStatus()));
        viewHolder.tvTanggal.setText(String.valueOf("Tanggal: "+topupArrayList.get(position).getCreatedAt()));



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topupArrayList.get(position).getStatus().equalsIgnoreCase("Pending")){
                    Intent intent = new Intent(context, PendingTopupActivity.class);
                    intent.putExtra("id_topup", String.valueOf(topupArrayList.get(position).getIdTopup()));
                    intent.putExtra("jumlah", String.valueOf(topupArrayList.get(position).getJumlah()));
                    context.startActivity(intent);
                }else {
                    Intent intent = new Intent(context, ToUpActivity.class);
                    intent.putExtra("id_topup", String.valueOf(topupArrayList.get(position).getIdTopup()));
                    intent.putExtra("id_pengguna", String.valueOf(topupArrayList.get(position).getIdPengguna()));
                    intent.putExtra("jumlah", String.valueOf(topupArrayList.get(position).getJumlah()));
                    intent.putExtra("tanggal", String.valueOf(topupArrayList.get(position).getCreatedAt()));
                    intent.putExtra("status", String.valueOf(topupArrayList.get(position).getStatus()));
                    context.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return (topupArrayList != null) ? topupArrayList.size() :0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvIdPengguna, tvJumlah, tvStatus, tvTanggal;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIdPengguna = itemView.findViewById(R.id.tvIdPengguna);
            tvJumlah = itemView.findViewById(R.id.tvJumlah);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
        }
    }
}
