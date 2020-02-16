package com.mechanics.mechadmin.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mechanics.mechadmin.R;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminLogsAdapter extends RecyclerView.Adapter<AdminLogsAdapter.ViewHolder> {

    private List<HashMap<String, Object>> logs;

    public AdminLogsAdapter(List<HashMap<String, Object>> mLogs){
        this.logs = mLogs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HashMap<String, Object> log = logs.get(position);

        TextView adminId = holder.itemView.findViewById(R.id.log_view_admin_id);
        TextView date = holder.itemView.findViewById(R.id.log_view_admin_date);

        adminId.setText(log.get("adminId").toString());
        Long timeInMillis = Long.valueOf(log.get("currentTimeInMillis").toString());

        date.setText(LoginUtils.convertDate(timeInMillis));
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ViewHolder(View itemView) {
            super(itemView);
        }

    }

}
