package com.mechanics.mechadmin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mechanics.mechadmin.models.Help_and_NotificationModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MechHelp extends Fragment {

    private ArrayList<Help_and_NotificationModel> arr;
    private ProgressBar progressBar;
    private HelpFragmentAdapter adapter;
    private ListView lv;
    private DatabaseReference databaseReference;
    private EditText text;
    private Date currentTime = Calendar.getInstance().getTime();
    private String now = DateFormat.getDateTimeInstance().format(currentTime);
    private int n = 10;
    private String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
    private StringBuilder sb = new StringBuilder(n);
    ArrayList<String> keyArray;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Help Collection").child("Mechanics");

        final View view = inflater.inflate(R.layout.content_help, container, false);
        lv = view.findViewById(R.id.help_listView);
        progressBar = view.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        TextView addText = view.findViewById(R.id.addText);
        addText.setText("Add Mechanic Help");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arr = new ArrayList<>();
                keyArray = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    String t1 = dataSnapshot1.child("help_message").getValue(String.class);
                    String t2 = dataSnapshot1.child("help_time").getValue(String.class);

                    arr.add(new Help_and_NotificationModel(t1, t2));
                    keyArray.add(dataSnapshot1.getKey());
                }
                progressBar.setVisibility(View.INVISIBLE);

                if (arr.size() > 0) {
                    adapter = new HelpFragmentAdapter(getContext(), arr,
                            R.drawable.ic_sync_black_24dp, databaseReference, keyArray);
                    lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    lv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        LinearLayout addHelp = view.findViewById(R.id.addHelpItem);
        addHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < n; i++) {
                    int index = (int) (AlphaNumericString.length() * Math.random());
                    sb.append(AlphaNumericString.charAt(index));
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("To Mechanic");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    View view1 = getLayoutInflater().inflate(R.layout.new_help, null);
                    text = view1.findViewById(R.id.ab_message);
                    builder.setView(view1);

                    builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Map<String, Object> carValues = new HashMap<>();
                            carValues.put("help_message", text.getText().toString());
                            carValues.put("help_time", now);
                            databaseReference.child(sb.toString()).setValue(carValues);
                            Toast.makeText(getContext(), "Help sent to all Mechanics", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            onResume();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });

        return view;
    }
}