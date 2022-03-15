package com.agency11.sogutapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.agency11.sogutapp.R;
import com.agency11.sogutapp.ReadData;
import com.agency11.sogutapp.adapter.ListAdapter;
import com.agency11.sogutapp.model.Diger_Bilgiler;
import com.agency11.sogutapp.model.Diger_Yerler;
import com.agency11.sogutapp.model.Onemli_Kisiler;
import com.agency11.sogutapp.model.Tarihi_Yerler;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ListAdapter(ListActivity.this, tarihi_yerlers,onemli_kisilers,diger_yerlers,diger_bilgilers, false);
        readData = new ReadData(this,recyclerView,firebaseFirestore,shimmerFrameLayout,adapter);
        recyclerView.setHasFixedSize(true);

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
                title.setText("Diğer Yerler");
                break;
            case "digerbilgiler":
                readData.diger_bilgiler(diger_bilgilers);
                title.setText("Diğer Bilgiler");
                break;
        }

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
}