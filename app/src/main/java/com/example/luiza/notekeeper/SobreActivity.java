package com.example.luiza.notekeeper;

import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SobreActivity extends AppCompatActivity {

    private TextView tvAppVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        tvAppVersion = (TextView) findViewById(R.id.tvAppVersion);
        try {
            tvAppVersion.setText(getString(R.string.app_version_text) + this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void close(View view){
        finish();
    }
}
