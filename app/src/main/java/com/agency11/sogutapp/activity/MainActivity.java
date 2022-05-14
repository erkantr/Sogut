package com.agency11.sogutapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agency11.sogutapp.method.BottomDialog;
import com.agency11.sogutapp.method.LocaleHelper;
import com.agency11.sogutapp.R;
import com.agency11.sogutapp.method.ReadData;
import com.agency11.sogutapp.method.Size;
import com.agency11.sogutapp.adapter.ListAdapter;
import com.agency11.sogutapp.model.Tarihi_Yerler;
import com.agency11.sogutapp.model.User;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    ImageView profile_image;
    LinearLayout linearLayout;
    TextView kullanici_adi;
    List<Integer> myImages;

    ArrayList<Tarihi_Yerler> tarihi_yerlers;
    ShimmerFrameLayout shimmerFrameLayout;
    RecyclerView recyclerView;
    ListAdapter adapter;
    ReadData readData;
    int max = 7;
    int min = 0;
    int random = new Random().nextInt(max - min) + min;
    AdRequest adRequest;
    AdView adView;

    InterstitialAd fullscreenAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        shimmerFrameLayout = findViewById(R.id.shimmer);
        tarihi_yerlers = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        adView = findViewById(R.id.adView);
        MobileAds.initialize(this);
        bannerAd();


        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //adapter = new ListAdapter(MainActivity.this, tarihi_yerlers, false);
        //readData = new ReadData(MainActivity.this, recyclerView, firebaseFirestore, shimmerFrameLayout, adapter);
        readData = new ReadData(this, null, null, null, null);
        adRequest = new AdRequest.Builder().build();
        //readData.getSearchTarihiYerler(tarihi_yerlers);
        //readData.adTime(adRequest,this);

        SharedPreferences sharedPreferences2 = getSharedPreferences("lang1", Context.MODE_PRIVATE);
        String targetLanguage = sharedPreferences2.getString("language", "");
        LocaleHelper.setLocale(this, targetLanguage);

        kullanici_adi = findViewById(R.id.kullanici_adi);
        RelativeLayout tarihi_yerler = findViewById(R.id.tarihi_yerler_item);
        RelativeLayout onemli_kisiler = findViewById(R.id.onemli_kisiler_item);
        RelativeLayout diger_yerler = findViewById(R.id.diger_yerler_item);
        RelativeLayout diger_bilgiler = findViewById(R.id.diger_bilgiler_item);
        LinearLayout kaydedilenler = findViewById(R.id.kaydedilenler_item);
        LinearLayout hakkimizda = findViewById(R.id.hakkinda_item);
        ImageView kaydedilenler_image = findViewById(R.id.kaydedilenler_image);
        ImageView hakkinda_image = findViewById(R.id.hakkinda_image);

        linearLayout = findViewById(R.id.profile_background);
        profile_image = findViewById(R.id.profile_image);
        LinearLayout linearLayout1 = findViewById(R.id.main_background);
        TextView title = findViewById(R.id.title);
        //FrameLayout search_view = findViewById(R.id.search_view);
        //SearchView search_view = findViewById(R.id.search_view);
        LinearLayout grid_linear = findViewById(R.id.linear_grid);
        LinearLayout profile_info = findViewById(R.id.profile_info);

        Size size = new Size(this);

        size.setSize(title, 20);
        size.setMargin(title, 16, 18, 0, 0);

        String slides = "background";

        int[] myImageList = new int[]{R.drawable.background1, R.drawable.background2, R.drawable.background3,
                R.drawable.background4, R.drawable.background5, R.drawable.background6, R.drawable.background7};

        //myImages = getResourceList(R.array.myImages);
        //final Random random = new Random();
        //int randomInt = random.nextInt(myImages.size());
        //int drawableID = myImages.get(random);
        int drawableID = myImageList[random];
        linearLayout.setBackgroundResource(drawableID);

        //linearLayout.setBackgroundResource(R.drawable.background1);


        size.setHeight(kaydedilenler_image, 64);
        size.setWidth(kaydedilenler_image, 64);
        size.setMargin(kaydedilenler_image, 0, 24, 0, 0);

        size.setHeight(hakkinda_image, 64);
        size.setWidth(hakkinda_image, 64);
        size.setMargin(hakkinda_image, 0, 24, 0, 0);

        size.setHeight(linearLayout, 220);
        size.setHeight(profile_image, 54);
        size.setWidth(profile_image, 54);
        size.setMargin(profile_image, 16, 48, 0, 0);
        size.setMargin(profile_info, 0, 16, 0, 0);

        size.setMargin(kullanici_adi, 7, 48, 0, 0);
        size.setSize(kullanici_adi, 19);

        size.setMargin(linearLayout1, 0, -52, 0, 0);

        size.setMargin(title, 16, 18, 0, 0);
        size.setSize(title, 18);

        //size.setMargin(search_view, 16, 16, 16, 0);

        size.setPadding(grid_linear, 0, 16, 0, 0);

        size.setMargin(tarihi_yerler, 16, 0, 0, 0);
        size.setWidth(tarihi_yerler, 167);
        size.setHeight(tarihi_yerler, 167);
        size.setMargin(tarihi_yerler, 16, 0, 0, 0);

        size.setWidth(onemli_kisiler, 167);
        size.setHeight(onemli_kisiler, 167);
        size.setMargin(onemli_kisiler, 0, 0, 16, 0);


        size.setWidth(diger_yerler, 167);
        size.setHeight(diger_yerler, 167);
        size.setMargin(diger_yerler, 16, 16, 0, 0);


        size.setWidth(diger_bilgiler, 167);
        size.setHeight(diger_bilgiler, 167);
        size.setMargin(diger_bilgiler, 0, 16, 16, 0);

        size.setWidth(kaydedilenler, 167);
        size.setHeight(kaydedilenler, 167);
        size.setMargin(kaydedilenler, 16, 16, 0, 80);

        size.setWidth(hakkimizda, 167);
        size.setHeight(hakkimizda, 167);
        size.setMargin(hakkimizda, 0, 16, 16, 21);


        BottomDialog bottomDialog = new BottomDialog(this, firebaseUser, auth, firebaseFirestore, kullanici_adi,
                profile_image, linearLayout);

        if (firebaseUser != null) {
            profile_image.setImageResource(R.drawable.profile);
            kullanici_adi.setVisibility(View.VISIBLE);
            //linearLayout.setBackgroundResource(R.drawable.background1);
            DocumentReference reference = firebaseFirestore.collection("users").document(firebaseUser.getUid());
            reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);
                    kullanici_adi.setText(getResources().getString(R.string.merhaba) + user.getName());
                }
            });
        } else {
            kullanici_adi.setVisibility(View.GONE);
            //linearLayout.setBackgroundResource(R.drawable.background2);
            profile_image.setImageResource(R.drawable.ic_login);
        }

        profile_image.setOnClickListener(view -> {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (firebaseUser != null) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            } else {
                bottomDialog.loginDialog(2);
            }
        });


        tarihi_yerler.setOnClickListener(view1 -> {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("list", "tarihiyerler");
            intent.putExtra("language", targetLanguage);
            startActivity(intent);
        });

        onemli_kisiler.setOnClickListener(view1 -> {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("list", "onemlikisiler");
            startActivity(intent);
        });

        diger_bilgiler.setOnClickListener(view1 -> {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("list", "digerbilgiler");
            startActivity(intent);
        });

        diger_yerler.setOnClickListener(view1 -> {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("list", "digeryerler");
            startActivity(intent);
        });

        kaydedilenler.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("list", "kaydedilenler");
            startActivity(intent);
        });

        hakkimizda.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
/*
        EditText search = findViewById(R.id.search_view);

        RelativeLayout touchInterceptor = findViewById(R.id.relative_layout);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (search.isFocused()) {
                        Rect outRect = new Rect();
                        search.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            search.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });


        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    //recyclerView.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.startShimmer();
                    //recyclerView.setAdapter(adapter);
                    //recyclerView.setHasFixedSize(true);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //recyclerView.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //readData.searchTarihiYerler(editable.toString(),tarihi_yerlers);


                if (editable.toString().isEmpty()){
                   // adapter = new ListAdapter(MainActivity.this, tarihi_yerlers, false);
                    readData = new ReadData(MainActivity.this, recyclerView, firebaseFirestore, shimmerFrameLayout, adapter);
                    readData.tarihiYerler(tarihi_yerlers);
                } else {

                Query query = FirebaseFirestore.getInstance().collection("tarihiyerler")
                        .whereEqualTo("name", editable.toString());

                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Tarihi_Yerler update = new Tarihi_Yerler(
                                        document.getId(),
                                        document.getData().get("exp").toString(),
                                        (ArrayList<String>) document.get("imageUrl"),
                                        (ArrayList<String>) document.get("location"),
                                       // document.getData().get("image").toString(),
                                        document.getData().get("name").toString(),
                                        document.getData().get("phone").toString(),
                                        (ArrayList<String>) document.get("times"),
                                        document.getData().get("videoId").toString(),
                                        document.getGeoPoint("location_id"));


                                tarihi_yerlers.add(update);
                            }
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            //adapter = new ListAdapter(MainActivity.this, tarihi_yerlers, false);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
                if (editable.toString().isEmpty()){
                    adapter = new ListAdapter(MainActivity.this, tarihi_yerlers, false);
                    readData = new ReadData(MainActivity.this, recyclerView, firebaseFirestore, shimmerFrameLayout, adapter);
                    readData.tarihiYerler(tarihi_yerlers);
                } else {
                    shimmerFrameLayout.startShimmer();
                    firebaseFirestore.collection("tarihiyerler")
                            .whereEqualTo("name", editable.toString()).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {

                                        for (QueryDocumentSnapshot document : task.getResult()) {

                                            Tarihi_Yerler update = new Tarihi_Yerler(
                                                    document.getId(),
                                                    document.getData().get("exp").toString(),
                                                    (ArrayList<String>) document.get("imageUrl"),
                                                    (ArrayList<String>) document.get("location"),
                                                    document.getData().get("image").toString(),
                                                    document.getData().get("name").toString(),
                                                    document.getData().get("phone").toString(),
                                                    (ArrayList<String>) document.get("times"),
                                                    document.getData().get("videoId").toString());


                                            tarihi_yerlers.add(update);
                                        }
                                        shimmerFrameLayout.stopShimmer();
                                        shimmerFrameLayout.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        adapter = new ListAdapter(MainActivity.this, tarihi_yerlers, false);
                                        recyclerView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            });
                }

                }

});
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.startShimmer();
                //recyclerView.setVisibility(View.VISIBLE);
                //Filtre(newText);
                return false;
            }
        });


    public void Filtre(String a) {
        adapter.getFilter().filter(a);
    }

     */
    }

    void bannerAd(){
        MobileAds.initialize(this);
        /*
        RequestConfiguration requestConfiguration = new RequestConfiguration.Builder()
                .setTestDeviceIds(Arrays.asList("EAD98D52B6934D07B9DAD189F4BACB64")).build();
        MobileAds.setRequestConfiguration(requestConfiguration);

         */
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Log.d("BannerAdsExample", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d("BannerAdsExample", "onAdClicked");
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Log.d("BannerAdsExample", "onAdClosed");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("BannerAdsExample", "onAdFailedToLoad");
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Log.d("BannerAdsExample", "onAdImpression");
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                Log.d("BannerAdsExample", "onAdOpened");
            }
        });

    }

    void intersititialAd(){
        /*
        RequestConfiguration requestConfiguration = new RequestConfiguration.Builder()
                .setTestDeviceIds(Arrays.asList("EAD98D52B6934D07B9DAD189F4BACB64")).build();
        MobileAds.setRequestConfiguration(requestConfiguration);

         */
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
                        Toast.makeText(MainActivity.this, "a"+ loadAdError, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        readData.adTime(adRequest,this);
        super.onResume();
    }
}