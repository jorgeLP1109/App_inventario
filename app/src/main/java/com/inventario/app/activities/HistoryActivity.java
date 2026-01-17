package com.inventario.app.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.inventario.app.R;
import com.inventario.app.adapters.MonthlyCloseAdapter;
import com.inventario.app.models.MonthlyClose;
import com.inventario.app.utils.FirebaseHelper;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MonthlyCloseAdapter adapter;
    private List<MonthlyClose> closes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.historyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new MonthlyCloseAdapter(closes, close -> {
            Intent intent = new Intent(this, ArchivedSalesActivity.class);
            intent.putExtra("monthYear", close.getMonthYear());
            intent.putExtra("close", close);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        loadHistory();
    }

    private void loadHistory() {
        FirebaseHelper.getMonthlyClosesRef().orderByChild("closeDate")
            .addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    closes.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        MonthlyClose close = child.getValue(MonthlyClose.class);
                        if (close != null) {
                            close.setId(child.getKey());
                            closes.add(0, close);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(com.google.firebase.database.DatabaseError error) {}
            });
    }
}
