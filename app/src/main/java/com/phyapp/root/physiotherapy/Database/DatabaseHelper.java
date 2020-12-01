package com.phyapp.root.physiotherapy.Database;

import android.database.sqlite.SQLiteOpenHelper;

//public class DatabaseHelper {
    import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;


    /**
     * Created by ravi on 15/03/18.
     */

    public class DatabaseHelper extends SQLiteOpenHelper {

        // Database Version
        private static final int DATABASE_VERSION = 1;

        // Database Name
        private static final String DATABASE_NAME = "notes_db";


        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Creating Tables
        @Override
        public void onCreate(SQLiteDatabase db) {

            // create notes table
         //   db.execSQL(CREATE_TABLE);
        }

        // Upgrading database
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
          //  db.execSQL("DROP TABLE IF EXISTS " +);

            // Create tables again
            onCreate(db);
        }
    }

