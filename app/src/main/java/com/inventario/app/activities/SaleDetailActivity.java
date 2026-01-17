package com.inventario.app.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.inventario.app.R;
import com.inventario.app.adapters.SaleItemAdapter;
import com.inventario.app.models.Sale;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SaleDetailActivity extends AppCompatActivity {
    private TextView dateText, totalText, profitText, paymentTypeText, customerText;
    private RecyclerView itemsRecyclerView;
    private SaleItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);

        dateText = findViewById(R.id.detailDate);
        totalText = findViewById(R.id.detailTotal);
        profitText = findViewById(R.id.detailProfit);
        paymentTypeText = findViewById(R.id.detailPaymentType);
        customerText = findViewById(R.id.detailCustomer);
        itemsRecyclerView = findViewById(R.id.detailItemsRecyclerView);

        Sale sale = (Sale) getIntent().getSerializableExtra("sale");
        
        if (sale != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            dateText.setText("Fecha: " + dateFormat.format(new Date(sale.getTimestamp())));
            totalText.setText(String.format("Total: $%.2f", sale.getTotal()));
            profitText.setText(String.format("Ganancia: $%.2f", sale.getProfit()));
            paymentTypeText.setText("Pago: " + sale.getPaymentType());
            
            if (sale.getCustomerName() != null && !sale.getCustomerName().isEmpty()) {
                customerText.setText("Cliente: " + sale.getCustomerName());
            }

            itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new SaleItemAdapter(sale.getItems());
            itemsRecyclerView.setAdapter(adapter);
        }
    }
}
