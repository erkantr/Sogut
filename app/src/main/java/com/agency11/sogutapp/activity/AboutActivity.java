package com.agency11.sogutapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.agency11.sogutapp.method.LocaleHelper;
import com.agency11.sogutapp.R;
import com.agency11.sogutapp.method.Size;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Size size = new Size(this);
        SharedPreferences sharedPreferences2 = getSharedPreferences("lang1", Context.MODE_PRIVATE);
        String targetLanguage = sharedPreferences2.getString("language","");
        LocaleHelper.setLocale(this,targetLanguage);

        LinearLayout back = findViewById(R.id.back_layout);

        back.setOnClickListener(view -> {
            NavUtils.navigateUpFromSameTask(AboutActivity.this);

        });

    }
}