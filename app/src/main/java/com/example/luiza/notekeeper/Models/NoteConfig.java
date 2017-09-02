package com.example.luiza.notekeeper.Models;

/**
 * Created by luiza on 2017-09-02.
 */

public class NoteConfig {

    public NoteConfig(){

    }

    public NoteConfig(String key, String value, String username, boolean remember){
        this.Key = key;
        this.Value = value;
        this.UserName = username;
        this.Remember = remember;
    }

    private String Key;
    private String Value;
    private String UserName;
    private boolean Remember;

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public boolean isRemember() {
        return Remember;
    }

    public void setRemember(boolean remember) {
        Remember = remember;
    }
}
