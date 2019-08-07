package com.example.safeboda2;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //        Enable use transition from splash to main activity
        setContentView(R.layout.activity_splash);
        Thread moveit = new Thread(){
            @Override
            public void run() {
                try {
//                    Time to load progress bar (3 secs)
                    sleep(3000);
                    Intent movetomain = new Intent(Splash.this,MainActivity.class);
                    startActivity(movetomain);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        moveit.start();
    }
}
