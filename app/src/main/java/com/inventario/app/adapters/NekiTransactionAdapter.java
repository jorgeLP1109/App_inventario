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

public class NekiTransactionAdapter extends RecyclerView.Adapter<NekiTransactionAdapter.ViewHolder> {
    private List<Sale> sales;
    private OnNekiClickListener listener;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    public interface OnNekiClickListener {
        void onNekiClick(Sale sale);
    }

    public NekiTransactionAdapter(List<Sale> sales, OnNekiClickListener listener) {
        this.sales = sales;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_neki_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sale sale = sales.get(position);
        holder.dateText.setText(dateFormat.format(new Date(sale.getTimestamp())));
        holder.referenceText.setText("Ref: " + (sale.getNekiReference() != null ? sale.getNekiReference() : "Sin referencia"));
        holder.amountText.setText(String.format("$%.2f", sale.getTotal()));
        holder.itemsText.setText(sale.getItems().size() + " productos");
        
        holder.itemView.setOnClickListener(v -> listener.onNekiClick(sale));
    }

    @Override
    public int getItemCount() {
        return sales.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, referenceText, amountText, itemsText;

        ViewHolder(View view) {
            super(view);
            dateText = view.findViewById(R.id.nekiDate);
            referenceText = view.findViewById(R.id.nekiReference);
            amountText = view.findViewById(R.id.nekiAmount);
            itemsText = view.findViewById(R.id.nekiItems);
        }
    }
}
