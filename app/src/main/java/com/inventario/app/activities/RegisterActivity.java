package com.inventario.app.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.inventario.app.R;
import com.inventario.app.models.User;
import com.inventario.app.utils.FirebaseHelper;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameInput, emailInput, passwordInput;
    private RadioGroup roleGroup;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        roleGroup = findViewById(R.id.roleGroup);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> register());
    }

    private void register() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String role = roleGroup.getCheckedRadioButtonId() == R.id.radioAdmin ? "admin" : "vendedor";

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseHelper.getAuth().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener(result -> {
                String uid = result.getUser().getUid();
                User user = new User(uid, email, name, role);
                FirebaseHelper.getUsersRef().child(uid).setValue(user)
                    .addOnSuccessListener(v -> {
                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        finish();
                    });
            })
            .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
