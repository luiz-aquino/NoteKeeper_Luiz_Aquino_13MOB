package com.example.luiza.notekeeper.Models;

import java.util.Date;

/**
 * Created by Luiz Aquino on 2017-08-20.
 */

public class Note {
    private long Id;
    private String Note;
    private String Groups;
    private Date CreateDate;
    private boolean Sent;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getGroups() {
        return Groups;
    }

    public void setGroups(String groups) {
        Groups = groups;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public boolean isSent() {
        return Sent;
    }

    public void setSent(boolean sent) {
        Sent = sent;
    }
}

