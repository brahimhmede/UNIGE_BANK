package com.example.newversion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class transfer extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private double balance; // Variable to hold the current balance

    public transfer() {
        // Required empty public constructor
    }

    public static transfer newInstance(String param1, String param2, double balance) {
        transfer fragment = new transfer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putDouble("balance", balance);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);

        // Retrieve the balance from the home activity
        home homeActivity = (home) getActivity();
        if (homeActivity != null) {
            balance = homeActivity.getBalance();
        }
        // Find the "Send" button
        Button sendButton = view.findViewById(R.id.buttonSend);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextLastName = view.findViewById(R.id.editTextLastName);
                EditText editTextFullName = view.findViewById(R.id.editTextFullName);
                EditText editTextIBAN = view.findViewById(R.id.editTextIBAN);
                EditText editTextAmount = view.findViewById(R.id.editTextAmount);

                // Validate input fields
                String lastName = editTextLastName.getText().toString().trim();
                String fullName = editTextFullName.getText().toString().trim();
                String iban = editTextIBAN.getText().toString().trim();
                String amountString = editTextAmount.getText().toString().trim();

                if (lastName.isEmpty() || fullName.isEmpty() || iban.isEmpty() || amountString.isEmpty()) {
                    // Show error message if any field is empty
                    Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the data exists in the database
                    boolean dataExists = checkDataExists(lastName, fullName, iban);

                    if (dataExists) {
                        // Perform the transfer
                        double amount = Double.parseDouble(amountString);
                        if (amount > balance) {
                            Toast.makeText(getActivity(), "Insufficient balance", Toast.LENGTH_SHORT).show();
                        } else {
                            // Deduct the transferred amount from the balance
                            balance -= amount;

                            // Update the balance in the home activity
                            if (getActivity() instanceof home) {
                                Objects.requireNonNull(homeActivity).updateBalance(balance);
                            }

                            // Redirect to PaymentSuccess activity
                            Intent intent = new Intent(getActivity(), PaymentSuccess.class);
                            startActivity(intent);
                        }
                    } else {
                        // Show error message if data does not exist
                        Toast.makeText(getActivity(), "Input data not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    private boolean checkDataExists(String lastName, String fullName, String iban) {
        // Query the database to check if the data exists
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = "LastName = ? AND Name = ? AND IBAN = ?";
        String[] selectionArgs = {lastName, fullName, iban};

        Cursor cursor = db.query("Users", null, selection, selectionArgs, null, null, null);
        boolean dataExists = cursor.moveToFirst();
        cursor.close();
        db.close();

        return dataExists;
    }
}
