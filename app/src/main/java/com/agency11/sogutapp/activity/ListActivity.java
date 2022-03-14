package com.agency11.sogutapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.agency11.sogutapp.R;
import com.agency11.sogutapp.ReadData;
import com.agency11.sogutapp.adapter.ListAdapter;
import com.agency11.sogutapp.model.Tarihi_Yerler;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<Tarihi_Yerler> tarihi_yerlers;
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
        firebaseFirestore = FirebaseFirestore.getInstance();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ListAdapter(ListActivity.this, tarihi_yerlers, false);
        readData = new ReadData(this,recyclerView,firebaseFirestore,shimmerFrameLayout,adapter);
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        String page = intent.getStringExtra("list");
        switch (page){
            case "kaydedilenler":
                readData.kaydedilenTarihiYerler(tarihi_yerlers);
                break;
            case "tarihiyerler":
                readData.tarihiYerler(tarihi_yerlers);
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