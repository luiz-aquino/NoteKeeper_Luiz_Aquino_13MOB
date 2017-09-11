package com.example.luiza.notekeeper.Models.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.luiza.notekeeper.Models.NoteConfig;

import java.text.SimpleDateFormat;

/**
 * Created by luiza on 2017-09-02.
 */

public class ConfigDao {
    private static final String TABLE_NAME = "CONFIGS";
    private Context context;
    private SQLiteDatabase db;
    private SQLiteStatement insertStmt;
    private SQLiteStatement updateStmt;
    private SQLiteStatement deleteStmt;
    private static final String INSERT = "INSERT INTO " + TABLE_NAME + " (KEY, VALUE, USERNAME, REMEMBER) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE CONFIGS SET VALUE = ?, REMEMBER = ? WHERE KEY = ?";
    private static final String DELETE = "DELETE FROM CONFIGS WHERE KEY = ?";
    private SimpleDateFormat dateFormat;

    public ConfigDao (Context context) {
        this.context = context;
        UserDataDb userDb = new UserDataDb(context);
        db = userDb.getWritableDatabase();
        insertStmt = this.db.compileStatement(INSERT);
        updateStmt = this.db.compileStatement(UPDATE);
        deleteStmt = this.db.compileStatement(DELETE);
    }

    public long insert(NoteConfig config){
        insertStmt.bindString(1, config.getKey());
        insertStmt.bindString(2, config.getValue());
        insertStmt.bindDouble(3, config.isRemember() ? 1 : 0);

        return insertStmt.executeInsert();
    }

    public NoteConfig get(String key){
        Cursor c = db.query(TABLE_NAME, new String[] { "KEY", "VALUE", "USERNAME", "REMEMBER" }, "KEY = ?" , new String[] { key }, null, null, null);

        NoteConfig config = null;

        if(c.getCount() > 0){
            c.moveToFirst();
            config = new NoteConfig();
            config.setKey(c.getString(0));
            config.setValue(c.getString(1));
            config.setRemember(c.getDouble(2) == 1);
        }

        return config;
    }

    public long delete(String key) {

        return  db.delete("CONFIGS", "KEY = ?", new String[] { key });
    }

    public long update(NoteConfig config){
        updateStmt.bindString(0, config.getValue());
        updateStmt.bindDouble(1, config.isRemember() ? 1 : 0);
        updateStmt.bindString(2, config.getKey());

        return updateStmt.executeUpdateDelete();
    }

}
