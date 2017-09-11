package com.example.luiza.notekeeper.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luiza on 2017-09-02.
 */

public class ApiUser {
    @SerializedName("usuario")
    private String User;
    @SerializedName("senha")
    private String Password;

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
