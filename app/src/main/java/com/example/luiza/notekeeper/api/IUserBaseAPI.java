package com.example.luiza.notekeeper.api;

import android.database.Observable;

import com.example.luiza.notekeeper.Models.ApiUser;


import java.util.List;
import java.util.Observer;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by luiza on 2017-09-02.
 */

public interface IUserBaseAPI {
    @GET("/v2/58b9b1740f0000b614f09d2f/")
    Call<ApiUser> GetUser();
}
