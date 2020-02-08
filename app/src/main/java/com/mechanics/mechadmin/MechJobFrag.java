package com.mechanics.mechadmin;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mechanics.mechadmin.models.JobsRecyclerModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.mechanics.mechadmin.EachMechanic.mechUID;

public class MechJobFrag extends Fragment {

    private ArrayList<JobsRecyclerModel> arr;
    private RecyclerView recyclerView;
    private JobAdapter adapter;
    private ProgressBar progressBar;
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_shop, container, false);
        final LinearLayout empty_job = view.findViewById(R.id.jobs_frag_no_jobs_layout);
        progressBar = view.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = view.findViewById(R.id.grid_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        // String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        assert mechUID != null;
        databaseReference.child("Jobs Collection").child("Mechanic").child(mechUID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        arr = new ArrayList<>();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            String t1 = dataSnapshot1.child("Mech Image").getValue(String.class);
                            String t2 = dataSnapshot1.child("Trans Amount").getValue(String.class);
                            String t3 = dataSnapshot1.child("Trans Time").getValue(String.class);
                            String t4 = dataSnapshot1.child("Car Type").getValue(String.class);
                            String t6 = dataSnapshot1.child("Trans Description").getValue(String.class);
                            String t7 = dataSnapshot1.child("Mech Name").getValue(String.class);
                            String t8 = dataSnapshot1.child("Mech Number").getValue(String.class);
                            String t9 = dataSnapshot1.child("Customer Name").getValue(String.class);
                            String t10 = dataSnapshot1.child("Trans ID").getValue(String.class);
                            String t11 = dataSnapshot1.child("Trans Confirmation").getValue(String.class);
                            String t12 = dataSnapshot1.child("Mech UID").getValue(String.class);

                            arr.add(new JobsRecyclerModel(t2, t4, t3, t6, t1, t7, t8, t9, t10, t11, t12));
                        }
                        progressBar.setVisibility(View.GONE);

                        if (!arr.isEmpty()) {
                            adapter = new JobAdapter(getContext(), arr);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            recyclerView.setVisibility(View.INVISIBLE);
                            empty_job.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

        return view;
    }

    public class JobAdapter extends RecyclerView.Adapter<JobAdapter.MyViewHolder> {

        private Context context;
        private final ArrayList<JobsRecyclerModel> mData;

        JobAdapter(Context context, ArrayList<JobsRecyclerModel> mArrayL) {
            this.context = context;
            this.mData = mArrayL;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.job_item_viewholder, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
            holder.oppName.setText(mData.get(position).getName());
            holder.thePrice.setText(String.format("₦%s", mData.get(position).getAmount_paid()));
            holder.oppNumber.setText(mData.get(position).getNumber());
            holder.tranxDate.setText(mData.get(position).getTransact_time());

            if (mData.get(position).getImage() == null) {
                holder.oppImage.setImageResource(R.drawable.engineer);
            } else {
                Picasso.get().load(mData.get(position).getImage()).placeholder(R.drawable.engineer).into(holder.oppImage);
            }

            if (mData.get(position).getTransConfirm().equals("Confirmed")) {
                holder.confirm.setText("COMPLETED!");
                holder.confirm.setBackgroundColor(Color.parseColor("#00baff"));
                holder.confirm.setEnabled(false);
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView oppName, tranxDate, thePrice, oppNumber;
            ImageView oppImage;
            Button confirm;

            private MyViewHolder(View itemView) {

                super(itemView);
                oppName = itemView.findViewById(R.id.opposite_name);
                thePrice = itemView.findViewById(R.id.job_price);
                tranxDate = itemView.findViewById(R.id.tranx_dateTime);
                oppNumber = itemView.findViewById(R.id.opposite_phone_number);
                oppImage = itemView.findViewById(R.id.oppositePerson_image);
                confirm = itemView.findViewById(R.id.confirm_the_transaction);
            }
        }
    }
}