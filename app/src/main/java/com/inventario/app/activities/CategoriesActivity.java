package com.inventario.app.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.inventario.app.R;
import com.inventario.app.adapters.CategoryAdapter;
import com.inventario.app.models.Category;
import com.inventario.app.utils.FirebaseHelper;
import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private List<Category> categories = new ArrayList<>();
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        recyclerView = findViewById(R.id.categoriesRecyclerView);
        fab = findViewById(R.id.fab);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryAdapter(categories);
        recyclerView.setAdapter(adapter);

        loadCategories();
        initializeDefaultCategories();

        fab.setOnClickListener(v -> showAddCategoryDialog());
    }

    private void initializeDefaultCategories() {
        FirebaseHelper.getCategoriesRef().get().addOnSuccessListener(snapshot -> {
            if (snapshot.getChildrenCount() == 0) {
                String[] defaults = {"Comida", "Snacks", "Gaseosas", "Bebidas", "Limpieza"};
                String[] colors = {"#FF5722", "#FFC107", "#4CAF50", "#2196F3", "#9C27B0"};
                for (int i = 0; i < defaults.length; i++) {
                    Category cat = new Category(defaults[i], colors[i]);
                    FirebaseHelper.getCategoriesRef().push().setValue(cat);
                }
            }
        });
    }

    private void loadCategories() {
        FirebaseHelper.getCategoriesRef().addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                categories.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Category category = child.getValue(Category.class);
                    if (category != null) {
                        category.setId(child.getKey());
                        categories.add(category);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError error) {}
        });
    }

    private void showAddCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nueva Categoría");

        EditText input = new EditText(this);
        input.setHint("Nombre de la categoría");
        builder.setView(input);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String name = input.getText().toString().trim();
            if (!name.isEmpty()) {
                Category category = new Category(name, "#607D8B");
                FirebaseHelper.getCategoriesRef().push().setValue(category)
                    .addOnSuccessListener(v -> {
                        FirebaseHelper.logActivity("CATEGORIA_CREADA", "Categoría: " + name);
                        Toast.makeText(this, "Categoría creada", Toast.LENGTH_SHORT).show();
                    });
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
}
