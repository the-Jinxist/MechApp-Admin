package com.mechanics.mechadmin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class EachUser extends AppCompatActivity {

    String title = "User";
    String[] k_val;
    static String userUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_each_user);

        Intent intent = getIntent();
        if (intent.hasExtra("map")) {
            k_val = intent.getStringArrayExtra("map");
        }
        title = k_val[0];
        userUID = k_val[3];

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setElevation(0.0f);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFrag(new UserJobFrag(), "Jobs");
        pagerAdapter.addFrag(new UserCartHistory(), "Shop");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.parseColor("#B6B0B0"), Color.parseColor("#ffffff"));

        TextView email = findViewById(R.id.userEmail);
        email.setText(k_val[2]);

        // To call Customer
        Button btnCallNow = findViewById(R.id.callnow);
        btnCallNow.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(EachUser.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EachUser.this, new String[]{Manifest.permission.CALL_PHONE}, 109);
                } else {
                    try {
                        Toast.makeText(EachUser.this, "Please wait while we place your call..", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + k_val[1]));

                        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(EachUser.this, "Call permission is not enabled..", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}