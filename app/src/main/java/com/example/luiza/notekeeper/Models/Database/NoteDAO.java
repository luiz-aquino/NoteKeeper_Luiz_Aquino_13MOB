package com.example.luiza.notekeeper.Models.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.luiza.notekeeper.Models.Note;

public class NoteDAO {
    private static final String TABLE_NAME = "NOTES";
    private static final String[] TABLE_FIELDS = new String[] { "ID", "NOTE", "GROUPS", "DATE", "SENT", "SCHEDULED", "SCHEDULED_DATE" };
    private Context context;
    private SQLiteDatabase db;
    private SQLiteStatement insertStmt;
    private static final String INSERT = "INSERT INTO " + TABLE_NAME + " (NOTE,GROUPS,DATE,SENT) VALUES (?,?,?,?)";
    private SimpleDateFormat dateFormater;

    public NoteDAO(Context context){
        this.context = context;
        UserDataDb userDb = new UserDataDb(context);
        db = userDb.getWritableDatabase();
        insertStmt = this.db.compileStatement(INSERT);
        dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public long Insert(Note note){
        insertStmt.bindString(1, note.getNote());
        insertStmt.bindString(2, note.getGroups());
        insertStmt.bindString(3, dateFormater.format(note.getCreateDate()));
        insertStmt.bindLong(4, note.isSent() ? 1 : 0);
        insertStmt.bindLong(5, note.isScheduled() ? 1 : 0);
        insertStmt.bindString(6, dateFormater.format(note.getScheduledDate()));

        note.setScheduledDate(null);

        return insertStmt.executeInsert();
    }

    public void deleteAll() { db.delete(TABLE_NAME, null, null); }

    public Note getNote(long idNote){
        Cursor c = db.query(TABLE_NAME, TABLE_FIELDS, "ID = ?", new String[] { String.valueOf(idNote) }, null, null, null );
        Note n = null;

        if(c.moveToFirst()){
            n = mapCursorToNote(c);
        }

        return n;
    }

    public List<Note> getAllNotes() {
        Cursor c = db.query(TABLE_NAME, TABLE_FIELDS, null, null, null, null, "DATE DESC");
        return mapCursorToList(c);
    }

    public List<Note> getByText(String text){
        Cursor c = db.query(TABLE_NAME, TABLE_FIELDS, "LIKE '%?%'", new String[] { text }, null, null, "DATE DESC");
        return mapCursorToList(c);
    }

    private List<Note> mapCursorToList(Cursor c){
        List<Note> notes = new ArrayList<Note>();

        if(c.getCount() > 0 && c.moveToFirst()){
            do {
                notes.add(mapCursorToNote(c));
            }while (c.moveToNext());
        }

        return notes;
    }

    private Note mapCursorToNote(Cursor c) {
        Note note = new Note();

        note.setId(c.getLong(0));
        note.setNote(c.getString(1));
        note.setGroups(c.getString(2));
        try {
            note.setCreateDate(dateFormater.parse(c.getString(3)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        note.setSent(c.getLong(4) == 1);
        note.setScheduled(c.getLong(5) == 1);
        try {
            note.setScheduledDate(dateFormater.parse(c.getString(6)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return note;
    }
}
