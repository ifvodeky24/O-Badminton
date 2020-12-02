package com.hero.o_badminton.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.hero.o_badminton.R;
import com.hero.o_badminton.adapter.BookingPagerAdapter;
import com.smarteist.autoimageslider.SliderLayout;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    public BookingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_booking, container, false);

        Objects.requireNonNull(getActivity()).setTitle("Booking");

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

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
        return view;
    }

    private void setUpViewPager(ViewPager viewPager) {
        BookingPagerAdapter adapter = new BookingPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new DalamProsesFragment(), "Dalam Proses");
        adapter.addFragment(new RiwayatFragment(), "Riwayat");

        viewPager.setAdapter(adapter);
    }
}




