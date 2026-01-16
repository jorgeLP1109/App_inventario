package com.inventario.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.inventario.app.R;
import com.inventario.app.models.ActivityLog;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {
    private List<ActivityLog> logs;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

    public LogAdapter(List<ActivityLog> logs) {
        this.logs = logs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActivityLog log = logs.get(position);
        holder.userText.setText(log.getUserName());
        holder.actionText.setText(log.getAction());
        holder.detailsText.setText(log.getDetails());
        holder.timeText.setText(dateFormat.format(new Date(log.getTimestamp())));
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userText, actionText, detailsText, timeText;

        ViewHolder(View view) {
            super(view);
            userText = view.findViewById(R.id.logUser);
            actionText = view.findViewById(R.id.logAction);
            detailsText = view.findViewById(R.id.logDetails);
            timeText = view.findViewById(R.id.logTime);
        }
    }
}
