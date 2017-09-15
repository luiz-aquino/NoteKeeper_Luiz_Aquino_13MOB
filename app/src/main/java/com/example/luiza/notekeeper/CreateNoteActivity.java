package com.example.luiza.notekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luiza.notekeeper.Models.Database.NoteDAO;
import com.example.luiza.notekeeper.Models.Database.UserDAO;
import com.example.luiza.notekeeper.Models.Note;

import java.util.Calendar;

public class CreateNoteActivity extends AppCompatActivity {

    private NoteDAO noteDao;
    private EditText edtNote;
    private Note note;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        note = null;

        edtNote = (EditText) findViewById(R.id.edtNewNote);

        noteDao = new NoteDAO(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            long id = extras.getLong("NOTEID");
            if(id != 0) {
                note = noteDao.getNote(id);
                edtNote.setText(note.getNote());
            }

            username = extras.getString("USERNAME", null);
        }
    }

    public void create(View view){
        String noteText = edtNote.getText().toString();

        if(noteText.isEmpty()) {
            Toast.makeText(this, getString(R.string.create_note_empt_text_error), Toast.LENGTH_LONG).show();
            return;
        }

        if(this.note == null) {
            Note note = new Note();
            note.setNote(noteText);
            note.setGroups(username);

            note.setCreateDate(Calendar.getInstance().getTime());

            if (noteDao.insert(note) > 0) {
                Toast.makeText(this, "Note created successfuly!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Error creating note", Toast.LENGTH_LONG).show();
            }
        }
        else {
            this.note.setNote(noteText);

            if(noteDao.update(note) > 0){
                Toast.makeText(this, getString(R.string.note_success_updating), Toast.LENGTH_LONG).show();
                finish();
            }
            else {
                Toast.makeText(this, getString(R.string.note_error_updating), Toast.LENGTH_LONG).show();
            }
        }

    }

    public void cancel(View view){
        finish();
    }
}
