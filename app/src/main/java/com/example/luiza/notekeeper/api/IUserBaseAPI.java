package com.example.luiza.notekeeper.api;

import android.database.Observable;

import com.example.luiza.notekeeper.Models.ApiUser;


import java.util.List;
import java.util.Observer;

import retrofit2.http.GET;

/**
 * Created by luiza on 2017-09-02.
 */

public interface IUserBaseAPI {
    @GET("/")
    Observable<List<ApiUser>> GetAll();
}
