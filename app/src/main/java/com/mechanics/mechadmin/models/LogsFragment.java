package com.mechanics.mechadmin.models;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mechanics.mechadmin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class LogsFragment extends Fragment {

    private RecyclerView logRecycler;
    private LinearLayout noInternetLayout;
    private DatabaseReference databaseReference;
    private ProgressBar logsProgress;

    private AdminLogsAdapter adminLogsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_logs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        logsProgress = view.findViewById(R.id.logs_progress);
        logRecycler = view.findViewById(R.id.logs_recyler);
        logRecycler.setHasFixedSize(true);
        noInternetLayout = view.findViewById(R.id.logs_no_connection_layout);

        if (isOnline()){

            final List<HashMap<String, Object>> logs = new ArrayList<>();

            databaseReference = FirebaseDatabase.getInstance().getReference().child("Admin-Logs");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        HashMap<String, Object> log = new HashMap<>();
                        String id = snapshot.child("adminId").getValue(String.class);
                        Long timeInMillis = snapshot.child("currentTimeInMillis").getValue(Long.class);

                        log.put("adminId", id);
                        log.put("currentTimeInMillis", timeInMillis);

                        logs.add(log);
                    }

                    adminLogsAdapter = new AdminLogsAdapter(logs);
                    
                    logsProgress.setVisibility(View.GONE);
                    logRecycler.setVisibility(View.VISIBLE);
                    noInternetLayout.setVisibility(View.GONE);

                    logRecycler.setAdapter(adminLogsAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else{
            logsProgress.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);
            logRecycler.setVisibility(View.GONE);
        }
    }

    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }
}
