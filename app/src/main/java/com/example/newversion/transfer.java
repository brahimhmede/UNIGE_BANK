package com.example.newversion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class transfer extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public transfer() {
        // Required empty public constructor
    }

    public static transfer newInstance(String param1, String param2) {
        transfer fragment = new transfer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);

        // Find the "Send" button
        Button sendButton = view.findViewById(R.id.buttonSend);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextLastName = view.findViewById(R.id.editTextLastName);
                EditText editTextName = view.findViewById(R.id.editTextFullName);
                EditText editTextIBAN = view.findViewById(R.id.editTextIBAN);
                EditText editTextAmount = view.findViewById(R.id.editTextAmount);

                // Validate input fields
                String lastName = editTextLastName.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String iban = editTextIBAN.getText().toString().trim();
                String amountString = editTextAmount.getText().toString().trim();

                if (lastName.isEmpty() || name.isEmpty() || iban.isEmpty() || amountString.isEmpty()) {
                    // Show error message if any field is empty
                    Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the data exists in the database
                    boolean dataExists = checkDataExists(lastName, name, iban);

                    if (dataExists) {
                        // Perform the transfer
                        double amount = Double.parseDouble(amountString);

                        // Redirect to PaymentSuccess activity
                        Intent intent = new Intent(getActivity(), PaymentSuccess.class);
                        startActivity(intent);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Return to home screen after 3 seconds
                                Intent homeIntent = new Intent(getActivity(), home.class);
                                startActivity(homeIntent);
                                getActivity().finish();
                            }
                        }, 3000);
                    } else {
                        // Show error message if data does not exist
                        Toast.makeText(getActivity(), "Input data not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        return view;
    }

    private boolean checkDataExists(String lastName, String Name, String iban) {
        // Query the database to check if the data exists
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Customize the query based on your database schema
        String selection = "LastName = ? AND Name = ? AND IBAN = ?";
        String[] selectionArgs = {lastName, Name, iban};

        Cursor cursor = db.query("Users", null, selection, selectionArgs, null, null, null);
        boolean dataExists = cursor.moveToFirst();
        cursor.close();
        db.close();

        return dataExists;
    }
}
