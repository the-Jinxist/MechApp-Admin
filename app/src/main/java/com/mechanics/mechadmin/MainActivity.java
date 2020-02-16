package com.mechanics.mechadmin;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mechanics.mechadmin.models.LogsFragment;

public class MainActivity extends AppCompatActivity {

    Fragment f1 = new UsersContainer();
    Fragment f2 = new ProductsContainer();
    Fragment f3 = new NotifiContainer();
    Fragment f4 = new HelpContainer();
    Fragment f6 = new LogsFragment();
    Fragment active = f1;
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Users");
        getSupportActionBar().setElevation(0.0f);

        fragmentManager.beginTransaction().add(R.id.container, f1, "1").commit();
        fragmentManager.beginTransaction().add(R.id.container, f2, "1").hide(f2).commit();
        fragmentManager.beginTransaction().add(R.id.container, f3, "1").hide(f3).commit();
        fragmentManager.beginTransaction().add(R.id.container, f4, "1").hide(f4).commit();
        fragmentManager.beginTransaction().add(R.id.container, f6, "1").hide(f6).commit();

//        fragmentManager.beginTransaction().add(R.id.container, f1, "1").commit();
//        fragmentManager.beginTransaction().add(R.id.container, f2, "2").hide(f2).commit();
//        fragmentManager.beginTransaction().add(R.id.container, f3, "3").hide(f3).commit();
//        fragmentManager.beginTransaction().add(R.id.container, f4, "4").hide(f2).commit();
//        fragmentManager.beginTransaction().add(R.id.container, f5, "5").hide(f5).commit();

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_users:
                        fragmentManager.beginTransaction().hide(active).show(f1).commit();
                        active = f1;
                        getSupportActionBar().setTitle("Users");
                        return true;
                    case R.id.navigation_products:
                        fragmentManager.beginTransaction().hide(active).show(f2).commit();
                        active = f2;
                        getSupportActionBar().setTitle("Products");
                        return true;
                    case R.id.navigation_notifications:
                        fragmentManager.beginTransaction().hide(active).show(f3).commit();
                        active = f3;
                        getSupportActionBar().setTitle("Notification Section");
                        return true;
                    case R.id.navigation_help:
                        fragmentManager.beginTransaction().hide(active).show(f4).commit();
                        active = f4;
                        getSupportActionBar().setTitle("Help Section");
                        return true;

                    case R.id.navigation_logs:
                        fragmentManager.beginTransaction().hide(active).show(f6).commit();
                        active = f6;
                        getSupportActionBar().setTitle("Admin Logs");
                        return true;
                }
                return false;
            }
        });
    }
}
