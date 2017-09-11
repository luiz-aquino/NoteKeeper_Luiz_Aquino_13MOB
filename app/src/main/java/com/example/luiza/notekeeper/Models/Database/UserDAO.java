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
    private static final String INSERT = "INSERT INTO " + TABLE_NAME + " (LOGIN, PASSWORD, ACCESS_TOKEN, SOCIAL_TOKEN, SOCIAL_TYPE) VALUES (?, ?,?,?,?)";

    public UserDAO(Context context){
        this.context = context;
        UserDataDb userDb = new UserDataDb(context);
        db = userDb.getWritableDatabase();
        insertStmt = this.db.compileStatement(INSERT);
    }

    public long insert(Login login) {
        insertStmt.bindString(1, login.getLogin());
        if(login.getPassword() == null)
        {
            insertStmt.bindNull(2);
        }
        else {
            insertStmt.bindString(2, login.getPassword());
        }

        if(login.getAcessToken() != null) {
            insertStmt.bindString(3, login.getAcessToken());
        }
        else {
            insertStmt.bindNull(3);
        }
        if(login.getSocialAccessToken() != null) {
            insertStmt.bindString(4, login.getSocialAccessToken());
        }
        else {
            insertStmt.bindNull(4);
        }
        insertStmt.bindLong(5, login.getSocialType());

        return this.insertStmt.executeInsert();
    }

    public void deleteAll() {
        this.db.delete(TABLE_NAME, null, null);
    }

    public Login getLogin(String login){
        Cursor c = db.query(TABLE_NAME, new String[] { "LOGIN", "PASSWORD","ACCESS_TOKEN", "SOCIAL_TOKEN", "SOCIAL_TYPE" }, "LOGIN = ?", new String[]{ login }, null, null, null);

        Login l = null;

        if(c.getCount() > 0){
            c.moveToFirst();
            l = new Login();
            l.setLogin(c.getString(0));
            l.setPassword(c.getString(1));
            l.setAcessToken(c.getString(2));
            l.setSocialAccessToken(c.getString(3));
            l.setSocialType(c.getLong(4));
        }

        return l;
    }

    public boolean validateLogin(String login, String password){
        Login l = getLogin(login);

        if(l == null || (l.getSocialAccessToken() != null && !l.getSocialAccessToken().isEmpty())){
            return false;
        }

        return l.getPassword().equals(password);
    }
}