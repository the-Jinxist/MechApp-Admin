package com.mechanics.mechadmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.mechanics.mechadmin.models.Help_and_NotificationModel;

import java.util.ArrayList;

public class HelpFragmentAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Help_and_NotificationModel> values;
    private final int image;
    private final DatabaseReference databaseReference;
    private final ArrayList<String> keyArray;

    HelpFragmentAdapter(Context context, ArrayList<Help_and_NotificationModel> values,
                        int image, DatabaseReference databaseReference, ArrayList<String> keyArray) {
        this.context = context;
        this.values = values;
        this.image = image;
        this.databaseReference = databaseReference;
        this.keyArray = keyArray;
    }

    @Override
    public int getCount() {
        return values.size();
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
        View rowView = inflater.inflate(R.layout.helpnoti_item, parent, false);

        TextView message = rowView.findViewById(R.id.eachMessage);
        ImageView removeItem = rowView.findViewById(R.id.removeItem);
        ImageView imageIcon = rowView.findViewById(R.id.imageIcon);

        message.setText(values.get(position).getItem_message());
        imageIcon.setImageResource(image);
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete the car?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference.child(keyArray.get(position)).removeValue();
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

        return rowView;
    }
}