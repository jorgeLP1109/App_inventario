package com.inventario.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.inventario.app.R;
import com.inventario.app.adapters.CreditAdapter;
import com.inventario.app.models.Sale;
import com.inventario.app.utils.FirebaseHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CreditAdapter adapter;
    private List<CreditItem> credits = new ArrayList<>();
    private TextView totalDebtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        recyclerView = findViewById(R.id.creditsRecyclerView);
        totalDebtText = findViewById(R.id.totalDebtText);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CreditAdapter(credits, this::markAsPaid, item -> {
            Intent intent = new Intent(this, CustomerDetailActivity.class);
            intent.putExtra("customerName", item.customerName);
            intent.putExtra("totalDebt", item.totalDebt);
            intent.putExtra("sales", new ArrayList<>(item.sales));
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        loadCredits();
    }

    private void loadCredits() {
        FirebaseHelper.getSalesRef().addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, CreditItem> creditMap = new HashMap<>();
                double totalDebt = 0;

                for (DataSnapshot child : snapshot.getChildren()) {
                    Sale sale = child.getValue(Sale.class);
                    if (sale != null && "CREDITO".equals(sale.getPaymentType()) && !sale.isPaid()) {
                        sale.setId(child.getKey());
                        String customer = sale.getCustomerName();
                        
                        if (creditMap.containsKey(customer)) {
                            CreditItem item = creditMap.get(customer);
                            item.totalDebt += sale.getTotal();
                            item.sales.add(sale);
                        } else {
                            CreditItem item = new CreditItem(customer, sale.getTotal());
                            item.sales.add(sale);
                            creditMap.put(customer, item);
                        }
                        totalDebt += sale.getTotal();
                    }
                }

                credits.clear();
                credits.addAll(creditMap.values());
                adapter.notifyDataSetChanged();
                totalDebtText.setText(String.format("Deuda Total: $%.2f", totalDebt));
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError error) {}
        });
    }

    private void markAsPaid(CreditItem item) {
        new AlertDialog.Builder(this)
            .setTitle("Método de Pago")
            .setMessage("¿Cómo pagó " + item.customerName + "?")
            .setPositiveButton("Contado", (d, w) -> {
                for (Sale sale : item.sales) {
                    FirebaseHelper.getSalesRef().child(sale.getId()).child("paid").setValue(true);
                }
                loadCredits();
            })
            .setNegativeButton("Neki", (d, w) -> showNekiReferenceDialog(item))
            .setNeutralButton("Cancelar", null)
            .show();
    }

    private void showNekiReferenceDialog(CreditItem item) {
        android.widget.EditText input = new android.widget.EditText(this);
        input.setHint("Código de referencia Neki");
        input.setPadding(50, 30, 50, 30);

        new AlertDialog.Builder(this)
            .setTitle("Referencia Neki")
            .setMessage("Ingrese el código de referencia de la transacción Neki:")
            .setView(input)
            .setPositiveButton("Guardar", (d, w) -> {
                String nekiRef = input.getText().toString().trim();
                if (nekiRef.isEmpty()) {
                    android.widget.Toast.makeText(this, "Debe ingresar una referencia", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }
                
                // Consolidar todos los items de las ventas en crédito
                List<Sale.SaleItem> allItems = new ArrayList<>();
                double totalAmount = 0;
                double totalProfit = 0;
                
                for (Sale sale : item.sales) {
                    if ("CREDITO".equals(sale.getPaymentType()) && !sale.isPaid()) {
                        allItems.addAll(sale.getItems());
                        totalAmount += sale.getTotal();
                        totalProfit += sale.getProfit();
                        // Marcar la venta original como pagada sin cambiar su tipo
                        FirebaseHelper.getSalesRef().child(sale.getId()).child("paid").setValue(true);
                    }
                }
                
                // Crear UNA SOLA venta Neki consolidada
                if (!allItems.isEmpty()) {
                    Sale nekiSale = new Sale(allItems, totalAmount, totalProfit, 
                        FirebaseHelper.getCurrentUser().getUid(), "NEKI", "", nekiRef);
                    FirebaseHelper.getSalesRef().push().setValue(nekiSale);
                    
                    FirebaseHelper.logActivity("PAGO_NEKI_CREDITO", 
                        "Cliente: " + item.customerName + ", Ref: " + nekiRef + ", Total: $" + totalAmount);
                }
                
                loadCredits();
                android.widget.Toast.makeText(this, "Deuda pagada con Neki - Ref: " + nekiRef, android.widget.Toast.LENGTH_LONG).show();
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }

    public static class CreditItem {
        public String customerName;
        public double totalDebt;
        public List<Sale> sales = new ArrayList<>();

        public CreditItem(String customerName, double totalDebt) {
            this.customerName = customerName;
            this.totalDebt = totalDebt;
        }
    }
}
