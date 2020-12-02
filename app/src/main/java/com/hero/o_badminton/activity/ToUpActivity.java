package com.hero.o_badminton.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.hero.o_badminton.R;

import java.text.DecimalFormat;

public class ToUpActivity extends AppCompatActivity {
    TextView tvIdTopup, tvIdPengguna, tvJumlah, tvTanggal, tvStatus;

    String id_topup, id_pengguna, jumlah, tanggal, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_up);
        setTitle("Riwayat Top Up");

        tvIdTopup = findViewById(R.id.tvIdTopup);
        tvIdPengguna = findViewById(R.id.tvIdPengguna);
        tvJumlah = findViewById(R.id.tvJumlah);
        tvTanggal = findViewById(R.id.tvTanggal);
        tvStatus = findViewById(R.id.tvStatus);

        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        DecimalFormat df = new DecimalFormat("#,###");

        if (extras != null) {
            id_topup = extras.getString("id_topup");
            id_pengguna = extras.getString("id_pengguna");
            jumlah = extras.getString("jumlah");
            tanggal = extras.getString("tanggal");
            status = extras.getString("status");
            System.out.println("garong" + id_topup + " " + id_pengguna);
        }

        tvIdTopup.setText("id Top Up: "+id_topup);
        tvIdPengguna.setText("id Pengguna: "+id_pengguna);
        tvJumlah.setText("Jumlah: "+"Rp. " + df.format(Double.valueOf(jumlah)));
        tvTanggal.setText("Tanggal: "+tanggal);
        tvStatus.setText(status);
    }
}
