package com.inventario.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.inventario.app.R;
import com.inventario.app.adapters.SaleAdapter;
import com.inventario.app.models.Sale;
import java.util.ArrayList;

public class CustomerDetailActivity extends AppCompatActivity {
    private TextView customerNameText, totalDebtText, salesCountText;
    private RecyclerView salesRecyclerView;
    private SaleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        customerNameText = findViewById(R.id.customerNameText);
        totalDebtText = findViewById(R.id.totalDebtText);
        salesCountText = findViewById(R.id.salesCountText);
        salesRecyclerView = findViewById(R.id.customerSalesRecyclerView);

        String customerName = getIntent().getStringExtra("customerName");
        double totalDebt = getIntent().getDoubleExtra("totalDebt", 0);
        ArrayList<Sale> sales = (ArrayList<Sale>) getIntent().getSerializableExtra("sales");

        customerNameText.setText("Cliente: " + customerName);
        totalDebtText.setText(String.format("Deuda Total: $%.2f", totalDebt));
        salesCountText.setText(sales.size() + " venta(s) pendiente(s)");

        salesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SaleAdapter(sales, sale -> {
            Intent intent = new Intent(this, SaleDetailActivity.class);
            intent.putExtra("sale", sale);
            startActivity(intent);
        });
        salesRecyclerView.setAdapter(adapter);
    }
}
