package com.example.newversion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton logoutButton = findViewById(R.id.logout);

        logoutButton.setOnClickListener(v -> {
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
        // Replace 'YourCurrentFragment' with the fragment you want to replace
        transfer fragment = new transfer();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment); // R.id.fragmentContainer is the ID of the container where you want to replace the fragment
        transaction.addToBackStack(null);
        transaction.commit();
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
                // Handle the case where the user denied the installation permission
                // or the permission was not granted
            }
        }
    }

    private void startAppUpdateService() {
        Intent serviceIntent = new Intent(this, AppUpdateService.class);
        startService(serviceIntent);
    }
}
