package com.inventario.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.inventario.app.activities.DashboardActivity;
import com.inventario.app.activities.LoginActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        
        new Handler().postDelayed(() -> {
            try {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            } catch (Exception e) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
            finish();
        }, 2000);
    }
}
