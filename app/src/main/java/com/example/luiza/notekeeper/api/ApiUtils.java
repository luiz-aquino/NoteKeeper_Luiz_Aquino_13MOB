package com.example.luiza.notekeeper.api;

import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Retrofit;

/**
 * Created by luiza on 2017-09-02.
 */

public class ApiUtils {
    public static final String BASE_URL = "http://www.mocky.io/v2/58b9b1740f0000b614f09d2f";
    private static URL URL_BASE;


    public static IUserBaseAPI getUserApi(){
        return RetrofitClient.getClient(BASE_URL)
                .create(IUserBaseAPI.class);
    }

    public static String getResourceFullUrl(String resource) {
        try {
            if(URL_BASE == null){
                URL_BASE = new URL(BASE_URL);
            }
            URL merged = new URL(URL_BASE, resource);

            return merged.toString();

        }catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
