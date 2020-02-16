package com.mechanics.mechadmin;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mechanics.mechadmin.models.LoginUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText adminLoginEdit;
    private CardView adminLoginButton;
    private ProgressBar adminLoginProgress;
    private TextView adminLoginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminLoginEdit = findViewById(R.id.admin_login_edit);
        adminLoginButton = findViewById(R.id.admin_login_button);
        adminLoginProgress = findViewById(R.id.admin_login_progress);
        adminLoginText = findViewById(R.id.admin_login_text);

        adminLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String id = adminLoginEdit.getText().toString().trim();
                if(isOnline()){
                    if (!TextUtils.isEmpty(id)){

                        adminLoginText.setVisibility(View.GONE);
                        adminLoginProgress.setVisibility(View.VISIBLE);

                        List<String> adminIDs = LoginUtils.getAdminIDs();
                        boolean equals = false;
                        for (String adminID : adminIDs){
                            if (id.equalsIgnoreCase(adminID)){
                                equals = true;
                            }
                        }

                        if (equals){
                            new LoginUtils().UpdateLogs(id, System.currentTimeMillis()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(AdminLoginActivity.this, MainActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    adminLoginProgress.setVisibility(View.GONE);
                                    adminLoginText.setVisibility(View.VISIBLE);

                                    Toast.makeText(AdminLoginActivity.this, "Check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else{
                            adminLoginProgress.setVisibility(View.GONE);
                            adminLoginText.setVisibility(View.VISIBLE);

                            Toast.makeText(AdminLoginActivity.this, "Sorry, your admin id doesn't match any of ours. Check with the management to resolve this", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(AdminLoginActivity.this, "Please input a valid id", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AdminLoginActivity.this, "Check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }
}
