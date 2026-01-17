package com.inventario.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.inventario.app.R;
import com.inventario.app.models.Sale;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.ViewHolder> {
    private List<Sale> sales;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    private OnSaleClickListener listener;

    public interface OnSaleClickListener {
        void onSaleClick(Sale sale);
    }

    public SaleAdapter(List<Sale> sales) {
        this.sales = sales;
    }

    public SaleAdapter(List<Sale> sales, OnSaleClickListener listener) {
        this.sales = sales;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sale, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sale sale = sales.get(position);
        holder.dateText.setText(dateFormat.format(new Date(sale.getTimestamp())));
        holder.totalText.setText(String.format("$%.2f", sale.getTotal()));
        holder.profitText.setText(String.format("Ganancia: $%.2f", sale.getProfit()));
        holder.itemsText.setText(sale.getItems().size() + " productos");
        
        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onSaleClick(sale));
        }
    }

    @Override
    public int getItemCount() {
        return sales.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, totalText, profitText, itemsText;

        ViewHolder(View view) {
            super(view);
            dateText = view.findViewById(R.id.saleDate);
            totalText = view.findViewById(R.id.saleTotal);
            profitText = view.findViewById(R.id.saleProfit);
            itemsText = view.findViewById(R.id.saleItems);
        }
    }
}
