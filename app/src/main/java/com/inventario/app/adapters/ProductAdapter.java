package com.inventario.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.inventario.app.R;
import com.inventario.app.models.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> products;
    private List<Product> productsFiltered;
    private Context context;
    private OnRestockClickListener restockListener;
    private OnEditClickListener editListener;

    public interface OnRestockClickListener {
        void onRestockClick(Product product);
    }

    public interface OnEditClickListener {
        void onEditClick(Product product);
    }

    public ProductAdapter(List<Product> products, Context context, OnRestockClickListener restockListener, OnEditClickListener editListener) {
        this.products = products;
        this.productsFiltered = new ArrayList<>(products);
        this.context = context;
        this.restockListener = restockListener;
        this.editListener = editListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productsFiltered.get(position);
        holder.nameText.setText(product.getName());
        holder.priceText.setText(String.format("$%.2f", product.getPrice()));
        holder.stockText.setText("Stock: " + product.getStock());
        
        if (product.getStock() <= product.getMinStock()) {
            holder.stockText.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }
        
        holder.itemView.setOnClickListener(v -> editListener.onEditClick(product));
        holder.itemView.setOnLongClickListener(v -> {
            restockListener.onRestockClick(product);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return productsFiltered.size();
    }

    public void filter(String query) {
        productsFiltered.clear();
        if (query.isEmpty()) {
            productsFiltered.addAll(products);
        } else {
            for (Product product : products) {
                if (product.getName().toLowerCase().contains(query.toLowerCase()) ||
                    (product.getBarcode() != null && product.getBarcode().contains(query))) {
                    productsFiltered.add(product);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateData() {
        productsFiltered.clear();
        productsFiltered.addAll(products);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, priceText, stockText;

        ViewHolder(View view) {
            super(view);
            nameText = view.findViewById(R.id.productName);
            priceText = view.findViewById(R.id.productPrice);
            stockText = view.findViewById(R.id.productStock);
        }
    }
}
