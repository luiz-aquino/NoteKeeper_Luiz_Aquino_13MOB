package com.example.luiza.notekeeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.luiza.notekeeper.Models.Login;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 4500;
    private SharedPreferences preferences;
    private boolean logedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        logedIn = false;

        readPerferences();
        playSound();
        load();
    }

    private void readPerferences(){
        String username = preferences.getString("USERNAME", "");
        boolean rememberMe = preferences.getBoolean("REMEMBER", false);
        if(!username.isEmpty()){
            if(!rememberMe) {
                SharedPreferences.Editor e = preferences.edit();
                e.putString("USERNAME", "");
            }
            else {
                logedIn = true;
            }
        }
    }

    private void playSound() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MediaPlayer player = new MediaPlayer().create(SplashActivity.this, R.raw.notepat_eraser);
                player.start();
            }
        }, 1500);
    }

    private void load() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.splash);
        anim.reset();

        ImageView iv = (ImageView) findViewById(R.id.splash);
        if(iv != null){
            iv.clearAnimation();
            iv.startAnimation(anim);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(logedIn){
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
