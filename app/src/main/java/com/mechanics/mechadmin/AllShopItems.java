package com.mechanics.mechadmin;

import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mechanics.mechadmin.models.ShopItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllShopItems extends Fragment {

    private ArrayList<ShopItemModel> arr;
    private RecyclerView recyclerView;
    private ShopAdapter adapter;
    private ProgressBar progressBar;
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.allitem_products, container, false);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Shop Collection");
        progressBar = view.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        final LinearLayout noItem = view.findViewById(R.id.no_item_layout);

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<String> keyArr = new ArrayList<>();
                for (DataSnapshot dataSnapshot7 : dataSnapshot.getChildren()) {
                    keyArr.add(dataSnapshot7.getKey());
                }

                arr = new ArrayList<>();

                for (int i = 0; i < keyArr.size(); i++) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.child(keyArr.get(i)).getChildren()) {
                        String t1 = dataSnapshot1.child("shop_item_name").getValue(String.class);
                        String t2 = dataSnapshot1.child("shop_item_price").getValue(Long.class).toString();
                        String t3 = dataSnapshot1.child("shop_item_seller").getValue(String.class);
                        String t4 = dataSnapshot1.child("shop_item_email").getValue(String.class);
                        String t5 = dataSnapshot1.child("shop_item_phoneNo").getValue(String.class);
                        String t6 = dataSnapshot1.child("shop_item_descrpt").getValue(String.class);
                        String t7 = dataSnapshot1.child("shop_item_image").getValue(String.class);
                        String t8 = dataSnapshot1.child("shop_item_ID").getValue(String.class);
                        arr.add(new ShopItemModel(t1, t2, t3, t4, t5, t6, t7, t8));
                    }
                }
                progressBar.setVisibility(View.GONE);
                if (arr != null) {
                    adapter = new ShopAdapter(getContext(), arr);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    noItem.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        LinearLayout addProduct = view.findViewById(R.id.addProduct);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddProductActivity.class));
            }
        });
        return view;
    }

    public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> {

        private Context context;
        private final ArrayList<ShopItemModel> mData;

        ShopAdapter(Context context, ArrayList<ShopItemModel> mArrayL) {
            this.context = context;
            this.mData = mArrayL;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.shop_item_viewholder,
                    parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
            holder.shopItemName.setText(mData.get(position).getShop_item_name());
            holder.shopItemSeller.setText(mData.get(position).getShop_item_seller());
            holder.shopItemPrice.setText(String.format("₦%s", mData.get(position).getShop_item_price()));

            if (mData.get(position).getShop_item_image() == null) {
                holder.shopItemImage.setImageResource(R.drawable.placeholder);
            } else {
                Picasso.get().load(mData.get(position).getShop_item_image())
                        .placeholder(R.drawable.placeholder).into(holder.shopItemImage);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent in = new Intent(context, ShopItemActivity.class);
//
//                    String[] stockArr = {arr.get(position).getShop_item_name(),
//                            arr.get(position).getShop_item_price(),
//                            arr.get(position).getShop_item_seller(),
//                            arr.get(position).getShop_item_email(),
//                            arr.get(position).getShop_item_phoneNo(),
//                            arr.get(position).getShop_item_descrpt(),
//                            arr.get(position).getShop_item_image(),
//                            arr.get(position).getShop_item_ID()
//                    };
//
//                    in.putExtra("arr", stockArr);
//
//                    context.startActivity(in);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView shopItemName, shopItemSeller, shopItemPrice;

            ImageView shopItemImage;

            private MyViewHolder(View itemView) {

                super(itemView);
                shopItemName = itemView.findViewById(R.id.shop_item_name);
                shopItemPrice = itemView.findViewById(R.id.shop_item_price);
                shopItemSeller = itemView.findViewById(R.id.shop_item_seller);
                shopItemImage = itemView.findViewById(R.id.shop_item_image);
            }
        }
    }
}
