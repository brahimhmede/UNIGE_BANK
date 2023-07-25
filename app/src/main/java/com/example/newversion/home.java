package com.example.newversion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

public class home extends AppCompatActivity {

    private static final String PREFS_NAME = "user_balances";
    private static final String BALANCE_KEY = "balance_key";
    private double balance; // User-specific balance.
    private TextView balanceTextView;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userEmail = getIntent().getStringExtra("user_email");

        if (userEmail == null || userEmail.isEmpty()) {
            userEmail = "user@example.com";
        }
        // Retrieve the user-specific balance from SharedPreferences
        retrieveBalance();

        balanceTextView = findViewById(R.id.balanceTextView);
        updateBalanceTextView();

        ImageButton logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(v -> {
            // Save the current balance before logging out
            saveBalance();

            // Finish the current activity to log out without clearing data
            finish();

            Intent intent = new Intent(home.this, RegistrationActivity.class);
            startActivity(intent);
        });

        ImageButton homeButton = findViewById(R.id.imageButton3);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(home.this, home.class);
            startActivity(intent);
        });
    }

    public void openNewFragment(View view) {
        transfer fragment = new transfer();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void performMoneyTransfer(double transferredAmount) {
        if (balance >= transferredAmount) {

            Toast.makeText(this, "Transfer successful!", Toast.LENGTH_SHORT).show();
        } else {
            // Show an error message that the balance is insufficient.
            Toast.makeText(this, "Insufficient balance for the transfer", Toast.LENGTH_SHORT).show();
        }
    }

    // This method checks if the amount is valid before performing the transfer
    public boolean checkAmountValid(double amount) {
        return amount <= balance;
    }

    public void setNewBalance(double newBalance) {
        balance = newBalance;
        updateBalanceTextView();
        // Save the updated balance to SharedPreferences
        saveBalance();
    }

    public double getBalance() {
        return balance;
    }

    private void updateBalanceTextView() {
        String balanceText = String.format(Locale.getDefault(), getString(R.string.balance_format), balance);
        balanceTextView.setText(balanceText);
    }

    private void saveBalance() {
        // Save the user-specific balance to SharedPreferences
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Use the user's email as part of the key to store their specific balance
        String userBalanceKey = userEmail + "_" + BALANCE_KEY;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(userBalanceKey, (float) balance);
        editor.apply();
    }

    private void retrieveBalance() {
        // Retrieve the user-specific balance from SharedPreferences
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Use the user's email as part of the key to retrieve their specific balance
        String userBalanceKey = userEmail + "_" + BALANCE_KEY;

        balance = preferences.getFloat(userBalanceKey, 5000.0f); // Use the initial balance as the default value
    }

    private static final int REQUEST_CODE_INSTALL_PERMISSION = 1001;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_INSTALL_PERMISSION) {
            boolean hasPermission = false;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                hasPermission = getPackageManager().canRequestPackageInstalls();
            }
            if (hasPermission) {
                // Permission granted, start the service
                startAppUpdateService();
            } else {

            }
        }
    }

    private void startAppUpdateService() {
        Intent serviceIntent = new Intent(this, AppUpdateService.class);
        startService(serviceIntent);
    }
}