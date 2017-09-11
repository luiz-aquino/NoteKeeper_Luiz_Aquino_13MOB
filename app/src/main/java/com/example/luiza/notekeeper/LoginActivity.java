package com.example.luiza.notekeeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luiza.notekeeper.Models.Database.ConfigDao;
import com.example.luiza.notekeeper.Models.Database.UserDAO;
import com.example.luiza.notekeeper.Models.Login;
import com.example.luiza.notekeeper.Models.NoteConfig;
import com.example.luiza.notekeeper.Models.SocialTypes;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    private UserDAO userDAO;
    private ConfigDao configDao;
    private EditText etUserName;
    private EditText etPassword;
    private CheckBox ckbRemember;
    private String accessToken;

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = (EditText) findViewById(R.id.login_username);
        etPassword = (EditText) findViewById(R.id.login_password);
        ckbRemember = (CheckBox) findViewById(R.id.login_rememberme);

        callbackManager = CallbackManager.Factory.create();
        final LoginButton loginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, getString(R.string.facebook_login_success), Toast.LENGTH_SHORT).show();
                Profile p = Profile.getCurrentProfile();
                accessToken = loginResult.getAccessToken().getToken();
                if(p == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            if(currentProfile == null) return;
                            Log.v("Facebook - profile", currentProfile.getFirstName());
                            LoginActivity.this.loggedInFacebook(accessToken, currentProfile);
                        }
                    };
                }
                else {
                    LoginActivity.this.loggedInFacebook(accessToken, p);
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, getString(R.string.login_facebook_canceled), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, getString(R.string.facebook_login_failed), Toast.LENGTH_SHORT).show();
                Log.e(getString(R.string.fabook_login_error_system_title), getString(R.string.facebook_login_error_system_msg), error);
            }
        });

        userDAO = new UserDAO(this);
        configDao = new ConfigDao(this);
     }

     private void loggedInFacebook(String accessToken, Profile profile){
         NoteConfig config = configDao.get("LOGGEDUSER");
         if(config != null){
             configDao.delete(config.getKey());
         }

         config = new NoteConfig("LOGGEDUSER", profile.getId(), profile.getId(), ckbRemember.isChecked());
         configDao.insert(config);

         if(userDAO.getLogin(profile.getId()) == null){
             Login l = new Login();
             l.setLogin(profile.getId());
             l.setSocialAccessToken(accessToken);
             l.setSocialType(SocialTypes.FACEBOOK);
             userDAO.insert(l);
         }

         Intent intent = new Intent(LoginActivity.this, MainActivity.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
         startActivity(intent);
         LoginActivity.this.finish();
     }

    public Login getLogin() {
        String username = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        if(username == null || username.isEmpty()){
            Toast.makeText(this, getString(R.string.login_username_error), Toast.LENGTH_SHORT).show();
            return null;
        }

        if(password == null || password.isEmpty()){
            Toast.makeText(this, getString(R.string.login_password_error), Toast.LENGTH_SHORT).show();
            return null;
        }

        Login l = new Login(username, password);
        return l;
    }

    public void signin(View view){
        Login l = getLogin();

        if(l == null) return;

        if(userDAO.validateLogin(l.getLogin(), l.getPassword())){
            NoteConfig config = new NoteConfig("LOGGEDUSER", l.getLogin(), l.getLogin(), ckbRemember.isChecked());

            configDao.insert(config);
            Toast.makeText(this, getString(R.string.login_successfull), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            LoginActivity.this.finish();
        }
        else {
            Toast.makeText(this, getString(R.string.login_invalid_signin), Toast.LENGTH_LONG).show();
        }
    }

    public void signup(View view) {
        Login l = getLogin();

        Login dbLogin = userDAO.getLogin(l.getLogin());

        if(dbLogin != null){
            Toast.makeText(this, getString(R.string.login_duplicate_user), Toast.LENGTH_LONG).show();
            return;
        }

        if(userDAO.insert(l) > 0) {
            Toast.makeText(this, getString(R.string.login_create_success), Toast.LENGTH_LONG).show();
            signin(view);
        }
        else {
            Toast.makeText(this, getString(R.string.login_create_error_generic), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
