package com.agency11.sogutapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agency11.sogutapp.BuildConfig;
import com.agency11.sogutapp.method.LocaleHelper;
import com.agency11.sogutapp.R;
import com.agency11.sogutapp.method.ReadData;
import com.agency11.sogutapp.method.Translator;
import com.agency11.sogutapp.adapter.ListAdapter;
import com.agency11.sogutapp.model.Diger_Bilgiler;
import com.agency11.sogutapp.model.Diger_Yerler;
import com.agency11.sogutapp.model.Onemli_Kisiler;
import com.agency11.sogutapp.model.Tarihi_Yerler;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ListActivity extends AppCompatActivity {

    ArrayList<Tarihi_Yerler> tarihi_yerlers;
    ArrayList<Onemli_Kisiler> onemli_kisilers;
    ArrayList<Diger_Yerler> diger_yerlers;
    ArrayList<Diger_Bilgiler> diger_bilgilers;
    FirebaseFirestore firebaseFirestore;
    ShimmerFrameLayout shimmerFrameLayout;
    RecyclerView recyclerView;
    ListAdapter adapter;
    ReadData readData;
    AdRequest adRequest;
    AdView adView;
    InterstitialAd fullscreenAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recyclerview);
        shimmerFrameLayout = findViewById(R.id.shimmer);
        shimmerFrameLayout.startShimmer();
        tarihi_yerlers = new ArrayList<>();
        onemli_kisilers = new ArrayList<>();
        diger_yerlers = new ArrayList<>();
        diger_bilgilers = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        TextView title = findViewById(R.id.title);
        SharedPreferences sharedPreferences2 = getSharedPreferences("lang1", Context.MODE_PRIVATE);
        String targetLanguage = sharedPreferences2.getString("language","");
        LocaleHelper.setLocale(this,targetLanguage);
        //Toast.makeText(this, targetLanguage, Toast.LENGTH_SHORT).show();
        MobileAds.initialize(this);
        /*
        if(fullscreenAdView != null){
            fullscreenAdView.show(this);
            // bisLoaded(false)
        } else{
            Toast.makeText(this,"Reklam yüklenemedi lütfen tekrar deneyin",Toast.LENGTH_SHORT).show();
            //button.isEnabled = true
        }

         */

        //Toast.makeText(this, targetLanguage, Toast.LENGTH_SHORT).show();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,true);
        recyclerView.setLayoutManager(linearLayoutManager);

        Collections.reverse(tarihi_yerlers);
        adapter = new ListAdapter(ListActivity.this, tarihi_yerlers,onemli_kisilers,diger_yerlers,diger_bilgilers, false);
        readData = new ReadData(this,recyclerView,firebaseFirestore,shimmerFrameLayout,adapter);
        Translator translator = new Translator();
        translator.translate(title,title.getText().toString(),shimmerFrameLayout);
        adRequest = new AdRequest.Builder().build();
       // readData.adTime(adRequest,this);


        LinearLayout back = findViewById(R.id.back_layout);

        back.setOnClickListener(view -> {
            NavUtils.navigateUpFromSameTask(ListActivity.this);

        });

        Intent intent = getIntent();
        String page = intent.getStringExtra("list");
        switch (page){
            case "kaydedilenler":
                readData.kaydedilenTarihiYerler(tarihi_yerlers);
                title.setText("Kaydedilenler");
                break;
            case "tarihiyerler":
                readData.tarihiYerler(tarihi_yerlers);
                title.setText("Tarihi Yerler");
                break;
            case "onemlikisiler":
                readData.onemli_kisiler(onemli_kisilers);
                title.setText("Önemli Kişiler");
                break;
            case "digeryerler":
                readData.diger_yerler(diger_yerlers);
                title.setText("Diger Yerler");
                break;
            case "digerbilgiler":
                readData.diger_bilgiler(diger_bilgilers);
                title.setText("Diğer Bilgiler");
                break;
        }

        translator.translate(title,title.getText().toString(),null);

    }

    String deleteData;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            deleteData = String.valueOf(tarihi_yerlers.get(position));
            tarihi_yerlers.remove(position);
            adapter.notifyDataSetChanged();

            /*
            Snackbar.make(recyclerView, deleteData,Snackbar.LENGTH_LONG).setAction("Geri al", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tarihi_yerlers.add(position,deleteData);
                    adapter.notifyDataSetChanged();
                }
            });

             */

        }
    };

    void intersititialAd(){
            // fullscreenAdView = InterstitialAd(applicationContext)
            // fullscreenAdView.adUnitId = "ca-app-pub-3940256099942544/1033173712"
            RequestConfiguration requestConfiguration = new RequestConfiguration.Builder()
                    .setTestDeviceIds(Arrays.asList("EAD98D52B6934D07B9DAD189F4BACB64")).build();
            MobileAds.setRequestConfiguration(requestConfiguration);
           // adRequest = new AdRequest.Builder().build();
        adRequest = new AdRequest.Builder().build();
        MobileAds.initialize(this);
        InterstitialAd.load(this,getString(R.string.interstitial_ad_unit_id), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        fullscreenAdView = interstitialAd;
                        //super.onAdLoaded(fullscreenAdView);

                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                Log.d("messages", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                super.onAdShowedFullScreenContent();
                                Log.d("messages", "The ad was shown.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                Log.d("messages", "The ad was dismissed.");
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Toast.makeText(ListActivity.this, "a"+ loadAdError, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    protected void onResume() {
        readData.adTime(adRequest,this);
        super.onResume();
    }
}