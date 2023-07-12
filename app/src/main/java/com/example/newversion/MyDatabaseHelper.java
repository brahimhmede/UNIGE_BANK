package com.example.newversion;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the "Users" table
        String createTableQuery = "CREATE TABLE Users (LastName TEXT, Name TEXT, IBAN TEXT);";
        db.execSQL(createTableQuery);

        // Insert the first row
        ContentValues values1 = new ContentValues();
        values1.put("LastName", "Hamade");
        values1.put("Name", "Ibrahim");
        values1.put("IBAN", "IT123");
        db.insert("Users", null, values1);

        // Insert the second row
        ContentValues values2 = new ContentValues();
        values2.put("LastName", "Kola");
        values2.put("Name", "Era");
        values2.put("IBAN", "IT321");
        db.insert("Users", null, values2);

        ContentValues values3 = new ContentValues();
        values3.put("LastName", "Ayoub");
        values3.put("Name", "Ali");
        values3.put("IBAN", "IT33");
        db.insert("Users", null, values3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades when the version number is increased
        // Example: db.execSQL("DROP TABLE IF EXISTS Users;");
        //          onCreate(db);
    }
}
