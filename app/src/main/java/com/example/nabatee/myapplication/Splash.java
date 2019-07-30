package com.example.nabatee.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Splash extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    public SharedPreferences loginPref;


    public String phon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        loginPref=getSharedPreferences("Login", Context.MODE_PRIVATE);
          phon=loginPref.getString("data","Empty");
        /* New Handler to start the Menu-Activity
         * and close this splash-Screen after some seconds.*/

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                if (phon=="Empty") {
                    Intent mainIntent = new Intent(Splash.this, Auth.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
                else {
                    Intent mainIntent1 = new Intent(Splash.this, ViewAds.class);
                    Splash.this.startActivity(mainIntent1);
                    Splash.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);


    }
}