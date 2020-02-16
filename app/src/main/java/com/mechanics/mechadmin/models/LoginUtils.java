package com.mechanics.mechadmin.models;

import android.text.format.DateFormat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginUtils {

    private DatabaseReference databaseReference;

    public LoginUtils(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Admin-Logs");
    }

    public static List<String> getAdminIDs(){
        List<String> adminIDs = new ArrayList<>();
        adminIDs.add("Fab1001");
        adminIDs.add("Fab1002");
        adminIDs.add("Fab1003");
        adminIDs.add("Fab1004");
        adminIDs.add("Fab1005");
        adminIDs.add("Fab1006");
        adminIDs.add("Fab1007");
        adminIDs.add("Fab1008");
        adminIDs.add("Fab1009");
        adminIDs.add("Fab1010");

        return adminIDs;
    }

    public Task<Void> UpdateLogs(String id, Long currentTimeInMillis){
        HashMap<String, Object> adminModel = new HashMap<>();
        adminModel.put("adminId", id);
        adminModel.put("currentTimeInMillis", currentTimeInMillis);

        return databaseReference.push().setValue(adminModel);
    }

    public static String convertDate(Long timeInMillis){
        return DateFormat.format("dd/MM/yyyy hh:mm:ss", timeInMillis).toString();
    }

}
