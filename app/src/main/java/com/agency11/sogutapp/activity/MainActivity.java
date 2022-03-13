package com.agency11.sogutapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    ImageView profile_image;
    LinearLayout linearLayout;
    TextView kullanici_adi;

    ArrayList<Tarihi_Yerler> tarihi_yerlers;
    ShimmerFrameLayout shimmerFrameLayout;
    RecyclerView recyclerView;
    ListAdapter adapter;
    ReadData readData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        shimmerFrameLayout = findViewById(R.id.shimmer);
        tarihi_yerlers = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        adapter = new ListAdapter(MainActivity.this, tarihi_yerlers, false);
        readData = new ReadData(this,recyclerView,firebaseFirestore,shimmerFrameLayout,adapter);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

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
        SearchView search_view = findViewById(R.id.search_view);
        LinearLayout grid_linear = findViewById(R.id.linear_grid);
        LinearLayout profile_info = findViewById(R.id.profile_info);

        Size size = new Size(this);

        size.setSize(title, 20);
        size.setMargin(title, 16, 18, 0, 0);


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

        size.setMargin(search_view, 16, 16, 16, 0);

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

        BottomDialog bottomDialog = new BottomDialog(this,firebaseUser,auth,firebaseFirestore, kullanici_adi,
                profile_image,linearLayout);

        if (firebaseUser != null) {
            profile_image.setImageResource(R.drawable.profile);
            kullanici_adi.setVisibility(View.VISIBLE);
            linearLayout.setBackgroundResource(R.drawable.arkaplan);
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
            linearLayout.setBackgroundResource(R.drawable.background2);
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

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.startShimmer();
                recyclerView.setVisibility(View.VISIBLE);
                Filtre(newText);
                return false;
            }
        });

    }
    public void Filtre(String a) {
        adapter.getFilter().filter(a);
    }
}