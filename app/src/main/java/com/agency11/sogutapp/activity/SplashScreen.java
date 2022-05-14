package com.agency11.sogutapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.agency11.sogutapp.method.LocaleHelper;
import com.agency11.sogutapp.R;
import com.agency11.sogutapp.method.Size;

import java.util.Locale;

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

        SharedPreferences sharedPreferences2 = getSharedPreferences("lang1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences2.edit();
        editor.putString("language", Locale.getDefault().getLanguage());
        editor.apply();
        String targetLanguage = sharedPreferences2.getString("language","");
        LocaleHelper.setLocale(this,targetLanguage);

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String status = sharedPreferences.getString("status",Locale.getDefault().getLanguage());
        //SharedPreferences sharedPreferences2 = getSharedPreferences("lang", Context.MODE_PRIVATE);
        //LocaleHelper.setLocale(this,targetLanguage);

        if (targetLanguage.equals("")){
            editor.putString("language", Locale.getDefault().getLanguage());
            editor.apply();
            LocaleHelper.setLocale(this,Locale.getDefault().getLanguage());
           // Toast.makeText(this, "daha once sifre alinmadi", Toast.LENGTH_SHORT).show();
        } else {
            String targetLanguage2 = sharedPreferences2.getString("language","");
            LocaleHelper.setLocale(this,targetLanguage2);
           // Toast.makeText(this, "daha once sifre alindi", Toast.LENGTH_SHORT).show();
        }

        //LocaleHelper.setLocale(this,targetLanguage);

        Thread splashThread = new Thread() {

            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(3000);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
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