package com.inventario.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.inventario.app.R;
import com.inventario.app.adapters.ProductAdapter;
import com.inventario.app.models.Product;
import com.inventario.app.utils.FirebaseHelper;
import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> products = new ArrayList<>();
    private SearchView searchView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerView = findViewById(R.id.productsRecyclerView);
        searchView = findViewById(R.id.searchView);
        fab = findViewById(R.id.fab);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(products, this, this::showRestockDialog, this::showEditDialog);
        recyclerView.setAdapter(adapter);

        loadProducts();

        fab.setOnClickListener(v -> startActivity(new Intent(this, AddProductActivity.class)));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
    }

    private void loadProducts() {
        FirebaseHelper.getProductsRef().addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                products.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Product product = child.getValue(Product.class);
                    if (product != null) {
                        product.setId(child.getKey());
                        products.add(product);
                    }
                }
                adapter.updateData();
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError error) {}
        });
    }

    private void showRestockDialog(Product product) {
        EditText input = new EditText(this);
        input.setHint("Cantidad a agregar");
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);

        new AlertDialog.Builder(this)
            .setTitle("Reponer: " + product.getName())
            .setMessage("Stock actual: " + product.getStock())
            .setView(input)
            .setPositiveButton("Agregar", (d, w) -> {
                String text = input.getText().toString();
                if (!text.isEmpty()) {
                    int quantity = Integer.parseInt(text);
                    int newStock = product.getStock() + quantity;
                    FirebaseHelper.getProductsRef().child(product.getId()).child("stock").setValue(newStock)
                        .addOnSuccessListener(v -> {
                            FirebaseHelper.logActivity("INVENTARIO_REPUESTO", product.getName() + ": +" + quantity);
                            Toast.makeText(this, "Stock actualizado", Toast.LENGTH_SHORT).show();
                        });
                }
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }

    private void showEditDialog(Product product) {
        String[] options = {"Editar", "Eliminar"};
        new AlertDialog.Builder(this)
            .setTitle(product.getName())
            .setItems(options, (d, w) -> {
                if (w == 0) editProduct(product);
                else deleteProduct(product);
            })
            .show();
    }

    private void editProduct(Product product) {
        Intent intent = new Intent(this, AddProductActivity.class);
        intent.putExtra("productId", product.getId());
        intent.putExtra("name", product.getName());
        intent.putExtra("barcode", product.getBarcode());
        intent.putExtra("categoryId", product.getCategoryId());
        intent.putExtra("price", product.getPrice());
        intent.putExtra("costPrice", product.getCostPrice());
        intent.putExtra("stock", product.getStock());
        intent.putExtra("minStock", product.getMinStock());
        startActivity(intent);
    }

    private void deleteProduct(Product product) {
        new AlertDialog.Builder(this)
            .setTitle("Eliminar Producto")
            .setMessage("¿Eliminar " + product.getName() + "?")
            .setPositiveButton("Eliminar", (d, w) -> {
                FirebaseHelper.getProductsRef().child(product.getId()).removeValue()
                    .addOnSuccessListener(v -> {
                        FirebaseHelper.logActivity("PRODUCTO_ELIMINADO", product.getName());
                        Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                    });
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }
}
