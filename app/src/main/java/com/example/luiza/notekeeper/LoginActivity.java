package com.example.luiza.notekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.luiza.notekeeper.Models.Database.UserDAO;

public class LoginActivity extends AppCompatActivity {

    private UserDAO userDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDAO = new UserDAO(this);
    }
}
