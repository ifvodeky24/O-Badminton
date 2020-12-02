package com.hero.o_badminton.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.hero.o_badminton.R;
import com.hero.o_badminton.activity.LoginPengelolaActivity;
import com.hero.o_badminton.activity.LoginPenggunaActivity;
import com.hero.o_badminton.activity.PemesananActivity;
import com.hero.o_badminton.model.Lapangan;
import com.hero.o_badminton.util.SessionManager;

import java.util.ArrayList;
import java.util.Objects;

public class DaftarLapanganAdapter extends RecyclerView.Adapter<DaftarLapanganAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Lapangan> lapangans;

    public DaftarLapanganAdapter(Context context, ArrayList<Lapangan> lapangans) {
        this.context = context;
        this.lapangans = lapangans;
    }

    @NonNull
    @Override
    public DaftarLapanganAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lapangan, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarLapanganAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.tvNomorLapangan.setText(String.valueOf(lapangans.get(position).getNomorLapangan()));

        SessionManager sessionManager = new SessionManager(context);
        viewHolder.llLapangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.isLoggedin()) {
                    if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGGUNA)) {
                        Intent intent = new Intent(context, PemesananActivity.class);
                        intent.putExtra("id_gor", String.valueOf(lapangans.get(position).getIdGor()));
                        intent.putExtra("id_lapangan", String.valueOf(lapangans.get(position).getIdLapangan()));
                        context.startActivity(intent);
                    } else {
                        CheckLogin();
                    }
                } else {
                    CheckLogin();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return (lapangans != null) ? lapangans.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llLapangan;
        TextView tvNomorLapangan;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNomorLapangan = itemView.findViewById(R.id.tvNomorLapangan);
            llLapangan = itemView.findViewById(R.id.llLapangan);
        }
    }

    public void CheckLogin() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(context));
        alertDialogBuilder.setCancelable(true);

        LayoutInflater inflater = LayoutInflater.from(context);

        View dialogView = inflater.inflate(R.layout.dialog_login_dulu, null);

        alertDialogBuilder.setView(dialogView);

        Button btn_login = dialogView.findViewById(R.id.btn_login);
        TextView btn_nanti = dialogView.findViewById(R.id.btn_nanti);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        btn_login.setOnClickListener(view1 -> {
            alertDialog.dismiss();
//            Intent intent = new Intent(context, LoginPenggunaActivity.class);
//            context.startActivity(intent);
            AlertDialog.Builder alertDialogBuildes = new AlertDialog.Builder(Objects.requireNonNull(context));
            alertDialogBuildes.setCancelable(true);

            LayoutInflater inflaters = LayoutInflater.from(context);

            View dialogViews = inflaters.inflate(R.layout.dialog_login, null);

            alertDialogBuildes.setView(dialogViews);

            Button btn_pengguna = dialogViews.findViewById(R.id.btn_pengguna);
            TextView btn_pengelola = dialogViews.findViewById(R.id.btn_pengelola);

            final AlertDialog alertDialogs = alertDialogBuildes.create();
            alertDialogs.show();

            btn_pengguna.setOnClickListener(viewj -> {
                Intent intent = new Intent(context, LoginPenggunaActivity.class);
                context.startActivity(intent);
            });

            btn_pengelola.setOnClickListener(viewk -> {
                Intent intent = new Intent(context, LoginPengelolaActivity.class);
                context.startActivity(intent);
            });
        });

        btn_nanti.setOnClickListener(view12 -> {
            alertDialog.dismiss();
        });
    }
}