package com.inventario.app.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inventario.app.models.ActivityLog;

public class FirebaseHelper {
    private static FirebaseDatabase database;
    private static FirebaseAuth auth;

    public static FirebaseDatabase getDatabase() {
        if (database == null) {
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }
        return database;
    }

    public static FirebaseAuth getAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    public static DatabaseReference getUsersRef() {
        return getDatabase().getReference("users");
    }

    public static DatabaseReference getProductsRef() {
        return getDatabase().getReference("products");
    }

    public static DatabaseReference getCategoriesRef() {
        return getDatabase().getReference("categories");
    }

    public static DatabaseReference getSalesRef() {
        return getDatabase().getReference("sales");
    }

    public static DatabaseReference getLogsRef() {
        return getDatabase().getReference("logs");
    }

    public static FirebaseUser getCurrentUser() {
        return getAuth().getCurrentUser();
    }

    public static void logActivity(String action, String details) {
        FirebaseUser user = getCurrentUser();
        if (user != null) {
            getUsersRef().child(user.getUid()).get().addOnSuccessListener(snapshot -> {
                String userName = snapshot.child("name").getValue(String.class);
                ActivityLog log = new ActivityLog(user.getUid(), userName, action, details);
                getLogsRef().push().setValue(log);
            });
        }
    }
}
