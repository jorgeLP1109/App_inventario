package com.inventario.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.inventario.app.R;
import com.inventario.app.adapters.SaleItemAdapter;
import com.inventario.app.models.Product;
import com.inventario.app.models.Sale;
import com.inventario.app.utils.FirebaseHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewSaleActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SaleItemAdapter adapter;
    private List<Sale.SaleItem> items = new ArrayList<>();
    private TextView totalText;
    private Button addItemButton, completeSaleButton;
    private RadioGroup paymentTypeGroup;
    private RadioButton radioContado, radioCredito, radioNeki;
    private EditText customerNameInput;
    private Map<String, Product> productsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sale);

        recyclerView = findViewById(R.id.saleItemsRecyclerView);
        totalText = findViewById(R.id.totalText);
        addItemButton = findViewById(R.id.addItemButton);
        completeSaleButton = findViewById(R.id.completeSaleButton);
        paymentTypeGroup = findViewById(R.id.paymentTypeGroup);
        radioContado = findViewById(R.id.radioContado);
        radioCredito = findViewById(R.id.radioCredito);
        radioNeki = findViewById(R.id.radioNeki);
        customerNameInput = findViewById(R.id.customerNameInput);

        paymentTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            customerNameInput.setVisibility(checkedId == R.id.radioCredito ? View.VISIBLE : View.GONE);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SaleItemAdapter(items, this::updateTotal);
        recyclerView.setAdapter(adapter);

        loadProducts();

        addItemButton.setOnClickListener(v -> showProductSelector());
        completeSaleButton.setOnClickListener(v -> completeSale());
    }

    private void loadProducts() {
        FirebaseHelper.getProductsRef().get().addOnSuccessListener(snapshot -> {
            for (DataSnapshot child : snapshot.getChildren()) {
                Product product = child.getValue(Product.class);
                if (product != null) {
                    product.setId(child.getKey());
                    productsMap.put(child.getKey(), product);
                }
            }
        });
    }

    private void showProductSelector() {
        List<String> productNames = new ArrayList<>();
        List<String> productIds = new ArrayList<>();
        for (Map.Entry<String, Product> entry : productsMap.entrySet()) {
            productNames.add(entry.getValue().getName() + " - $" + entry.getValue().getPrice());
            productIds.add(entry.getKey());
        }

        new AlertDialog.Builder(this)
            .setTitle("Seleccionar Producto")
            .setItems(productNames.toArray(new String[0]), (dialog, which) -> {
                Product product = productsMap.get(productIds.get(which));
                if (product.getStock() > 0) {
                    Sale.SaleItem item = new Sale.SaleItem(product.getId(), product.getName(), 1, 
                        product.getPrice(), product.getCostPrice());
                    items.add(item);
                    adapter.notifyDataSetChanged();
                    updateTotal();
                } else {
                    Toast.makeText(this, "Producto sin stock", Toast.LENGTH_SHORT).show();
                }
            })
            .show();
    }

    private void updateTotal() {
        double total = 0;
        for (Sale.SaleItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        totalText.setText(String.format("Total: $%.2f", total));
    }

    private void completeSale() {
        if (items.isEmpty()) {
            Toast.makeText(this, "Agregue productos a la venta", Toast.LENGTH_SHORT).show();
            return;
        }

        String paymentType = radioContado.isChecked() ? "CONTADO" : 
                            radioCredito.isChecked() ? "CREDITO" : "NEKI";
        String customerName = customerNameInput.getText().toString().trim();

        if ("CREDITO".equals(paymentType) && customerName.isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre del cliente", Toast.LENGTH_SHORT).show();
            return;
        }

        double total = 0, profit = 0;
        for (Sale.SaleItem item : items) {
            total += item.getPrice() * item.getQuantity();
            profit += (item.getPrice() - item.getCostPrice()) * item.getQuantity();
        }

        final double finalTotal = total;
        Sale sale = new Sale(items, total, profit, FirebaseHelper.getCurrentUser().getUid(), paymentType, customerName);
        FirebaseHelper.getSalesRef().push().setValue(sale)
            .addOnSuccessListener(v -> {
                updateStock();
                FirebaseHelper.logActivity("VENTA_REGISTRADA", "Total: $" + finalTotal + " (" + paymentType + ")");
                Toast.makeText(this, "Venta registrada", Toast.LENGTH_SHORT).show();
                finish();
            });
    }

    private void updateStock() {
        for (Sale.SaleItem item : items) {
            Product product = productsMap.get(item.getProductId());
            if (product != null) {
                int newStock = product.getStock() - item.getQuantity();
                FirebaseHelper.getProductsRef().child(item.getProductId()).child("stock").setValue(newStock);
            }
        }
    }
}
