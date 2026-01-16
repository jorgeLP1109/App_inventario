package com.inventario.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.inventario.app.R;
import com.inventario.app.models.Category;
import com.inventario.app.models.Product;
import com.inventario.app.utils.FirebaseHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {
    private EditText nameInput, barcodeInput, priceInput, costInput, stockInput, minStockInput;
    private Spinner categorySpinner;
    private Button saveButton, scanButton;
    private List<Category> categories = new ArrayList<>();
    private Map<String, String> categoryMap = new HashMap<>();
    private String editingProductId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        nameInput = findViewById(R.id.nameInput);
        barcodeInput = findViewById(R.id.barcodeInput);
        priceInput = findViewById(R.id.priceInput);
        costInput = findViewById(R.id.costInput);
        stockInput = findViewById(R.id.stockInput);
        minStockInput = findViewById(R.id.minStockInput);
        categorySpinner = findViewById(R.id.categorySpinner);
        saveButton = findViewById(R.id.saveButton);
        scanButton = findViewById(R.id.scanButton);

        loadCategories();

        editingProductId = getIntent().getStringExtra("productId");
        if (editingProductId != null) {
            setTitle("Editar Producto");
        }

        scanButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, BarcodeScannerActivity.class);
            startActivityForResult(intent, 100);
        });

        saveButton.setOnClickListener(v -> saveProduct());
    }

    private void loadCategories() {
        FirebaseHelper.getCategoriesRef().get().addOnSuccessListener(snapshot -> {
            List<String> names = new ArrayList<>();
            for (DataSnapshot child : snapshot.getChildren()) {
                Category cat = child.getValue(Category.class);
                if (cat != null) {
                    cat.setId(child.getKey());
                    categories.add(cat);
                    names.add(cat.getName());
                    categoryMap.put(cat.getName(), cat.getId());
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(adapter);
            
            if (editingProductId != null) {
                loadProductData();
            }
        });
    }

    private void loadProductData() {
        nameInput.setText(getIntent().getStringExtra("name"));
        barcodeInput.setText(getIntent().getStringExtra("barcode"));
        priceInput.setText(String.valueOf(getIntent().getDoubleExtra("price", 0)));
        costInput.setText(String.valueOf(getIntent().getDoubleExtra("costPrice", 0)));
        stockInput.setText(String.valueOf(getIntent().getIntExtra("stock", 0)));
        minStockInput.setText(String.valueOf(getIntent().getIntExtra("minStock", 0)));
        
        String categoryId = getIntent().getStringExtra("categoryId");
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(categoryId)) {
                categorySpinner.setSelection(i);
                break;
            }
        }
    }

    private void saveProduct() {
        String name = nameInput.getText().toString().trim();
        String barcode = barcodeInput.getText().toString().trim();
        String priceStr = priceInput.getText().toString().trim();
        String costStr = costInput.getText().toString().trim();
        String stockStr = stockInput.getText().toString().trim();
        String minStockStr = minStockInput.getText().toString().trim();
        String categoryName = categorySpinner.getSelectedItem().toString();

        if (name.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
            Toast.makeText(this, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        double cost = costStr.isEmpty() ? 0 : Double.parseDouble(costStr);
        int stock = Integer.parseInt(stockStr);
        int minStock = minStockStr.isEmpty() ? 5 : Integer.parseInt(minStockStr);
        String categoryId = categoryMap.get(categoryName);

        Product product = new Product(name, barcode, categoryId, price, cost, stock, minStock, 
            FirebaseHelper.getCurrentUser().getUid());

        if (editingProductId != null) {
            FirebaseHelper.getProductsRef().child(editingProductId).setValue(product)
                .addOnSuccessListener(v -> {
                    FirebaseHelper.logActivity("PRODUCTO_EDITADO", "Producto: " + name);
                    Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show();
                    finish();
                });
        } else {
            FirebaseHelper.getProductsRef().push().setValue(product)
                .addOnSuccessListener(v -> {
                    FirebaseHelper.logActivity("PRODUCTO_CREADO", "Producto: " + name);
                    Toast.makeText(this, "Producto guardado", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            String barcode = data.getStringExtra("barcode");
            barcodeInput.setText(barcode);
        }
    }
}
