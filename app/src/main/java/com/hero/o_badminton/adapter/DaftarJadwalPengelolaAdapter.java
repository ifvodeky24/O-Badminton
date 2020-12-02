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
import com.hero.o_badminton.activity.UpdateJadwalActivity;
import com.hero.o_badminton.model.Jadwal;
import com.hero.o_badminton.response.HapusResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarJadwalPengelolaAdapter extends RecyclerView.Adapter<DaftarJadwalPengelolaAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Jadwal> jadwals;

    public DaftarJadwalPengelolaAdapter(Context context, ArrayList<Jadwal> jadwals) {
        this.context = context;
        this.jadwals = jadwals;
    }

    @NonNull
    @Override
    public DaftarJadwalPengelolaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jadwal_pengelola, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarJadwalPengelolaAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.tvNamaGor.setText("Nama Gor: "+jadwals.get(position).getNamaGor());
        viewHolder.tvNomorLapangan.setText("Nomor Lapangan: "+jadwals.get(position).getNomorLapangan());
        viewHolder.tvHari.setText("Hari: "+jadwals.get(position).getHari());
        viewHolder.tvPukul.setText("Pukul: "+jadwals.get(position).getJam());

        viewHolder.btn_perbarui.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateJadwalActivity.class);
            intent.putExtra("id_jadwal", jadwals.get(position).getIdJadwal());
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

                        apiInterface.hapusJadwal(jadwals.get(position).getIdJadwal()).enqueue(new Callback<HapusResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<HapusResponse> call, @NonNull Response<HapusResponse> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(context, "Data Berhasil dihapus", Toast.LENGTH_SHORT).show();

                                    jadwals.remove(position);
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
        return (jadwals != null) ? jadwals.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNamaGor, tvNomorLapangan, tvHari, tvPukul;
        CardView cvJadwal;
        ImageView iv_hapus;
        Button btn_perbarui;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvJadwal = itemView.findViewById(R.id.cvJadwal);
            tvNamaGor = itemView.findViewById(R.id.tvNamaGor);
            tvNomorLapangan = itemView.findViewById(R.id.tvNomorLapangan);
            tvHari = itemView.findViewById(R.id.tvHari);
            tvPukul = itemView.findViewById(R.id.tvPukul);
            iv_hapus = itemView.findViewById(R.id.iv_hapus);
            btn_perbarui = itemView.findViewById(R.id.btn_perbarui);
        }
    }
}
