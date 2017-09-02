package com.example.luiza.notekeeper.Models.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDataDb extends SQLiteOpenHelper {

    private static final String DBNAME = "noteKeeper.db";
    private static final int DBVERSION = 4;
    public UserDataDb(Context context){
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String sqlDbUsers = "CREATE TABLE USERS ( LOGIN TEXT PRIMARY KEY, PASSWORD TEXT, ACCESS_TOKEN TEXT, SOCIAL_TOKEN TEXT, SOCIAL_TYPE INTEGER )";
        final String sqlDbNotes = "CREATE TABLE NOTES ( ID INTEGER PRIMARY KEY, NOTE TEXT, GROUPS TEXT, DATE TEXT, SENT INTEGER, SCHEDULED INT, SCHEDULED_DATE TEXT )";
        final String sqlDbConfigs = "CREATE TABLE CONFIGS ( KEY TEXT PRIMARY KEY, VALUE TEXT, USERNAME TEXT, REMEMBER NUMBER)";
        db.execSQL(sqlDbUsers);
        db.execSQL(sqlDbNotes);
        db.execSQL(sqlDbConfigs);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USERS");
        db.execSQL("DROP TABLE IF EXISTS NOTES");
        db.execSQL("DROP TABLE IF EXISTS CONFIGS");
        onCreate(db);
    }
}
