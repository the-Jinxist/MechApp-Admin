package com.mechanics.mechadmin;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProductItemActivity extends AppCompatActivity {

    String[] a, b, c, d;
    TextView name, total, subtotal, charge, location;
    Button email, call, performAction;
    Activity activity = ProductItemActivity.this;
    private DatabaseReference databaseReference, routeReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_item);

        ListView itemList = findViewById(R.id.itemList);
        name = findViewById(R.id.cusName);
        total = findViewById(R.id.total);
        subtotal = findViewById(R.id.subTotal);
        charge = findViewById(R.id.charge);
        location = findViewById(R.id.cusLocation);
        email = findViewById(R.id.email_cus);
        call = findViewById(R.id.call_cus);
        performAction = findViewById(R.id.performAction);

        Intent intent = getIntent();
        if (intent.hasExtra("itemDetails")) {
            a = intent.getStringArrayExtra("itemDetails");

            double sub22 = Double.parseDouble(a[0]);
            name.setText(a[2]);
            total.setText(String.format("₦%s", sub22));
            charge.setText(String.format("₦%s", Math.round(sub22 * 0.1)));
            subtotal.setText(String.format("₦%s", Math.round(sub22 * 0.9)));
            location.setText(a[1]);
            performAction.setText(a[6]);

            databaseReference = FirebaseDatabase.getInstance().getReference().child(a[8] + " Items");
            routeReference = FirebaseDatabase.getInstance().getReference().child(a[6] + " Items");

            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareCompat.IntentBuilder.from(activity).setType("message/rfc822")
                            .addEmailTo(a[5]).setSubject("Message from FABAT Admin").setText("We want to let you know that ...")
                            .setChooserTitle("Send Email").startChooser();
                }
            });

            call.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {

                    if (ActivityCompat.checkSelfPermission(ProductItemActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ProductItemActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 109);
                    } else {
                        try {
                            Toast.makeText(activity, "Please wait while we place your call..", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + a[4]));

                            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(ProductItemActivity.this, "Call permission is not enabled..", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            });

            performAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            final ProgressDialog progressDialog = new ProgressDialog(activity);
                            progressDialog.setMessage("Proceeding...");
                            progressDialog.setCanceledOnTouchOutside(false);

                            progressDialog.show();
                            Date currentTime = Calendar.getInstance().getTime();
                            String now = DateFormat.getDateTimeInstance().format(currentTime);

                            String made = "Confirmed! Products is now " + a[6] + ". Thanks for using FABAT";

                            final Map<String, String> sentMessage = new HashMap<>();
                            sentMessage.put("notification_message", made);
                            sentMessage.put("notification_time", now);

                            final Map<String, Object> updateStatus = new HashMap<>();
                            updateStatus.put("Trans Status", a[6]);

                            final Map<String, Object> valuesToCustomer = new HashMap<>();
                            valuesToCustomer.put("Customer Name", a[2]);
                            valuesToCustomer.put("Customer Number", a[4]);
                            valuesToCustomer.put("Customer Email", a[5]);
                            valuesToCustomer.put("Customer Uid", a[3]);
                            valuesToCustomer.put("Product List", c);
                            valuesToCustomer.put("Product Sellers", b);
                            valuesToCustomer.put("Product Numbers", d);
                            //valuesToCustomer.put("Total Amount Paid", "" + sub22 * 1.1);
                            valuesToCustomer.put("Street Address", a[2]);
                            // valuesToCustomer.put("City", t3.getText().toString());
                            valuesToCustomer.put("Trans Time", now);
                            //valuesToCustomer.put("Server Confirmation", serverData);
                            //valuesToCustomer.put("Trans Description", "Payment for Items");
                            valuesToCustomer.put("Trans ID", a[7]);
                            valuesToCustomer.put("Trans Status", a[6]);

                            final String theUID = a[3];

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
                                                                            .child(a[7]).updateChildren(updateStatus);
                                                                    Toast.makeText(activity, "SUCCESSFUL", Toast.LENGTH_SHORT).show();
                                                                    progressDialog.dismiss();
//                                                            adapter.notifyDataSetChanged();
                                                                }
                                                            });
                                                        }
                                                    });
                                        }
                                    });
//                    adapter.notifyDataSetChanged();

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

        }
        if (intent.hasExtra("itemSellers") && intent.hasExtra("items") && intent.hasExtra("itemsNum")) {
            b = intent.getStringArrayExtra("itemSellers");
            c = intent.getStringArrayExtra("items");
            d = intent.getStringArrayExtra("itemsNum");

            ItemAdapter adapter = new ItemAdapter(ProductItemActivity.this, c, b, d);
            itemList.setAdapter(adapter);
        }
    }

    public class ItemAdapter extends BaseAdapter {
        private final Context context;
        private final String[] items;
        private final String[] sellers;
        private final String[] numberOf;

        ItemAdapter(Context context, String[] items, String[] sellers, String[] numberOf) {
            this.context = context;
            this.items = items;
            this.numberOf = numberOf;
            this.sellers = sellers;
        }

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            View rowView = inflater.inflate(R.layout.product_item_vh, parent, false);

            TextView item = rowView.findViewById(R.id.mm_item);
            TextView seller = rowView.findViewById(R.id.mm_seller);
            TextView num = rowView.findViewById(R.id.mm_num);

            item.setText(String.format("Item: %s", items[position]));
            seller.setText(String.format("Seller: %s", sellers[position]));
            num.setText(String.format("No: %s", numberOf[position]));

            return rowView;
        }
    }
}