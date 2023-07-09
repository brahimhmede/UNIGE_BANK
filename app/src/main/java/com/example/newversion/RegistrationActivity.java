package com.example.newversion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText emailEditText, passwordEditText, phoneNumberEditText, nameEditText;

//    public RegistrationActivity(FirebaseAuth firebaseAuth) {
//        this.firebaseAuth = firebaseAuth;
//    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, AuthenticatedActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();

        // Get references to UI elements
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        nameEditText = findViewById(R.id.nameEditText);
        Button registerButton;
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String phoneNumber = phoneNumberEditText.getText().toString().trim();
            String name = nameEditText.getText().toString().trim();

            // Perform input validation
            if (email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty() || name.isEmpty()) {
                Toast.makeText(RegistrationActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            } else

            {
                // Create user in Firebase Authentication
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Registration successful, save user data to Firestore
                                String userId = firebaseAuth.getCurrentUser().getUid();
                                user user = new user(email, userId, phoneNumber, name);

                                // Redirect user to authenticated activity
                                startActivity(new Intent(RegistrationActivity.this, AuthenticatedActivity.class));
                                finish();
                            } else {
                                // Registration failed
                                Toast.makeText(RegistrationActivity.this, "Registration failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }


}

