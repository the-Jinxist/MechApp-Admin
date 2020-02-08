package com.mechanics.mechadmin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {
    ImageView proImage;
    CardView cardView;
    String proName, proPrice, proDesc, proSeller, proSellerNum, proSellerEmail;
    TextInputEditText ProName, ProPrice, ProDesc, ProSellerEmail, ProSeller, ProSellerNum;
    Uri imageUri;
    String pro_image_url;

    ProgressDialog progressDialog;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Shop Collection");
        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        setContentView(R.layout.activity_add_product);

        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (proPrice.isEmpty() &&
                        proName.isEmpty() &&
                        proDesc.isEmpty() &&
                        proSeller.isEmpty() &&
                        proSellerEmail.isEmpty() &&
                        proSellerNum.isEmpty()) {
                    finish();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you want to discard the fields? ");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Product");
        getSupportActionBar().setElevation(0.0f);

        cardView = findViewById(R.id.add_pro_image_container);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIn = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIn.setType("image/*");
                startActivityForResult(galleryIn, 10);
            }
        });

        proImage = findViewById(R.id.add_pro_image);

        ProName = findViewById(R.id.add_pro_name);
        ProPrice = findViewById(R.id.add_pro_price);
        ProDesc = findViewById(R.id.add_pro_description);
        ProSellerEmail = findViewById(R.id.add_pro_seller_email);
        ProSeller = findViewById(R.id.add_pro_seller_name);
        ProSellerNum = findViewById(R.id.add_pro_seller_number);

        Button btnAdd = findViewById(R.id.add_pro_button);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proPrice = ProPrice.getText().toString().trim();
                proName = ProName.getText().toString().trim();
                proDesc = ProDesc.getText().toString().trim();
                proSeller = ProSeller.getText().toString().trim();
                proSellerNum = ProSellerNum.getText().toString().trim();
                proSellerEmail = ProSellerEmail.getText().toString().trim();


                if (TextUtils.isEmpty(proPrice)) {
                    Toast.makeText(AddProductActivity.this, "Product Price is empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(proName)) {
                    Toast.makeText(AddProductActivity.this, "Product Name is empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(proDesc)) {
                    Toast.makeText(AddProductActivity.this, "Product Description is empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(proSeller)) {
                    Toast.makeText(AddProductActivity.this, "Product Seller is empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(proSellerEmail)) {
                    Toast.makeText(AddProductActivity.this, "Phone Number is empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(proSellerNum)) {
                    Toast.makeText(AddProductActivity.this, "Email is empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (imageUri == null) {
                    Toast.makeText(AddProductActivity.this, "Add an Image", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog.setMessage("Uploading Product...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                if (imageUri != null) {
                    int n = 10;
                    String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

                    final StringBuilder sb = new StringBuilder(n);

                    for (int i = 0; i < n; i++) {
                        int index = (int) (AlphaNumericString.length() * Math.random());
                        sb.append(AlphaNumericString.charAt(index));
                    }

                    final StorageReference s = storageReference.child("Product Images/").child(sb.toString());
                    UploadTask uploadTask = s.putFile(imageUri);

                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return s.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                pro_image_url = task.getResult().toString();

                                Map<String, Object> proValues = new HashMap<>();
                                proValues.put("shop_item_price", Long.parseLong(proPrice));
                                proValues.put("shop_item_name", proName);
                                proValues.put("shop_item_seller", proSeller);
                                proValues.put("shop_item_email", proSellerEmail);
                                proValues.put("shop_item_phoneNo", proSellerNum);
                                proValues.put("shop_item_descrpt", proDesc);
                                proValues.put("shop_item_image", pro_image_url);
                                proValues.put("shop_item_ID", "1");

                                databaseReference.child("Uploaded").child(sb.toString()).setValue(proValues).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(AddProductActivity.this, "Product Uploaded", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                        finish();

                                    }
                                });
                            } else {
                                Toast.makeText(AddProductActivity.this, "A problem occured!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 10 && resultCode == RESULT_OK) {
            imageUri = data.getData();
            Bitmap bitmap1 = null;
            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            proImage.setImageBitmap(bitmap1);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            proPrice = ProPrice.getText().toString().trim();
            proName = ProName.getText().toString().trim();
            proDesc = ProDesc.getText().toString().trim();
            proSeller = ProSeller.getText().toString().trim();
            proSellerNum = ProSellerNum.getText().toString().trim();
            proSellerEmail = ProSellerEmail.getText().toString().trim();

            if (proPrice.isEmpty() &&
                    proName.isEmpty() &&
                    proDesc.isEmpty() &&
                    proSeller.isEmpty() &&
                    proSellerEmail.isEmpty() &&
                    proSellerNum.isEmpty()) {
                finish();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to discard the fields? ");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onResume();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}