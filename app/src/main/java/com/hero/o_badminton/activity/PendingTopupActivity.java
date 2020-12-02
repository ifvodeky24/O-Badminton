package com.hero.o_badminton.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hero.o_badminton.R;
import com.hero.o_badminton.response.TopupResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingTopupActivity extends AppCompatActivity {
    TextView tv_total_tagihan;
    Button btn_batal, btn_upload_bukti;

    private SessionManager sessionManager;
    private ApiInterface apiInterface;

    String id_topup, jumlah;

    private DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_topup);
        setTitle("Data Top Up");

        df = new DecimalFormat("#,###");

        sessionManager = new SessionManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        tv_total_tagihan = findViewById(R.id.tv_total_tagihan);
        btn_batal = findViewById(R.id.btn_batal);
        btn_upload_bukti = findViewById(R.id.btn_upload_bukti);

        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            id_topup = extras.getString("id_topup");
            jumlah = extras.getString("jumlah");
            System.out.println("garong" + id_topup + " " + jumlah);
        }

        tv_total_tagihan.setText("Rp. " + df.format(Double.valueOf(jumlah)));

        btn_batal.setOnClickListener(view -> apiInterface.batalTopup(id_topup).enqueue(new Callback<TopupResponse>() {
            @Override
            public void onResponse(@NonNull Call<TopupResponse> call, @NonNull Response<TopupResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Top Up Berhasil Dibatalkan", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(PendingTopupActivity.this, MainActivity.class);
                    startActivity(intent1);
                }else {
                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopupResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        }));

        btn_upload_bukti.setOnClickListener(view -> {

        });
    }
}
