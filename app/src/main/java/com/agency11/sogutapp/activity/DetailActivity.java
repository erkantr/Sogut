package com.agency11.sogutapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agency11.sogutapp.BottomDialog;
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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.PlayerUiController;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.menu.YouTubePlayerMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();

        RelativeLayout kaydet = findViewById(R.id.kaydet_button);
        ImageView kaydet_image = findViewById(R.id.kaydet_button_image);
        TextView pagetitle = findViewById(R.id.page_title);
        firebaseFirestore = FirebaseFirestore.getInstance();
        RelativeLayout relativeLayout = findViewById(R.id.map_button);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);

        RelativeLayout call = findViewById(R.id.call_button);

        TextView baslik = findViewById(R.id.baslik);
        TextView detay_text = findViewById(R.id.detay);

        Translator translator = new Translator();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String detay = intent.getStringExtra("exp");
        String numara = intent.getStringExtra("phone");
        String videoId = intent.getStringExtra("videoId");
        String id = intent.getStringExtra("id");
        ArrayList<String> images = intent.getStringArrayListExtra("imageUrl");
        ArrayList<String> times = intent.getStringArrayListExtra("times");
        ArrayList<String> location = intent.getStringArrayListExtra("location");
        String targetLanguage = Locale.getDefault().getLanguage();

        sharedPreferences = getSharedPreferences("id", Context.MODE_PRIVATE);
        new_id = sharedPreferences.getString(id, "");

        translator.translate(baslik,name,targetLanguage);
        translator.translate(detay_text,detay,targetLanguage);

        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new  AbstractYouTubePlayerListener(){
            @Override
            public  void  onReady ( @NonNull YouTubePlayer youTubePlayer ) {



                youTubePlayer.loadVideo(videoId, 0 );
            }
        });

        int[] cards = new int[] {R.id.card_image0,R.id.card_image2,R.id.card_image3,R.id.card_image4,R.id.card_image5,
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

        }

        if(firebaseUser != null){
        kaydet.setOnClickListener(view -> {
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

        call.setOnClickListener(view -> {
            Uri uri = Uri.parse("tel:" + numara);
            Intent call_intent = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(call_intent);
        });
    }

    @Override
    public void addLifeCycleCallBack(YouTubePlayerView youTubePlayerView) {
            getLifecycle().addObserver(youTubePlayerView);
    }
}