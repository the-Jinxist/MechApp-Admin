package com.mechanics.mechadmin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.mechanics.mechadmin.models.CartHistoryModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderedShopItems extends Fragment {

    private CartHistoryAdapter adapter;
    private DatabaseReference databaseReference, routeReference;
    //    private ArrayList<String> keyArray;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.cart_fragment_history_page, container, false);

        final RecyclerView recyclerView = view.findViewById(R.id.cartHistoryRecycler);
        final ProgressBar progressBar = view.findViewById(R.id.progress44);
        progressBar.setVisibility(View.VISIBLE);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Proceeding...");
        progressDialog.setCanceledOnTouchOutside(false);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Ordered Items");
        routeReference = FirebaseDatabase.getInstance().getReference().child("En Route Items");

        final LinearLayout empty_list = view.findViewById(R.id.no_cart_layout);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<CartHistoryModel> list = new ArrayList<>();
//                keyArray = new ArrayList<>();

                GenericTypeIndicator<ArrayList<String>> g = new GenericTypeIndicator<ArrayList<String>>() {
                };
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ArrayList<String> t1 = dataSnapshot1.child("Product List").getValue(g);
                    ArrayList<String> t2 = dataSnapshot1.child("Product Sellers").getValue(g);
                    ArrayList<String> t10 = dataSnapshot1.child("Product Numbers").getValue(g);
                    String t3 = dataSnapshot1.child("Total Amount Paid").getValue(String.class);
                    // String t4 = dataSnapshot1.child("Product Image").getValue(String.class);
                    String t5 = dataSnapshot1.child("Customer Uid").getValue(String.class);
                    String t6 = dataSnapshot1.child("Customer Name").getValue(String.class);
                    String t7 = dataSnapshot1.child("Customer Number").getValue(String.class);
                    String t8 = dataSnapshot1.child("Street Address").getValue(String.class);
                    String t9 = dataSnapshot1.child("Customer Email").getValue(String.class);
                    String t11 = dataSnapshot1.child("Trans ID").getValue(String.class);

                    list.add(new CartHistoryModel(t6, t3, t2, "jj", t5, t9, t7, t8, t1, t10, t11));
//                    keyArray.add(dataSnapshot1.getKey());
                }

                if (list.size() > 0) {
                    adapter = new CartHistoryAdapter(getContext(), list);
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    empty_list.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    public class CartHistoryAdapter extends RecyclerView.Adapter<CartHistoryAdapter.MyViewHolder> {

        private Context context;
        private final ArrayList<CartHistoryModel> mData;

        CartHistoryAdapter(Context context, ArrayList<CartHistoryModel> mArrayL) {
            this.context = context;
            this.mData = mArrayL;
        }

        @NonNull
        @Override
        public CartHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.ordereditem_vh, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
            String s = mData.get(position).getCusName();
            String s1 = mData.get(position).getSellers().toString();
            holder.itemName.setText(s.substring(1, s.length() - 1));
            holder.thePrice.setText(String.format("₦%s.", Math.round(Double.parseDouble(mData.get(position).getPrice()))));
            holder.itemSeller.setText(s1.substring(1, s1.length() - 1));

            //Set button text
            holder.proceedItem.setText("En Route");
            holder.proceedItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to take the products En Route?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            // To get the current time
                            progressDialog.show();
                            Date currentTime = Calendar.getInstance().getTime();
                            String now = DateFormat.getDateTimeInstance().format(currentTime);

                            String made = "Confirmed! Products is now En Route. Thanks for using FABAT";

                            final Map<String, String> sentMessage = new HashMap<>();
                            sentMessage.put("notification_message", made);
                            sentMessage.put("notification_time", now);

                            final Map<String, Object> updateStatus = new HashMap<>();
                            updateStatus.put("Trans Status", "En Route");

                            final Map<String, Object> valuesToCustomer = new HashMap<>();
                            valuesToCustomer.put("Customer Name", mData.get(position).getCusName());
                            valuesToCustomer.put("Customer Number", mData.get(position).getCusNumber());
                            valuesToCustomer.put("Customer Email", mData.get(position).getCusEmail());
                            valuesToCustomer.put("Customer Uid", mData.get(position).getCusUid());
                            valuesToCustomer.put("Product List", mData.get(position).getItems());
                            valuesToCustomer.put("Product Sellers", mData.get(position).getSellers());
                            valuesToCustomer.put("Product Numbers", mData.get(position).getSellers());
                            //valuesToCustomer.put("Total Amount Paid", "" + sub22 * 1.1);
                            valuesToCustomer.put("Street Address", mData.get(position).getStreetname());
                            // valuesToCustomer.put("City", t3.getText().toString());
                            valuesToCustomer.put("Trans Time", now);
                            //valuesToCustomer.put("Server Confirmation", serverData);
                            //valuesToCustomer.put("Trans Description", "Payment for Items");
                            valuesToCustomer.put("Trans ID", mData.get(position).getTransId());
                            valuesToCustomer.put("Trans Status", "En Route");

                            final String theUID = mData.get(position).getCusUid();

                            databaseReference.child(theUID).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.setMessage("... almost done...");
                                            routeReference.child(theUID).setValue(valuesToCustomer)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            progressDialog.setMessage("...clarifying things...");
                                                            databaseReference.child("Notification Collection").child("Customer")
                                                                    .child(theUID).push()
                                                                    .setValue(sentMessage).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    databaseReference.child("Cart Collection").child(theUID)
                                                                            .child(mData.get(position).getTransId()).updateChildren(updateStatus);
                                                                    Toast.makeText(getContext(), "SUCCESSFUL, Item is En Route...", Toast.LENGTH_SHORT).show();
                                                                    progressDialog.dismiss();
                                                                    adapter.notifyDataSetChanged();
                                                                }
                                                            });
                                                        }
                                                    });
                                        }
                                    });
                            adapter.notifyDataSetChanged();

                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });


            //Set Item onClick
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(context, ProductItemActivity.class);

                    String[] a = {mData.get(position).getPrice(), mData.get(position).getStreetname(),
                            mData.get(position).getCusName(), mData.get(position).getCusUid(),
                            mData.get(position).getCusNumber(), mData.get(position).getCusEmail(),
                            "En Route", mData.get(position).getTransId(), "Ordered"};

                    Object[] b = mData.get(position).getItems().toArray();
                    Object[] c = mData.get(position).getSellers().toArray();
                    Object[] d = mData.get(position).getItemNumbers().toArray();
                    in.putExtra("itemDetails", a);
                    in.putExtra("itemSellers", b);
                    in.putExtra("items", c);
                    in.putExtra("itemsNum", d);

                    context.startActivity(in);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView itemName, thePrice, itemSeller;
            ImageView itemImage;
            Button proceedItem;

            private MyViewHolder(View itemView) {

                super(itemView);
                itemName = itemView.findViewById(R.id.itemNameB);
                thePrice = itemView.findViewById(R.id.itemPriceB);
                itemSeller = itemView.findViewById(R.id.itemSellerB);

                proceedItem = itemView.findViewById(R.id.proceedItem);
                itemImage = itemView.findViewById(R.id.itemImageB);
            }
        }
    }
}