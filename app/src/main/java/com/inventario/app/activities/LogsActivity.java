package com.inventario.app.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.inventario.app.R;
import com.inventario.app.adapters.LogAdapter;
import com.inventario.app.models.ActivityLog;
import com.inventario.app.utils.FirebaseHelper;
import java.util.ArrayList;
import java.util.List;

public class LogsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LogAdapter adapter;
    private List<ActivityLog> logs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        recyclerView = findViewById(R.id.logsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LogAdapter(logs);
        recyclerView.setAdapter(adapter);

        loadLogs();
    }

    private void loadLogs() {
        FirebaseHelper.getLogsRef().orderByChild("timestamp").limitToLast(100)
            .addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    logs.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        ActivityLog log = child.getValue(ActivityLog.class);
                        if (log != null) {
                            log.setId(child.getKey());
                            logs.add(0, log);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(com.google.firebase.database.DatabaseError error) {}
            });
    }
}
