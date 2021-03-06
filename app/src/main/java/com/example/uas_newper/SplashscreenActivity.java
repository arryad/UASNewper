package com.example.uas_newper;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.uas_newper.user.BeritaActivity;

public class SplashscreenActivity extends AppCompatActivity {
    private ActionBar actionBar;
    int TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        actionBar = getSupportActionBar();
        actionBar.hide();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cekLogin();
                finish();
//                MyPref myPref = new MyPref(SplashscreenActivity.this);
//                if(myPref.getSharedPreferences().getBoolean(MyPref.ISLOGIN,false) == true){
//                    startActivity(new Intent(SplashscreenActivity.this, BeritaActivity.class));
//                    finish();
//                } else {
//                    startActivity(new Intent(SplashscreenActivity.this, LoginActivity.class));
//                    finish();
//                }
            }
        }, TIME_OUT);
    }

    private void cekLogin() {
        MyPref myPref;
        myPref = new MyPref(this);
        if(myPref.getSPISLOGIN()){
            String jenisUser = myPref.getSPLevel();
            if(jenisUser.equals("admin")){
                startActivity(new Intent(SplashscreenActivity.this, com.example.uas_newper.admin.BeritaActivity.class));
                finish();
            }else if(jenisUser.equals("user")){
                startActivity(new Intent(SplashscreenActivity.this, BeritaActivity.class));
                finish();
            }
        }else{
            startActivity(new Intent(SplashscreenActivity.this, LoginActivity.class));
            finish();
        }
    }
}
