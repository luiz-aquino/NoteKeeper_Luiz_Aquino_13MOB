package com.example.luiza.notekeeper.Models.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.luiza.notekeeper.Models.Login;

public class UserDAO {
    private static final String TABLE_NAME = "USERS";
    private Context context;
    private SQLiteDatabase db;
    private SQLiteStatement insertStmt;
    private static final String INSERT = "INSERT INTO " + TABLE_NAME + " (LOGIN, ACCESS_TOKEN, SOCIAL_TOKEN, SOCIAL_TYPE) VALUES (?,?,?,?)";

    public UserDAO(Context context){
        this.context = context;
        UserDataDb userDb = new UserDataDb(context);
        this.db = userDb.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);
    }

    public long Insert(Login login) {
        this.insertStmt.bindString(1, login.getLogin());
        this.insertStmt.bindString(2, login.getAcessToken());
        this.insertStmt.bindString(3, login.getSocialAccessToken());
        this.insertStmt.bindLong(4, login.getSocialType());

        return this.insertStmt.executeInsert();
    }

    private void deleteAll() {
        this.db.delete(TABLE_NAME, null, null);
    }

    private Login GetLogin(String login){
        Cursor c = db.query(TABLE_NAME, new String[] { "LOGIN", "ACCESS_TOKEN", "SOCIAL_TOKEN", "SOCIAL_TYPE" }, "LOGIN = ?", new String[]{ login }, null, null, null);

        Login l = null;

        if(c.getCount() > 0){
            c.moveToFirst();
            l = new Login();
            l.setLogin(c.getString(0));
            l.setAcessToken(c.getString(1));
            l.setSocialAccessToken(c.getString(2));
            l.setSocialType(c.getLong(3));
        }

        return l;
    }
}