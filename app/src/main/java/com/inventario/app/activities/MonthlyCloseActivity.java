package com.inventario.app.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.inventario.app.R;
import com.inventario.app.models.MonthlyClose;
import com.inventario.app.models.Sale;
import com.inventario.app.utils.FirebaseHelper;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MonthlyCloseActivity extends AppCompatActivity {
    private TextView currentMonthText, totalSalesText, totalProfitText, salesCountText;
    private Button closeMonthButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_close);

        currentMonthText = findViewById(R.id.currentMonthText);
        totalSalesText = findViewById(R.id.totalSalesText);
        totalProfitText = findViewById(R.id.totalProfitText);
        salesCountText = findViewById(R.id.salesCountText);
        closeMonthButton = findViewById(R.id.closeMonthButton);

        loadCurrentMonthData();

        closeMonthButton.setOnClickListener(v -> confirmClose());
    }

    private void loadCurrentMonthData() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy", new Locale("es", "ES"));
        currentMonthText.setText("Mes Actual: " + monthFormat.format(cal.getTime()));

        FirebaseHelper.getSalesRef().addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                double totalSales = 0;
                double totalProfit = 0;
                int count = 0;

                for (DataSnapshot child : snapshot.getChildren()) {
                    Sale sale = child.getValue(Sale.class);
                    if (sale != null) {
                        totalSales += sale.getTotal();
                        totalProfit += sale.getProfit();
                        count++;
                    }
                }

                totalSalesText.setText(String.format("Total Ventas: $%.2f", totalSales));
                totalProfitText.setText(String.format("Total Ganancias: $%.2f", totalProfit));
                salesCountText.setText("Número de Ventas: " + count);
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError error) {}
        });
    }

    private void confirmClose() {
        new AlertDialog.Builder(this)
            .setTitle("Cerrar Mes")
            .setMessage("¿Estás seguro de cerrar el mes actual?\n\n" +
                    "Esto archivará todas las ventas actuales y comenzarás un nuevo mes.\n\n" +
                    "Esta acción no se puede deshacer.")
            .setPositiveButton("Sí, Cerrar", (d, w) -> performClose())
            .setNegativeButton("Cancelar", null)
            .show();
    }

    private void performClose() {
        closeMonthButton.setEnabled(false);
        
        FirebaseHelper.getSalesRef().addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat monthYearFormat = new SimpleDateFormat("MM-yyyy", Locale.getDefault());
                String monthYear = monthYearFormat.format(cal.getTime());

                double totalSales = 0;
                double totalProfit = 0;
                int count = 0;
                Map<String, Object> salesToArchive = new HashMap<>();

                for (DataSnapshot child : snapshot.getChildren()) {
                    Sale sale = child.getValue(Sale.class);
                    if (sale != null) {
                        sale.setId(child.getKey());
                        salesToArchive.put(child.getKey(), sale);
                        totalSales += sale.getTotal();
                        totalProfit += sale.getProfit();
                        count++;
                    }
                }

                if (count == 0) {
                    Toast.makeText(MonthlyCloseActivity.this, "No hay ventas para cerrar", Toast.LENGTH_SHORT).show();
                    closeMonthButton.setEnabled(true);
                    return;
                }

                // Guardar ventas archivadas
                FirebaseHelper.getArchivedSalesRef(monthYear).setValue(salesToArchive);

                // Crear registro de cierre
                MonthlyClose close = new MonthlyClose(monthYear, totalSales, totalProfit, count, 
                        FirebaseHelper.getCurrentUser().getEmail());
                FirebaseHelper.getMonthlyClosesRef().push().setValue(close);

                // Eliminar ventas actuales
                FirebaseHelper.getSalesRef().removeValue();

                // Log
                FirebaseHelper.logActivity("CIERRE_MENSUAL", "Mes: " + monthYear + ", Ventas: " + count);

                Toast.makeText(MonthlyCloseActivity.this, "Mes cerrado exitosamente", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError error) {
                Toast.makeText(MonthlyCloseActivity.this, "Error al cerrar mes", Toast.LENGTH_SHORT).show();
                closeMonthButton.setEnabled(true);
            }
        });
    }
}
