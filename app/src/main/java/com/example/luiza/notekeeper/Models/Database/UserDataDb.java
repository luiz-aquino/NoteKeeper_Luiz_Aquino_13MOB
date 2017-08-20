package com.example.luiza.notekeeper.Models.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDataDb extends SQLiteOpenHelper {

    private static final String DBNAME = "noteKeeper.db";
    private static final int DBVERSION = 2;
    public UserDataDb(Context context){
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String sqlDbUsers = "CREATE TABLE USERS ( LOGIN TEXT PRIMARY KEY, ACCESS_TOKEN TEXT, SOCIAL_TOKEN TEXT, SOCIAL_TYPE INTEGER)";
        final String sqlDbNotes = "CREATE TABLE NOTES ( ID INTEGER AUTOINCREMENT PRIMARY KEY, NOTE TEXT, GROUPS TEXT, DATE TEXT, SENT INTEGER )";
        db.execSQL(sqlDbUsers);
        db.execSQL(sqlDbNotes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USERS");
        db.execSQL("DROP TABLE IF EXISTS NOTES");
        onCreate(db);
    }
}
