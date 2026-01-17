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
    private OnPayClickListener payListener;
    private OnCreditClickListener clickListener;

    public interface OnPayClickListener {
        void onPayClick(CreditItem item);
    }

    public interface OnCreditClickListener {
        void onCreditClick(CreditItem item);
    }

    public CreditAdapter(List<CreditItem> credits, OnPayClickListener payListener) {
        this.credits = credits;
        this.payListener = payListener;
    }

    public CreditAdapter(List<CreditItem> credits, OnPayClickListener payListener, OnCreditClickListener clickListener) {
        this.credits = credits;
        this.payListener = payListener;
        this.clickListener = clickListener;
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
        holder.payButton.setOnClickListener(v -> payListener.onPayClick(item));
        
        if (clickListener != null) {
            holder.itemView.setOnClickListener(v -> clickListener.onCreditClick(item));
        }
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
