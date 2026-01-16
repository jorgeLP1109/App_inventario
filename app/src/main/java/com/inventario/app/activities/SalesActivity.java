package com.inventario.app.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.inventario.app.R;
import com.inventario.app.adapters.SaleAdapter;
import com.inventario.app.models.Sale;
import com.inventario.app.utils.FirebaseHelper;
import java.util.ArrayList;
import java.util.List;

public class SalesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SaleAdapter adapter;
    private List<Sale> sales = new ArrayList<>();
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        recyclerView = findViewById(R.id.salesRecyclerView);
        fab = findViewById(R.id.fab);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SaleAdapter(sales);
        recyclerView.setAdapter(adapter);

        loadSales();

        fab.setOnClickListener(v -> startActivity(new Intent(this, NewSaleActivity.class)));
    }

    private void loadSales() {
        FirebaseHelper.getSalesRef().orderByChild("timestamp").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
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
