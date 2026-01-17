package com.inventario.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.inventario.app.R;
import com.inventario.app.models.Sale;
import java.util.List;

public class SaleItemAdapter extends RecyclerView.Adapter<SaleItemAdapter.ViewHolder> {
    private List<Sale.SaleItem> items;
    private Runnable onUpdateCallback;
    private boolean editable;

    public SaleItemAdapter(List<Sale.SaleItem> items, Runnable onUpdateCallback) {
        this.items = items;
        this.onUpdateCallback = onUpdateCallback;
        this.editable = true;
    }

    public SaleItemAdapter(List<Sale.SaleItem> items) {
        this.items = items;
        this.editable = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sale_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sale.SaleItem item = items.get(position);
        holder.nameText.setText(item.getProductName());
        holder.priceText.setText(String.format("$%.2f", item.getPrice()));
        holder.quantityText.setText(String.valueOf(item.getQuantity()));

        if (editable) {
            holder.plusButton.setVisibility(View.VISIBLE);
            holder.minusButton.setVisibility(View.VISIBLE);
            holder.removeButton.setVisibility(View.VISIBLE);

            holder.plusButton.setOnClickListener(v -> {
                item.setQuantity(item.getQuantity() + 1);
                notifyItemChanged(position);
                onUpdateCallback.run();
            });

            holder.minusButton.setOnClickListener(v -> {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                    notifyItemChanged(position);
                    onUpdateCallback.run();
                }
            });

            holder.removeButton.setOnClickListener(v -> {
                items.remove(position);
                notifyItemRemoved(position);
                onUpdateCallback.run();
            });
        } else {
            holder.plusButton.setVisibility(View.GONE);
            holder.minusButton.setVisibility(View.GONE);
            holder.removeButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, priceText, quantityText;
        Button plusButton, minusButton, removeButton;

        ViewHolder(View view) {
            super(view);
            nameText = view.findViewById(R.id.itemName);
            priceText = view.findViewById(R.id.itemPrice);
            quantityText = view.findViewById(R.id.itemQuantity);
            plusButton = view.findViewById(R.id.plusButton);
            minusButton = view.findViewById(R.id.minusButton);
            removeButton = view.findViewById(R.id.removeButton);
        }
    }
}
