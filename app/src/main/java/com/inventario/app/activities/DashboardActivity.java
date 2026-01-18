package com.inventario.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.firebase.database.DataSnapshot;
import com.inventario.app.R;
import com.inventario.app.utils.FirebaseHelper;

public class DashboardActivity extends AppCompatActivity {
    private TextView totalProductsText, lowStockText, todaySalesText;
    private CardView productsCard, salesCard, reportsCard, categoriesCard, logsCard, creditsCard, monthlyCloseCard, historyCard, nekiCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        totalProductsText = findViewById(R.id.totalProductsText);
        lowStockText = findViewById(R.id.lowStockText);
        todaySalesText = findViewById(R.id.todaySalesText);
        productsCard = findViewById(R.id.productsCard);
        salesCard = findViewById(R.id.salesCard);
        reportsCard = findViewById(R.id.reportsCard);
        categoriesCard = findViewById(R.id.categoriesCard);
        logsCard = findViewById(R.id.logsCard);
        creditsCard = findViewById(R.id.creditsCard);
        monthlyCloseCard = findViewById(R.id.monthlyCloseCard);
        historyCard = findViewById(R.id.historyCard);
        nekiCard = findViewById(R.id.nekiCard);

        loadDashboardData();

        productsCard.setOnClickListener(v -> startActivity(new Intent(this, ProductsActivity.class)));
        salesCard.setOnClickListener(v -> startActivity(new Intent(this, SalesActivity.class)));
        reportsCard.setOnClickListener(v -> startActivity(new Intent(this, ReportsActivity.class)));
        categoriesCard.setOnClickListener(v -> startActivity(new Intent(this, CategoriesActivity.class)));
        logsCard.setOnClickListener(v -> startActivity(new Intent(this, LogsActivity.class)));
        creditsCard.setOnClickListener(v -> startActivity(new Intent(this, CreditsActivity.class)));
        monthlyCloseCard.setOnClickListener(v -> startActivity(new Intent(this, MonthlyCloseActivity.class)));
        historyCard.setOnClickListener(v -> startActivity(new Intent(this, HistoryActivity.class)));
        nekiCard.setOnClickListener(v -> startActivity(new Intent(this, NekiTransactionsActivity.class)));
    }

    private void loadDashboardData() {
        FirebaseHelper.getProductsRef().addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int total = (int) snapshot.getChildrenCount();
                int lowStock = 0;
                for (DataSnapshot child : snapshot.getChildren()) {
                    Integer stock = child.child("stock").getValue(Integer.class);
                    Integer minStock = child.child("minStock").getValue(Integer.class);
                    if (stock != null && minStock != null && stock <= minStock) lowStock++;
                }
                totalProductsText.setText(String.valueOf(total));
                lowStockText.setText(String.valueOf(lowStock));
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError error) {}
        });

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calendar.set(java.util.Calendar.MINUTE, 0);
        calendar.set(java.util.Calendar.SECOND, 0);
        calendar.set(java.util.Calendar.MILLISECOND, 0);
        long todayStart = calendar.getTimeInMillis();

        FirebaseHelper.getSalesRef().addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                double total = 0;
                for (DataSnapshot child : snapshot.getChildren()) {
                    Long timestamp = child.child("timestamp").getValue(Long.class);
                    if (timestamp != null && timestamp >= todayStart) {
                        Double saleTotal = child.child("total").getValue(Double.class);
                        if (saleTotal != null) total += saleTotal;
                    }
                }
                todaySalesText.setText(String.format("$%.2f", total));
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError error) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            FirebaseHelper.getAuth().signOut();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
