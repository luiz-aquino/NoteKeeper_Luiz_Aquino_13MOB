package com.example.luiza.notekeeper.Models.Services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by luiza on 2017-09-15.
 */

public class MeuFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FireBase", "Refreshed token: " + refreshedToken);
        this.sendRegistrationToServer(refreshedToken);
        super.onTokenRefresh();
    }

    private void sendRegistrationToServer(String token){

    }
}
