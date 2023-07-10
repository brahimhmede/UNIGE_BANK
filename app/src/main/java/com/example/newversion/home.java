package com.example.newversion;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class home extends AppCompatActivity {
    private double balance = 500.0; // Initial balance amount, you can adjust this value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        updateBalance(balance); // Update the balance display

        ImageButton homeButton = findViewById(R.id.imageButton3);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(home.this, home.class);
            startActivity(intent);
        });
    }

    public void openNewFragment(View view) {
        // Replace 'YourCurrentFragment' with the fragment you want to replace
        transfer fragment = new transfer();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment); // R.id.fragmentContainer is the ID of the container where you want to replace the fragment
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @SuppressLint("SetTextI18n")
    public void updateBalance(double newBalance) {
        balance = newBalance; // Update the balance value
        // Find the balanceTextView and set the balance value
        TextView balanceTextView = findViewById(R.id.balanceTextView);
        balanceTextView.setText("Balance: $" + balance); // Customize the text format as needed
    }

    private static final int REQUEST_CODE_TRANSFER = 1002;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_TRANSFER && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("newBalance")) {
                double updatedBalance = data.getDoubleExtra("newBalance", 0.0);
                updateBalance(updatedBalance); // Update the balance value in the home activity
            }
        }
    }

    public double getBalance() {

        return balance;
    }
}
