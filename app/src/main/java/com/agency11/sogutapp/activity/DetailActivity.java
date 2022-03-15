package com.agency11.sogutapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agency11.sogutapp.BottomDialog;
import com.agency11.sogutapp.map.MapsActivity;
import com.agency11.sogutapp.R;
import com.agency11.sogutapp.Translator;
import com.agency11.sogutapp.adapter.ImageListAdapter;
import com.agency11.sogutapp.adapter.VideoAdapter;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity implements VideoAdapter.AddLifecycleCallbackListener {

    FirebaseFirestore firebaseFirestore;
    SharedPreferences sharedPreferences;
    String new_id;
    ImageListAdapter imageListAdapter;
    ArrayList<String> list;
    ArrayList<String> videoList;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    VideoAdapter videoAdapter;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        RelativeLayout kaydet = findViewById(R.id.kaydet_button);
        ImageView kaydet_image = findViewById(R.id.kaydet_button_image);

        TextView pagetitle = findViewById(R.id.page_title);

        RelativeLayout map_button = findViewById(R.id.map_button);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        Button time_button = findViewById(R.id.time_button);
        TextView konum = findViewById(R.id.konum);
        RelativeLayout call = findViewById(R.id.call_button);
        RelativeLayout bottom = findViewById(R.id.bottom);
        TextView adres_title = findViewById(R.id.adres_title);
        LinearLayout adres_layout = findViewById(R.id.adres_layout);
        TextView saat_title = findViewById(R.id.saat_title);
        LinearLayout saat_layout = findViewById(R.id.saat_layout);

        TextView baslik = findViewById(R.id.baslik);
        TextView detay_text = findViewById(R.id.detay);

        TextView sibs_title_text = findViewById(R.id.sibs_title_text);
        TextView sibs_text = findViewById(R.id.sibs_text);
        LinearLayout sibs_layout = findViewById(R.id.sibs_layout);
        LinearLayout children_layout = findViewById(R.id.children_layout);
        TextView children_title_text = findViewById(R.id.children_title_text);
        TextView children_text = findViewById(R.id.children_text);


        Translator translator = new Translator();
        sharedPreferences = getSharedPreferences("id", Context.MODE_PRIVATE);
        String targetLanguage = Locale.getDefault().getLanguage();

        Intent intent = getIntent();
        String list = intent.getStringExtra("list");
        switch (list) {
            case "kaydedilenler":
                sibs_title_text.setVisibility(View.GONE);
                sibs_layout.setVisibility(View.GONE);
                children_layout.setVisibility(View.GONE);
                children_title_text.setVisibility(View.GONE);
                String name = intent.getStringExtra("name");
                String detay = intent.getStringExtra("exp");
                String numara = intent.getStringExtra("phone");
                String videoId = intent.getStringExtra("videoId");
                id = intent.getStringExtra("id");
                ArrayList<String> images = intent.getStringArrayListExtra("imageUrl");
                ArrayList<String> times = intent.getStringArrayListExtra("times");
                ArrayList<String> location = intent.getStringArrayListExtra("location");

                new_id = sharedPreferences.getString(id, "");

                translator.translate(baslik,name,targetLanguage);
                translator.translate(detay_text,detay,targetLanguage);

                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                Date d = new Date();
                String dayOfTheWeek = sdf.format(d);


                konum.setText(location.get(0));

                for (int i=0; i < times.size(); i++){
                    String time = times.get(i).toLowerCase();
                    if (time.matches("her zaman açık")){
                        //holder.time.setBackgroundResource(R.drawable.ic_ellipse_1);
                        time_button.setBackgroundResource(R.drawable.time_background);
                        time_button.setTextColor(getResources().getColor(R.color.primary_green));
                        time_button.setText("Her zaman açık");

                    } else if (time.matches("kapalı") && time.matches(dayOfTheWeek)){
                        time_button.setBackgroundResource(R.drawable.clan);
                        time_button.setTextColor(getResources().getColor(R.color.yellow_variant));
                        time_button.setText("Kapalı");
                    }  else {
                        time_button.setBackgroundResource(R.drawable.time_background);
                        time_button.setTextColor(getResources().getColor(R.color.primary_green));
                        time_button.setText("Şu an açık");
                    }
                }

                map_button.setOnClickListener(view -> {
                    if (ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Intent map_intent = new Intent(DetailActivity.this, MapsActivity.class);
                        map_intent.putExtra("name", name);
                        startActivity(map_intent);
                    } else {
                        ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                        Toast.makeText(this, "Konum izni gerekli", Toast.LENGTH_SHORT).show();
                    }
                });

                getLifecycle().addObserver(youTubePlayerView);
                youTubePlayerView.addYouTubePlayerListener(new  AbstractYouTubePlayerListener(){
                    @Override
                    public  void  onReady ( @NonNull YouTubePlayer youTubePlayer ) {



                        youTubePlayer.loadVideo(videoId, 0 );
                    }
                });

                int[] cards = new int[] {R.id.card_image0, R.id.card_image1,R.id.card_image2,R.id.card_image3,R.id.card_image4,R.id.card_image5,
                        R.id.card_image6,R.id.card_image7,R.id.card_image8,R.id.card_image9,R.id.card_image10};

                int[] imageId= new int[] {R.id.imageview0,R.id.imageview1,R.id.imageview2,R.id.imageview3,R.id.imageview4
                        ,R.id.imageview5,R.id.imageview6,R.id.imageview7,R.id.imageview8,R.id.imageview9,R.id.imageview10};

                for (int i=0; i<images.size(); i++){
                    CardView cardView = findViewById(cards[i]);
                    cardView.setVisibility(View.VISIBLE);
                    System.out.println(i);
                    ImageView imageView = findViewById(imageId[i]);
                    imageView.setVisibility(View.VISIBLE);
                    Glide.with(this).load(images.get(i)).into(imageView);

                    call.setOnClickListener(view -> {
                        Uri uri = Uri.parse("tel:" + numara);
                        Intent call_intent = new Intent(Intent.ACTION_DIAL, uri);
                        startActivity(call_intent);
                    });


                }

                if(firebaseUser != null){

                    kaydet.setOnClickListener(view -> {
            /*
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            SharedPreferences.Editor editor2 = sharedPreferences.edit();
            editor2.putString(id + "=date", currentDate);
            editor2.apply();

             */
                        String new_id2 = sharedPreferences.getString(id,"");
                        if (new_id2 != null && new_id2.equals(id)) {
                            sharedPreferences.edit().remove(id).apply();
                            kaydet.setBackgroundResource(R.drawable.button_gray);
                            kaydet_image.setImageResource(R.drawable.ic_save_gray);
                            Toast.makeText(this, "İçerik silindi", Toast.LENGTH_SHORT).show();
                            new_id2 = null;
                        } else {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(id, id);
                            editor.apply();
                            Toast.makeText(this, "İçerik kaydedildi", Toast.LENGTH_SHORT).show();
                            kaydet.setBackgroundResource(R.drawable.button);
                            kaydet_image.setImageResource(R.drawable.ic_save_orange);
                        }
                    });

                    if (id != null && id.equals(new_id)) {
                        kaydet.setBackgroundResource(R.drawable.button);
                        kaydet_image.setImageResource(R.drawable.ic_save_orange);
                    } else {
                        kaydet.setBackgroundResource(R.drawable.button_gray);
                        kaydet_image.setImageResource(R.drawable.ic_save_gray);
                    }

                }else {
                    kaydet.setBackgroundResource(R.drawable.button_gray);
                    kaydet_image.setImageResource(R.drawable.ic_save_gray);
                    kaydet.setOnClickListener(view -> {
                        BottomDialog bottomDialog = new BottomDialog(this,firebaseUser,auth,
                                firebaseFirestore,null,null,null);
                        bottomDialog.loginDialog(1);
                    });
                }
                break;
            case "tarihiyerler":
                sibs_title_text.setVisibility(View.GONE);
                sibs_layout.setVisibility(View.GONE);
                children_layout.setVisibility(View.GONE);
                children_title_text.setVisibility(View.GONE);
                String name7 = intent.getStringExtra("name");
                String detay7 = intent.getStringExtra("exp");
                String numara7 = intent.getStringExtra("phone");
                String videoId7 = intent.getStringExtra("videoId");
                 id = intent.getStringExtra("id");
                ArrayList<String> images7 = intent.getStringArrayListExtra("imageUrl");
                ArrayList<String> times7 = intent.getStringArrayListExtra("times");
                ArrayList<String> location7 = intent.getStringArrayListExtra("location");

                new_id = sharedPreferences.getString(id, "");

                translator.translate(baslik,name7,targetLanguage);
                translator.translate(detay_text,detay7,targetLanguage);

                SimpleDateFormat sdf7 = new SimpleDateFormat("EEEE");
                String currentTime7 = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                Date d7 = new Date();
                String dayOfTheWeek7 = sdf7.format(d7);

                konum.setText(location7.get(0));

                for (int i=0; i < times7.size(); i++){
                    String time = times7.get(i).toLowerCase();
                    if (time.matches("her zaman açık")){
                        //holder.time.setBackgroundResource(R.drawable.ic_ellipse_1);
                        time_button.setBackgroundResource(R.drawable.time_background);
                        time_button.setTextColor(getResources().getColor(R.color.primary_green));
                        time_button.setText("Her zaman açık");

                    } else if (time.matches("kapalı") && time.matches(dayOfTheWeek7)){
                        time_button.setBackgroundResource(R.drawable.clan);
                        time_button.setTextColor(getResources().getColor(R.color.yellow_variant));
                        time_button.setText("Kapalı");
                    }  else {
                        time_button.setBackgroundResource(R.drawable.time_background);
                        time_button.setTextColor(getResources().getColor(R.color.primary_green));
                        time_button.setText("Şu an açık");
                    }
                }

                map_button.setOnClickListener(view -> {
                    if (ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Intent map_intent = new Intent(DetailActivity.this, MapsActivity.class);
                        map_intent.putExtra("name", name7);
                        startActivity(map_intent);
                    } else {
                        ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                        Toast.makeText(this, "Konum izni gerekli", Toast.LENGTH_SHORT).show();
                    }
                });

                getLifecycle().addObserver(youTubePlayerView);
                youTubePlayerView.addYouTubePlayerListener(new  AbstractYouTubePlayerListener(){
                    @Override
                    public  void  onReady ( @NonNull YouTubePlayer youTubePlayer ) {



                        youTubePlayer.loadVideo(videoId7, 0 );
                    }
                });

                int[] cards7 = new int[] {R.id.card_image0, R.id.card_image1,R.id.card_image2,R.id.card_image3,R.id.card_image4,R.id.card_image5,
                        R.id.card_image6,R.id.card_image7,R.id.card_image8,R.id.card_image9,R.id.card_image10};

                int[] imageId7= new int[] {R.id.imageview0,R.id.imageview1,R.id.imageview2,R.id.imageview3,R.id.imageview4
                        ,R.id.imageview5,R.id.imageview6,R.id.imageview7,R.id.imageview8,R.id.imageview9,R.id.imageview10};

                for (int i=0; i<images7.size(); i++){
                    CardView cardView = findViewById(cards7[i]);
                    cardView.setVisibility(View.VISIBLE);
                    System.out.println(i);
                    ImageView imageView = findViewById(imageId7[i]);
                    imageView.setVisibility(View.VISIBLE);
                    Glide.with(this).load(images7.get(i)).into(imageView);

                    call.setOnClickListener(view -> {
                        Uri uri = Uri.parse("tel:" + numara7);
                        Intent call_intent = new Intent(Intent.ACTION_DIAL, uri);
                        startActivity(call_intent);
                    });


                }

                if(firebaseUser != null){

                    kaydet.setOnClickListener(view -> {
            /*
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            SharedPreferences.Editor editor2 = sharedPreferences.edit();
            editor2.putString(id + "=date", currentDate);
            editor2.apply();

             */
                        String new_id2 = sharedPreferences.getString(id,"");
                        if (new_id2 != null && new_id2.equals(id)) {
                            sharedPreferences.edit().remove(id).apply();
                            kaydet.setBackgroundResource(R.drawable.button_gray);
                            kaydet_image.setImageResource(R.drawable.ic_save_gray);
                            Toast.makeText(this, "İçerik silindi", Toast.LENGTH_SHORT).show();
                            new_id2 = null;
                        } else {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(id, id);
                            editor.apply();
                            Toast.makeText(this, "İçerik kaydedildi", Toast.LENGTH_SHORT).show();
                            kaydet.setBackgroundResource(R.drawable.button);
                            kaydet_image.setImageResource(R.drawable.ic_save_orange);
                        }
                    });

                    if (id != null && id.equals(new_id)) {
                        kaydet.setBackgroundResource(R.drawable.button);
                        kaydet_image.setImageResource(R.drawable.ic_save_orange);
                    } else {
                        kaydet.setBackgroundResource(R.drawable.button_gray);
                        kaydet_image.setImageResource(R.drawable.ic_save_gray);
                    }

                }else {
                    kaydet.setBackgroundResource(R.drawable.button_gray);
                    kaydet_image.setImageResource(R.drawable.ic_save_gray);
                    kaydet.setOnClickListener(view -> {
                        BottomDialog bottomDialog = new BottomDialog(this,firebaseUser,auth,
                                firebaseFirestore,null,null,null);
                        bottomDialog.loginDialog(1);
                    });
                }

                break;
            case "onemlikisiler":
                youTubePlayerView.setVisibility(View.GONE);
                time_button.setVisibility(View.GONE);
                adres_layout.setVisibility(View.GONE);
                adres_title.setVisibility(View.GONE);
                saat_layout.setVisibility(View.GONE);
                saat_title.setVisibility(View.GONE);
                bottom.setVisibility(View.GONE);
                youTubePlayerView.setVisibility(View.GONE);
                String children = intent.getStringExtra("children");
                String exp = intent.getStringExtra("exp");
                String sibs = intent.getStringExtra("sibs");
                 id = intent.getStringExtra("id");
                String image2 = intent.getStringExtra("image");
                String name2 = intent.getStringExtra("name");

                children_text.setText(children);
                detay_text.setText(exp);
                sibs_text.setText(sibs);
                baslik.setText(name2);
                CardView cardView = findViewById(R.id.card_image0);
                cardView.setVisibility(View.VISIBLE);
                ImageView imageView = findViewById(R.id.imageview0);
                imageView.setVisibility(View.VISIBLE);
                Glide.with(this).load(image2).into(imageView);

                break;
            case "digeryerler":
                sibs_title_text.setVisibility(View.GONE);
                sibs_layout.setVisibility(View.GONE);
                children_layout.setVisibility(View.GONE);
                children_title_text.setVisibility(View.GONE);
                String name1 = intent.getStringExtra("name");
                String detay1 = intent.getStringExtra("exp");
                String numara1 = intent.getStringExtra("phone");
                String videoId1 = intent.getStringExtra("videoId");
               //  id = intent.getStringExtra("id");
                ArrayList<String> images1 = intent.getStringArrayListExtra("imageUrl");
                ArrayList<String> times1 = intent.getStringArrayListExtra("times");
                ArrayList<String> location1 = intent.getStringArrayListExtra("location");
               // new_id = sharedPreferences.getString(id, "");

                translator.translate(baslik,name1,targetLanguage);
                translator.translate(detay_text,detay1,targetLanguage);

                SimpleDateFormat sdf1 = new SimpleDateFormat("EEEE");
                String currentTime1 = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                Date d1 = new Date();
                String dayOfTheWeek1 = sdf1.format(d1);

                //konum.setText(location.get(0));

                for (int i=0; i < times1.size(); i++){
                    String time = times1.get(i).toLowerCase();
                    if (time.matches("her zaman açık")){
                        //holder.time.setBackgroundResource(R.drawable.ic_ellipse_1);
                        time_button.setBackgroundResource(R.drawable.time_background);
                        time_button.setTextColor(getResources().getColor(R.color.primary_green));
                        time_button.setText("Her zaman açık");

                    } else if (time.matches("kapalı") && time.matches(dayOfTheWeek1)){
                        time_button.setBackgroundResource(R.drawable.clan);
                        time_button.setTextColor(getResources().getColor(R.color.yellow_variant));
                        time_button.setText("Kapalı");
                    }  else {
                        time_button.setBackgroundResource(R.drawable.time_background);
                        time_button.setTextColor(getResources().getColor(R.color.primary_green));
                        time_button.setText("Şu an açık");
                    }
                }

                map_button.setOnClickListener(view -> {
                    if (ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Intent map_intent = new Intent(DetailActivity.this, MapsActivity.class);
                        map_intent.putExtra("name", name1);
                        startActivity(map_intent);
                    } else {
                        ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                        Toast.makeText(this, "Konum izni gerekli", Toast.LENGTH_SHORT).show();
                    }
                });

                getLifecycle().addObserver(youTubePlayerView);
                youTubePlayerView.addYouTubePlayerListener(new  AbstractYouTubePlayerListener(){
                    @Override
                    public  void  onReady ( @NonNull YouTubePlayer youTubePlayer ) {



                        youTubePlayer.loadVideo(videoId1, 0 );
                    }
                });

                int[] cards1 = new int[] {R.id.card_image0, R.id.card_image1,R.id.card_image2,R.id.card_image3,R.id.card_image4,R.id.card_image5,
                        R.id.card_image6,R.id.card_image7,R.id.card_image8,R.id.card_image9,R.id.card_image10};

                int[] imageId1= new int[] {R.id.imageview0,R.id.imageview1,R.id.imageview2,R.id.imageview3,R.id.imageview4
                        ,R.id.imageview5,R.id.imageview6,R.id.imageview7,R.id.imageview8,R.id.imageview9,R.id.imageview10};

                for (int i=0; i<images1.size(); i++) {
                    CardView cardView1 = findViewById(cards1[i]);
                    cardView1.setVisibility(View.VISIBLE);
                    System.out.println(i);
                    ImageView imageView1 = findViewById(imageId1[i]);
                    imageView1.setVisibility(View.VISIBLE);
                    Glide.with(this).load(images1.get(i)).into(imageView1);
                }

                    call.setOnClickListener(view -> {
                        Uri uri = Uri.parse("tel:" + numara1);
                        Intent call_intent = new Intent(Intent.ACTION_DIAL, uri);
                        startActivity(call_intent);
                    });
                break;
            case "digerbilgiler":
                youTubePlayerView.setVisibility(View.GONE);
                time_button.setVisibility(View.GONE);
                adres_layout.setVisibility(View.GONE);
                adres_title.setVisibility(View.GONE);
                saat_layout.setVisibility(View.GONE);
                saat_title.setVisibility(View.GONE);
                bottom.setVisibility(View.GONE);
                sibs_title_text.setVisibility(View.GONE);
                sibs_layout.setVisibility(View.GONE);
                children_layout.setVisibility(View.GONE);
                children_title_text.setVisibility(View.GONE);

                String name3 = intent.getStringExtra("name");
                String exp1 = intent.getStringExtra("exp");
                String image3 = intent.getStringExtra("image");
                String id = intent.getStringExtra("id");

                baslik.setText(name3);
                detay_text.setText(exp1);
                CardView cardView1 = findViewById(R.id.card_image0);
                cardView1.setVisibility(View.VISIBLE);
                ImageView imageView1 = findViewById(R.id.imageview0);
                imageView1.setVisibility(View.VISIBLE);
                Glide.with(this).load(image3).into(imageView1);
                break;
        }

        /*
        else if (time.matches(dayOfTheWeek) ){
            time_button.setBackgroundResource(R.drawable.time_background);
            time_button.setTextColor(getResources().getColor(R.color.primary_green));
            time_button.setText("Şu an açık");
        }

         */

        //MapsActivity.latitude = location.get(2);

    }

    @Override
    public void addLifeCycleCallBack(YouTubePlayerView youTubePlayerView) {
            getLifecycle().addObserver(youTubePlayerView);
    }

    public boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;}
        else {
            ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            return false;}
    }
}