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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        edtNote = (EditText) findViewById(R.id.edtNewNote);

        noteDao = new NoteDAO(this);
    }

    public void Create(View view){
        String noteText = edtNote.getText().toString();

        if(noteText.isEmpty()) {
            Toast.makeText(this, getString(R.string.create_note_empt_text_error), Toast.LENGTH_LONG).show();
            return;
        }

        Note note = new Note();
        note.setNote(noteText);
        note.setCreateDate(Calendar.getInstance().getTime());


        noteDao.deleteAll();
    }
}
