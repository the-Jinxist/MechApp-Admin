package com.mechanics.mechadmin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.mechanics.mechadmin.Customers.cusUIDArray;
import static com.mechanics.mechadmin.Mechanics.mechUIDArray;

public class NotifiContainer extends Fragment {

    private TextView text1, text2;
    private DatabaseReference databaseReference;
    private Date currentTime = Calendar.getInstance().getTime();
    private String now = DateFormat.getDateTimeInstance().format(currentTime);
    private int n = 10;
    private String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
    StringBuilder sb;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Notification Collection");

        LinearLayout toCus = view.findViewById(R.id.addItem1);
        toCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb = new StringBuilder(n);

                for (int i = 0; i < n; i++) {
                    int index = (int) (AlphaNumericString.length() * Math.random());
                    sb.append(AlphaNumericString.charAt(index));
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("To Customer");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    View view1 = getLayoutInflater().inflate(R.layout.new_help, null);
                    text1 = view1.findViewById(R.id.ab_message);
                    text1.setHint("Create Notification...");
                    builder.setView(view1);
                }
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        assert  cusUIDArray != null;
                        for (String uid: cusUIDArray) {
                            Map<String, Object> carValues = new HashMap<>();
                            carValues.put("notification_message", text1.getText().toString());
                            carValues.put("notification_time", now);
                            databaseReference.child("Customer").child(uid).child(sb.toString()).setValue(carValues);
                        }
                        Toast.makeText(getContext(), "Notification sent to all Mechanics", Toast.LENGTH_SHORT).show();
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
        });

        LinearLayout toMech = view.findViewById(R.id.addItem2);
        toMech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb = new StringBuilder(n);

                for (int i = 0; i < n; i++) {
                    int index = (int) (AlphaNumericString.length() * Math.random());
                    sb.append(AlphaNumericString.charAt(index));
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("To Mechanic");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    View view1 = getLayoutInflater().inflate(R.layout.new_help, null);
                    text2 = view1.findViewById(R.id.ab_message);
                    text2.setHint("Create Notification...");
                    builder.setView(view1);
                }
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        assert  mechUIDArray != null;
                        for (String uid: mechUIDArray) {
                            Map<String, Object> carValues = new HashMap<>();
                            carValues.put("notification_message", text2.getText().toString());
                            carValues.put("notification_time", now);
                            databaseReference.child("Mechanic").child(uid).child(sb.toString()).setValue(carValues);
                        }
                        Toast.makeText(getContext(), "Notification sent to all Mechanics", Toast.LENGTH_SHORT).show();
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
        });
        return view;
    }

    class SendNoti extends AsyncTask<ArrayList<String>, Void, String>{

        @Override
        protected String doInBackground(ArrayList<String>... arrayLists) {
            ArrayList<String> arrayList = arrayLists[0];


            return null;
        }
    }
}