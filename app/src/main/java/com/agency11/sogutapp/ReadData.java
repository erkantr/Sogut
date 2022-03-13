package com.agency11.sogutapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agency11.sogutapp.adapter.ListAdapter;
import com.agency11.sogutapp.model.Tarihi_Yerler;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class ReadData {

    Context mContext;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    ListAdapter adapter;
    ShimmerFrameLayout shimmerFrameLayout;

    public ReadData(Context mContext, RecyclerView recyclerView, FirebaseFirestore firebaseFirestore,
                    ShimmerFrameLayout shimmerFrameLayout,ListAdapter adapter){
        this.mContext = mContext;
        this.recyclerView = recyclerView;
        this.firebaseFirestore = firebaseFirestore;
        this.shimmerFrameLayout = shimmerFrameLayout;
        this.adapter = adapter;
    }

    public void tarihiYerler(ArrayList<Tarihi_Yerler> tarihi_yerlers) {
        firebaseFirestore.collection("tarihiyerler")
                .get()
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
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext.getApplicationContext());
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(linearLayoutManager);

                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            adapter = new ListAdapter(mContext, tarihi_yerlers, false);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void kaydedilenTarihiYerler(ArrayList<Tarihi_Yerler> tarihi_yerlers) {
        SharedPreferences sharedPreferences2 = mContext.getSharedPreferences("id", Context.MODE_PRIVATE);

        Map<String, ?> allEntries = sharedPreferences2.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            DocumentReference documentReference = firebaseFirestore.collection("tarihiyerler")
                    .document(entry.getKey());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Tarihi_Yerler update = new Tarihi_Yerler(
                            documentSnapshot.getId(),
                            documentSnapshot.getData().get("exp").toString(),
                            (ArrayList<String>) documentSnapshot.get("imageUrl"),
                            (ArrayList<String>) documentSnapshot.get("location"),
                            documentSnapshot.getData().get("image").toString(),
                            documentSnapshot.getData().get("name").toString(),
                            documentSnapshot.getData().get("phone").toString(),
                            (ArrayList<String>) documentSnapshot.get("times"),
                            documentSnapshot.getData().get("videoId").toString());


                    tarihi_yerlers.add(update);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext.getApplicationContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    adapter = new ListAdapter(mContext, tarihi_yerlers, false);

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

}