package com.inventario.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.inventario.app.R;
import com.inventario.app.adapters.NekiTransactionAdapter;
import com.inventario.app.models.Sale;
import com.inventario.app.utils.FirebaseHelper;
import java.util.ArrayList;
import java.util.List;

public class NekiTransactionsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NekiTransactionAdapter adapter;
    private List<Sale> nekiSales = new ArrayList<>();
    private TextView totalNekiText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neki_transactions);

        recyclerView = findViewById(R.id.nekiRecyclerView);
        totalNekiText = findViewById(R.id.totalNekiText);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NekiTransactionAdapter(nekiSales, sale -> {
            Intent intent = new Intent(this, SaleDetailActivity.class);
            intent.putExtra("sale", sale);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        loadNekiTransactions();
    }

    private void loadNekiTransactions() {
        FirebaseHelper.getSalesRef().orderByChild("paymentType").equalTo("NEKI")
            .addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    nekiSales.clear();
                    double total = 0;

                    for (DataSnapshot child : snapshot.getChildren()) {
                        Sale sale = child.getValue(Sale.class);
                        if (sale != null) {
                            sale.setId(child.getKey());
                            nekiSales.add(0, sale);
                            total += sale.getTotal();
                        }
                    }

                    adapter.notifyDataSetChanged();
                    totalNekiText.setText(String.format("Total Neki: $%.2f | %d transacciones", total, nekiSales.size()));
                }

                @Override
                public void onCancelled(com.google.firebase.database.DatabaseError error) {}
            });
    }
}
