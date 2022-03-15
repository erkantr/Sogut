package com.agency11.sogutapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.agency11.sogutapp.BottomDialog;
import com.agency11.sogutapp.R;
import com.agency11.sogutapp.ReadData;
import com.agency11.sogutapp.Size;
import com.agency11.sogutapp.adapter.ListAdapter;
import com.agency11.sogutapp.model.Tarihi_Yerler;
import com.agency11.sogutapp.model.User;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        shimmerFrameLayout = findViewById(R.id.shimmer);
        tarihi_yerlers = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ListAdapter(MainActivity.this, tarihi_yerlers, false);
        //readData = new ReadData(MainActivity.this, recyclerView, firebaseFirestore, shimmerFrameLayout, adapter);
        //readData.getSearchTarihiYerler(tarihi_yerlers);

        kullanici_adi = findViewById(R.id.kullanici_adi);
        RelativeLayout tarihi_yerler = findViewById(R.id.tarihi_yerler_item);
        RelativeLayout onemli_kisiler = findViewById(R.id.onemli_kisiler_item);
        RelativeLayout kutsal_mekanlar = findViewById(R.id.diger_yerler_item);
        RelativeLayout diger_bilgiler = findViewById(R.id.diger_bilgiler_item);
        RelativeLayout kaydedilenler = findViewById(R.id.kaydedilenler_item);
        RelativeLayout hakkimizda = findViewById(R.id.hakkinda_item);

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


        size.setWidth(kutsal_mekanlar, 167);
        size.setHeight(kutsal_mekanlar, 167);
        size.setMargin(kutsal_mekanlar, 16, 16, 0, 0);


        size.setWidth(diger_bilgiler, 167);
        size.setHeight(diger_bilgiler, 167);
        size.setMargin(diger_bilgiler, 0, 16, 16, 0);

        size.setWidth(kaydedilenler, 167);
        size.setHeight(kaydedilenler, 167);
        size.setMargin(kaydedilenler, 16, 16, 0, 21);

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
                    kullanici_adi.setText("Merhaba,\n" + user.getName());
                }
            });
        } else {
            kullanici_adi.setVisibility(View.GONE);
            //linearLayout.setBackgroundResource(R.drawable.background2);
            profile_image.setImageResource(R.drawable.ic_login);
        }

        profile_image.setOnClickListener(view -> {
            if (firebaseUser != null) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            } else {
                bottomDialog.loginDialog(0);
            }
        });


        tarihi_yerler.setOnClickListener(view1 -> {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("list", "tarihiyerler");
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
                    adapter = new ListAdapter(MainActivity.this, tarihi_yerlers, false);
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
                            adapter = new ListAdapter(MainActivity.this, tarihi_yerlers, false);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
                /*
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

                 */
                }

            });
        /*
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

         */

                                      }
    /*
    public void Filtre(String a) {
        adapter.getFilter().filter(a);
    }

     */
}