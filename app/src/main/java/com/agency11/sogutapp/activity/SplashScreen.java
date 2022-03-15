package com.agency11.sogutapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.agency11.sogutapp.R;
import com.agency11.sogutapp.Size;

public class SplashScreen extends AppCompatActivity {

    Thread splashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ImageView splashImageView = findViewById(R.id.splashImageView);

        Size size = new Size(this);

        size.setWidth(splashImageView,300);
        size.setHeight(splashImageView,300);

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String status = sharedPreferences.getString("status","");

        splashThread = new Thread() {

            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(3000);
                    }
                } catch (InterruptedException ex) {

                } finally {

                    if (status.equals("1")){
                        startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    } else {
                        startActivity(new Intent(SplashScreen.this, OnboardingActivity.class));
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("status", "1");
                        editor.apply();
                    }
                    finish();
                }

            }
        };
        splashThread.start();

    }
}