package com.inventario.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.inventario.app.R;
import com.inventario.app.activities.CreditsActivity.CreditItem;
import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.ViewHolder> {
    private List<CreditItem> credits;
    private OnPayClickListener listener;

    public interface OnPayClickListener {
        void onPayClick(CreditItem item);
    }

    public CreditAdapter(List<CreditItem> credits, OnPayClickListener listener) {
        this.credits = credits;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CreditItem item = credits.get(position);
        holder.customerName.setText(item.customerName);
        holder.debtAmount.setText(String.format("Debe: $%.2f", item.totalDebt));
        holder.salesCount.setText(item.sales.size() + " venta(s)");
        holder.payButton.setOnClickListener(v -> listener.onPayClick(item));
    }

    @Override
    public int getItemCount() {
        return credits.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView customerName, debtAmount, salesCount;
        Button payButton;

        ViewHolder(View view) {
            super(view);
            customerName = view.findViewById(R.id.customerName);
            debtAmount = view.findViewById(R.id.debtAmount);
            salesCount = view.findViewById(R.id.salesCount);
            payButton = view.findViewById(R.id.payButton);
        }
    }
}
