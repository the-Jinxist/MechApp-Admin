package com.mechanics.mechadmin;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mechanics.mechadmin.models.MechModel;

import java.util.ArrayList;

public class Customers extends Fragment {

    private ArrayList<MechModel> arr;
    static ArrayList<String> cusUIDArray;

    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_page, container, false);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer Collection");
        final ProgressBar progressBar = view.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        final LinearLayout noConnect = view.findViewById(R.id.no_connection_layout);
        TextView textView = view.findViewById(R.id.errMessage);
        textView.setText("No Internet Connection");

        if(!isOnline()){
            progressBar.setVisibility(View.INVISIBLE);
            noConnect.setVisibility(View.VISIBLE);
        }else {
            noConnect.setVisibility(View.GONE);
        }

        final RecyclerView mechsCategoryRecycler = view.findViewById(R.id.recycler);

        mechsCategoryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arr = new ArrayList<>();
                cusUIDArray = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    String t1 = dataSnapshot1.child("Company Name").getValue(String.class);
                    String t2 = dataSnapshot1.child("Email").getValue(String.class);
                    String t3 = dataSnapshot1.child("Phone Number").getValue(String.class);
                    String t4 = dataSnapshot1.child("Uid").getValue(String.class);

                    cusUIDArray.add(t4);
                    arr.add(new MechModel(t1, t3, t2, t4));
                }
                progressBar.setVisibility(View.GONE);

                if (arr != null) {
                    MechsCatAdapter adapter = new MechsCatAdapter(getContext(), arr);
                    mechsCategoryRecycler.setAdapter(adapter);
                    noConnect.setVisibility(View.INVISIBLE);
                    adapter.notifyDataSetChanged();
                }else{
                    noConnect.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return view;
    }

    private class MechsCatAdapter extends RecyclerView.Adapter<MechsCatAdapter.MyViewHolder> {

        private Context context;
        private final ArrayList<MechModel> mData;

        MechsCatAdapter(Context context, ArrayList<MechModel> mArrayL) {
            this.context = context;
            this.mData = mArrayL;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.eachmechanic_viewholder,
                    parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
            holder.name.setText(mData.get(position).getName());
            holder.phoneNumber.setText(mData.get(position).getNumber());
            holder.email.setText(mData.get(position).getEmail());
            holder.image.setImageResource(R.drawable.boss);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(context, EachUser.class);

                    String[] a = {mData.get(position).getName(),mData.get(position).getNumber(),
                            mData.get(position).getEmail(),mData.get(position).getUid()};

                    in.putExtra("map", a);

                    context.startActivity(in);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name, phoneNumber, email;
            ImageView image;

            private MyViewHolder(View itemView) {

                super(itemView);
                name = itemView.findViewById(R.id.mechanic_name);
                phoneNumber = itemView.findViewById(R.id.mechanic_number);
                email = itemView.findViewById(R.id.mech_address);
                image = itemView.findViewById(R.id.mechanic_image);
            }
        }
    }
}