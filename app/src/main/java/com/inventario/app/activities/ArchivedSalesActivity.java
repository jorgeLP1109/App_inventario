package com.inventario.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.inventario.app.R;
import com.inventario.app.adapters.SaleAdapter;
import com.inventario.app.models.MonthlyClose;
import com.inventario.app.models.Sale;
import com.inventario.app.utils.FirebaseHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ArchivedSalesActivity extends AppCompatActivity {
    private TextView monthText, summaryText;
    private RecyclerView recyclerView;
    private SaleAdapter adapter;
    private List<Sale> sales = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived_sales);

        monthText = findViewById(R.id.archivedMonthText);
        summaryText = findViewById(R.id.archivedSummaryText);
        recyclerView = findViewById(R.id.archivedSalesRecyclerView);

        String monthYear = getIntent().getStringExtra("monthYear");
        MonthlyClose close = (MonthlyClose) getIntent().getSerializableExtra("close");

        SimpleDateFormat inputFormat = new SimpleDateFormat("MM-yyyy", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM yyyy", new Locale("es", "ES"));
        
        try {
            Date date = inputFormat.parse(monthYear);
            monthText.setText("Ventas de " + outputFormat.format(date));
        } catch (Exception e) {
            monthText.setText("Ventas de " + monthYear);
        }

        if (close != null) {
            summaryText.setText(String.format("Total: $%.2f | Ganancia: $%.2f | Ventas: %d",
                    close.getTotalSales(), close.getTotalProfit(), close.getSalesCount()));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SaleAdapter(sales, sale -> {
            Intent intent = new Intent(this, SaleDetailActivity.class);
            intent.putExtra("sale", sale);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        loadArchivedSales(monthYear);
    }

    private void loadArchivedSales(String monthYear) {
        FirebaseHelper.getArchivedSalesRef(monthYear).addListenerForSingleValueEvent(
            new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    sales.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Sale sale = child.getValue(Sale.class);
                        if (sale != null) {
                            sale.setId(child.getKey());
                            sales.add(0, sale);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(com.google.firebase.database.DatabaseError error) {}
            });
    }
}
