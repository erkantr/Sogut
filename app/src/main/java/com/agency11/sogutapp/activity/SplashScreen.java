package com.agency11.sogutapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        splashThread = new Thread() {

            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(3000);
                    }
                } catch (InterruptedException ex) {

                } finally {
                    startActivity(new Intent(SplashScreen.this, OnboardingActivity.class));
                    finish();
                }

            }
        };
        splashThread.start();

    }
}