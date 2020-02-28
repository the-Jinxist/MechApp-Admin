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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

public class EachMechanic extends AppCompatActivity {

    String title = "Mechanic";
    final String specifationText = "";
    String[] k_val;
    String[] specifications;
    static String mechUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.each_mechanic);

        Intent intent = getIntent();
        if (intent.hasExtra("mechMap")) {
            k_val = intent.getStringArrayExtra("mechMap");
            specifications = intent.getStringArrayExtra("mechSpecs");
        }

//        {mData.get(position).getBankAccountName(),mData.get(position).getBankAccountNumber(),
//                mData.get(position).getBankName(),mData.get(position).getCacImageUrl(),
//                mData.get(position).getCity(),mData.get(position).getCusName(),
//                mData.get(position).getDescript(),mData.get(position).getCusEmail(),
//                mData.get(position).getImage(),mData.get(position).getWorkDone(),
//                mData.get(position).getLocality(),mData.get(position).getUid(),
//                mData.get(position).getCusNumber(),mData.get(position).getStreet_name(),
//                mData.get(position).getRating(),mData.get(position).getWebUrl()};

        title = k_val[5];
        mechUID = k_val[11];

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setElevation(0.0f);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFrag(new MechJobFrag(), "Jobs");
        pagerAdapter.addFrag(new MechShop(), "Shop");
        pagerAdapter.addFrag(new MechShop(), "History");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.parseColor("#B6B0B0"), Color.parseColor("#ffffff"));

        TextView email = findViewById(R.id.ar_mech_description);
        email.setText(k_val[6]);
        TextView desc = findViewById(R.id.ar_mech_location);
        desc.setText(k_val[13]);
        TextView jobsDone = findViewById(R.id.ar_mech_jobs_done);
        jobsDone.setText(k_val[9]);
        TextView ratings = findViewById(R.id.ar_mech_rating);
        ratings.setText(k_val[14]);

        TextView mechSpecsText= findViewById(R.id.ar_mech_specifications);
        for (String spec : specifications){
            specifationText.concat(spec + ". ");
        }
        mechSpecsText.setText(specifationText);

//        TextView email = findViewById(R.id.userEmail);
//        email.setText(k_val[2]);
        ImageView image = findViewById(R.id.ar_mech_image);
        if (k_val[8] == null) {
            image.setImageResource(R.drawable.engineer);
        } else {
            Picasso.get().load(k_val[8]).placeholder(R.drawable.engineer).into(image);
        }

        // To call Customer
        Button btnCallNow = findViewById(R.id.ar_mech_callnow);
        Button btnPayMech = findViewById(R.id.ar_payMech);
        btnCallNow.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(EachMechanic.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EachMechanic.this, new String[]{Manifest.permission.CALL_PHONE}, 109);
                } else {
                    try {
                        Toast.makeText(EachMechanic.this, "Please wait while we place your call..", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + k_val[12]));

                        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(EachMechanic.this, "Call permission is not enabled..", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        btnPayMech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EachMechanic.this, "To Pay " + title, Toast.LENGTH_SHORT).show();
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