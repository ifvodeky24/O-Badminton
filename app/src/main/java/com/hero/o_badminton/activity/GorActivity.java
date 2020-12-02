package com.hero.o_badminton.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.hero.o_badminton.R;
import com.hero.o_badminton.adapter.GorPagerAdapter;
import com.hero.o_badminton.fragment.DaftarGorFragment;
import com.hero.o_badminton.fragment.MapGorFragment;

public class GorActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gor);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    private void setUpViewPager(ViewPager viewPager) {
        GorPagerAdapter adapter = new GorPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new MapGorFragment(), "Map");
        adapter.addFragment(new DaftarGorFragment(), "Daftar");

        viewPager.setAdapter(adapter);
    }
}
