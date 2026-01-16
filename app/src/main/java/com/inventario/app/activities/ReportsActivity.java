package com.inventario.app.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.inventario.app.R;
import com.inventario.app.models.Sale;
import com.inventario.app.utils.FirebaseHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportsActivity extends AppCompatActivity {
    private TextView totalSalesText, totalProfitText, avgSaleText;
    private PieChart topProductsChart;
    private BarChart salesChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        totalSalesText = findViewById(R.id.totalSalesText);
        totalProfitText = findViewById(R.id.totalProfitText);
        avgSaleText = findViewById(R.id.avgSaleText);
        topProductsChart = findViewById(R.id.topProductsChart);
        salesChart = findViewById(R.id.salesChart);

        loadReports();
    }

    private void loadReports() {
        FirebaseHelper.getSalesRef().addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                double totalSales = 0, totalProfit = 0;
                int count = 0;
                Map<String, Integer> productSales = new HashMap<>();
                Map<String, Double> productProfits = new HashMap<>();

                for (DataSnapshot child : snapshot.getChildren()) {
                    Sale sale = child.getValue(Sale.class);
                    if (sale != null && sale.getItems() != null) {
                        totalSales += sale.getTotal();
                        totalProfit += sale.getProfit();
                        count++;

                        for (Sale.SaleItem item : sale.getItems()) {
                            String productName = item.getProductName();
                            int quantity = item.getQuantity();
                            double itemProfit = (item.getPrice() - item.getCostPrice()) * quantity;

                            productSales.put(productName, 
                                productSales.getOrDefault(productName, 0) + quantity);
                            productProfits.put(productName, 
                                productProfits.getOrDefault(productName, 0.0) + itemProfit);
                        }
                    }
                }

                totalSalesText.setText(String.format("$%.2f", totalSales));
                totalProfitText.setText(String.format("$%.2f", totalProfit));
                avgSaleText.setText(count > 0 ? String.format("$%.2f", totalSales / count) : "$0.00");

                setupTopProductsChart(productSales);
                setupProfitChart(productProfits);
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError error) {}
        });
    }

    private void setupTopProductsChart(Map<String, Integer> productSales) {
        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : productSales.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Productos Más Vendidos");
        dataSet.setColors(new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN});
        PieData data = new PieData(dataSet);
        topProductsChart.setData(data);
        topProductsChart.invalidate();
    }

    private void setupProfitChart(Map<String, Double> productProfits) {
        List<BarEntry> entries = new ArrayList<>();
        int i = 0;
        for (Double profit : productProfits.values()) {
            entries.add(new BarEntry(i++, profit.floatValue()));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Ganancias por Producto");
        dataSet.setColor(Color.GREEN);
        BarData data = new BarData(dataSet);
        salesChart.setData(data);
        salesChart.invalidate();
    }
}
