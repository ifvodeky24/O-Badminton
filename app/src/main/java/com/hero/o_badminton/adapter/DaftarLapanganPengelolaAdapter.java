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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hero.o_badminton.R;
import com.hero.o_badminton.activity.UpdateLapanganActivity;
import com.hero.o_badminton.model.Lapangan;
import com.hero.o_badminton.response.HapusResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarLapanganPengelolaAdapter extends RecyclerView.Adapter<DaftarLapanganPengelolaAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Lapangan> lapangans;

    public DaftarLapanganPengelolaAdapter(Context context, ArrayList<Lapangan> lapangans) {
        this.context = context;
        this.lapangans = lapangans;
    }

    @NonNull
    @Override
    public DaftarLapanganPengelolaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lapangan_pengelola, viewGroup, false);
        return new DaftarLapanganPengelolaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarLapanganPengelolaAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.tvNamaGor.setText("Nama Gor: "+lapangans.get(position).getNamaGor());
        viewHolder.tvNomorLapangan.setText("Nomor Lapangan: "+lapangans.get(position).getNomorLapangan());
        viewHolder.tvHarga.setText("Tarif: "+lapangans.get(position).getHarga()+" / Jam");

        viewHolder.btn_perbarui.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateLapanganActivity.class);
            intent.putExtra("nama_gor", lapangans.get(position).getNamaGor());
            intent.putExtra("id_gor", lapangans.get(position).getIdGor());
            intent.putExtra("id_lapangan", lapangans.get(position).getIdLapangan());
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

                        apiInterface.hapusLapangan(lapangans.get(position).getIdLapangan()).enqueue(new Callback<HapusResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<HapusResponse> call, @NonNull Response<HapusResponse> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(context, "Data Berhasil dihapus", Toast.LENGTH_SHORT).show();

                                    lapangans.remove(position);
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
        return (lapangans != null) ? lapangans.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNamaGor, tvNomorLapangan, tvHarga;
        CardView cvLapangan;
        ImageView iv_hapus;
        Button btn_perbarui;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvLapangan = itemView.findViewById(R.id.cvLapangan);
            tvNamaGor = itemView.findViewById(R.id.tvNamaGor);
            tvNomorLapangan = itemView.findViewById(R.id.tvNomorLapangan);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            iv_hapus = itemView.findViewById(R.id.iv_hapus);
            btn_perbarui = itemView.findViewById(R.id.btn_perbarui);
        }
    }
}
