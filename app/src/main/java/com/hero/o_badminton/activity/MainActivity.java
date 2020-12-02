package com.hero.o_badminton.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hero.o_badminton.R;
import com.hero.o_badminton.fragment.AkunFragment;
import com.hero.o_badminton.fragment.BerandaFragment;
import com.hero.o_badminton.fragment.BookingFragment;
import com.hero.o_badminton.fragment.RiwayatTopupFragment;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.SessionManager;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    MenuItem navigation_beranda, navigation_booking, navigation_akun, navigation_topup;
    Menu menu_bottom;
    BottomNavigationView navigation;
    public SessionManager sessionManager;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //fungsi bottom navigasi
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        menu_bottom = navigation.getMenu();

        navigation_beranda = menu_bottom.findItem(R.id.navigation_beranda);
        navigation_booking = menu_bottom.findItem(R.id.navigation_booking);
        navigation_akun = menu_bottom.findItem(R.id.navigation_akun);
        navigation_topup = menu_bottom.findItem(R.id.navigation_topup);

        sessionManager = new SessionManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if (savedInstanceState == null) {
            fragment = new BerandaFragment();
            loadFragment(fragment);

        } else {
            Log.d(String.valueOf(getApplicationContext()), "cek");
        }

        if (sessionManager.isLoggedin()) {
            String id = sessionManager.getLoginDetail().get(SessionManager.ID);

            System.out.println("cek id" + id);

//            initFreshChat();

            if (sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN).equalsIgnoreCase(SessionManager.LEVEL_PENGGUNA)){
                navigation_topup.setVisible(true);
            }else {
                navigation_topup.setVisible(false);
            }

        } else {
            displayMenuBelumLogin();
            navigation_topup.setVisible(false);
        }
    }

    private void displayMenuBelumLogin() {
        navigation_booking.setOnMenuItemClickListener(menuItem -> {
            harus_login();
            return true;
        });

        navigation_akun.setOnMenuItemClickListener(menuItem -> {
            harus_login();
            return true;
        });

    }

    private void harus_login() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(true);

        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_login, null);

        alertDialogBuilder.setView(dialogView);

        Button btn_pengguna = dialogView.findViewById(R.id.btn_pengguna);
        TextView btn_pengelola = dialogView.findViewById(R.id.btn_pengelola);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        btn_pengguna.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginPenggunaActivity.class);
            finish();
            startActivity(intent);
        });

        btn_pengelola.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginPengelolaActivity.class);
            finish();
            startActivity(intent);
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                Fragment fragment;

                switch (item.getItemId()) {
                    case R.id.navigation_beranda:
                        fragment = new BerandaFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.navigation_booking:
                        fragment = new BookingFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.navigation_akun:
                        fragment = new AkunFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.navigation_topup:
                        fragment = new RiwayatTopupFragment();
                        loadFragment(fragment);
                        return true;

                }
                return false;
            };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onPostResume() { super.onPostResume(); }
}
