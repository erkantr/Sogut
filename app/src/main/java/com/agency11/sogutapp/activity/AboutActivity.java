package com.agency11.sogutapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agency11.sogutapp.R;
import com.agency11.sogutapp.Size;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Size size = new Size(this);

        ImageView image_about = findViewById(R.id.image_about);
        TextView text_about = findViewById(R.id.text_about);
        CardView cardview_about = findViewById(R.id.cardview_about);
        TextView text_about1 = findViewById(R.id.text_about1);
        TextView text_about2 = findViewById(R.id.text_about2);
        TextView text_about3 = findViewById(R.id.text_about3);
        TextView text_about4 = findViewById(R.id.text_about4);
        LinearLayout linear_about = findViewById(R.id.linear_about);
        ImageView image_about1 = findViewById(R.id.image_about1);
        ImageView image_about2 = findViewById(R.id.image_about2);
        ImageView image_about3 = findViewById(R.id.image_about3);
        CardView cardview_about1 = findViewById(R.id.cardview_about1);
        TextView text_about5 = findViewById(R.id.text_about5);
        TextView text_about6 = findViewById(R.id.text_about6);
        TextView text_about7 = findViewById(R.id.text_about7);
        TextView text_about8 = findViewById(R.id.text_about8);
        LinearLayout linear_about1 = findViewById(R.id.linear_about1);
        ImageView image_about4 = findViewById(R.id.image_about4);
        ImageView image_about5 = findViewById(R.id.image_about5);
        ImageView image_about6 = findViewById(R.id.image_about6);

        size.setMargin(image_about,23,19,0,0);
        size.setMargin(text_about,40,18,0,0);
        size.setSize(text_about,15);
        size.setWidth(cardview_about,343);
        size.setHeight(cardview_about,453);
        size.setMargin(cardview_about,16,15,0,0);
        size.setMargin(text_about1,24,24,0,0);
        size.setSize(text_about1,19);
        size.setMargin(text_about2,24,6,0,0);
        size.setSize(text_about2,14);
        size.setMargin(text_about3,24,6,24,0);
        size.setSize(text_about3,12);
        size.setMargin(text_about4,0,60,0,0);
        size.setSize(text_about4,10);
        size.setMargin(linear_about,0,16,0,0);
        size.setMargin(image_about1,24,0,0,0);
        size.setMargin(image_about2,24,0,0,0);
        size.setMargin(image_about3,24,0,0,0);
        size.setWidth(cardview_about1,343);
        size.setHeight(cardview_about1,453);
        size.setMargin(cardview_about1,8,15,16,0);
        size.setMargin(text_about5,24,24,0,0);
        size.setSize(text_about5,19);
        size.setMargin(text_about6,24,6,0,0);
        size.setSize(text_about6,14);
        size.setMargin(text_about7,24,6,24,0);
        size.setSize(text_about7,12);
        size.setMargin(text_about8,0,60,0,0);
        size.setSize(text_about8,10);
        size.setMargin(linear_about1,0,16,0,0);
        size.setMargin(image_about4,24,0,0,0);
        size.setMargin(image_about5,24,0,0,0);
        size.setMargin(image_about6,24,0,0,0);


    }
}