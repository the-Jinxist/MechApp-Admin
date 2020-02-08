package com.mechanics.mechadmin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class UsersContainer extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.main_help, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());

        pagerAdapter.addFrag(new Customers(), "Customer");
        pagerAdapter.addFrag(new Mechanics(), "Mechanic");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.parseColor("#B6B0B0"), Color.parseColor("#ffffff"));

        return view;
    }
}