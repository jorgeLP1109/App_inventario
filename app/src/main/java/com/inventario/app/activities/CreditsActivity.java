package com.inventario.app.activities;

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
        adapter = new CreditAdapter(credits, this::markAsPaid);
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
            .setTitle("Marcar como Pagado")
            .setMessage("¿Marcar todas las deudas de " + item.customerName + " como pagadas?")
            .setPositiveButton("Sí", (d, w) -> {
                for (Sale sale : item.sales) {
                    FirebaseHelper.getSalesRef().child(sale.getId()).child("paid").setValue(true);
                }
                loadCredits();
            })
            .setNegativeButton("No", null)
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
