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

public class ProductsContainer extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_help22, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());

        pagerAdapter.addFrag(new AllShopItems(), "All Items");
        pagerAdapter.addFrag(new OrderedShopItems(), "Ordered");
        pagerAdapter.addFrag(new EnrouteShopItems(), "En Route");
        pagerAdapter.addFrag(new DeliveredShopItems(), "Delivered");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.parseColor("#B6B0B0"), Color.parseColor("#ffffff"));
        return view;
    }
}