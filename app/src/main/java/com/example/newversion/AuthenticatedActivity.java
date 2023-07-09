package com.example.newversion;

import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthenticatedActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private String getEmailFromEditText() {
        EditText emailEditText = findViewById(R.id.idEdtUserName);
        return emailEditText.getText().toString().trim();
    }

    private String getPasswordFromEditText() {
        EditText passwordEditText = findViewById(R.id.idEdtPassword);
        return passwordEditText.getText().toString().trim();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticated);

        firebaseAuth = FirebaseAuth.getInstance();
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String email = getEmailFromEditText();
        String password = getPasswordFromEditText();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // User is authenticated, continue to the next activity
                        Intent intent = new Intent(AuthenticatedActivity.this, home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // User is not authenticated, display registration message
                        Toast.makeText(AuthenticatedActivity.this, "Please register first", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}