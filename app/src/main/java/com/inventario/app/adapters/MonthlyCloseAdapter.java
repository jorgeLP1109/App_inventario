package com.inventario.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.inventario.app.R;
import com.inventario.app.models.MonthlyClose;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MonthlyCloseAdapter extends RecyclerView.Adapter<MonthlyCloseAdapter.ViewHolder> {
    private List<MonthlyClose> closes;
    private OnCloseClickListener listener;
    private SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy", new Locale("es", "ES"));
    private SimpleDateFormat inputFormat = new SimpleDateFormat("MM-yyyy", Locale.getDefault());

    public interface OnCloseClickListener {
        void onCloseClick(MonthlyClose close);
    }

    public MonthlyCloseAdapter(List<MonthlyClose> closes, OnCloseClickListener listener) {
        this.closes = closes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monthly_close, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonthlyClose close = closes.get(position);
        
        try {
            Date date = inputFormat.parse(close.getMonthYear());
            holder.monthText.setText(monthFormat.format(date));
        } catch (Exception e) {
            holder.monthText.setText(close.getMonthYear());
        }
        
        holder.totalText.setText(String.format("Total: $%.2f", close.getTotalSales()));
        holder.profitText.setText(String.format("Ganancia: $%.2f", close.getTotalProfit()));
        holder.countText.setText(close.getSalesCount() + " ventas");
        
        holder.itemView.setOnClickListener(v -> listener.onCloseClick(close));
    }

    @Override
    public int getItemCount() {
        return closes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView monthText, totalText, profitText, countText;

        ViewHolder(View view) {
            super(view);
            monthText = view.findViewById(R.id.closeMonthText);
            totalText = view.findViewById(R.id.closeTotalText);
            profitText = view.findViewById(R.id.closeProfitText);
            countText = view.findViewById(R.id.closeCountText);
        }
    }
}
