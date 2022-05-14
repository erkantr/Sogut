package com.agency11.sogutapp.method;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agency11.sogutapp.R;
import com.agency11.sogutapp.activity.AdsActivity;
import com.agency11.sogutapp.activity.ListActivity;
import com.agency11.sogutapp.adapter.ListAdapter;
import com.agency11.sogutapp.model.Diger_Bilgiler;
import com.agency11.sogutapp.model.Diger_Yerler;
import com.agency11.sogutapp.model.Onemli_Kisiler;
import com.agency11.sogutapp.model.Tarihi_Yerler;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

public class ReadData {

    Activity mContext;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    ListAdapter adapter;
    ShimmerFrameLayout shimmerFrameLayout;

    public ReadData(Activity mContext, RecyclerView recyclerView, FirebaseFirestore firebaseFirestore,
                    ShimmerFrameLayout shimmerFrameLayout,ListAdapter adapter){
        this.mContext = mContext;
        this.recyclerView = recyclerView;
        this.firebaseFirestore = firebaseFirestore;
        this.shimmerFrameLayout = shimmerFrameLayout;
        this.adapter = adapter;
    }

    public void diger_bilgiler(ArrayList<Diger_Bilgiler> diger_bilgilers){
        firebaseFirestore.collection("digerbilgiler")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Diger_Bilgiler> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Diger_Bilgiler update = new Diger_Bilgiler(
                                        document.getId(),
                                        document.getData().get("exp").toString(),
                                        document.getData().get("image").toString(),
                                        document.getData().get("name").toString());


                                list.add(update);
                            }

                            for(int i=0;i<list.size();i++){
                                if(i%3==0 && i!=0)
                                {
                                    diger_bilgilers.add(null);
                                }
                                diger_bilgilers.add(list.get(i));
                            }

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext.getApplicationContext());
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(linearLayoutManager);

                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            adapter = new ListAdapter(mContext, null,null,null,diger_bilgilers, false);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setHasFixedSize(true);
                            adapter.notifyDataSetChanged();


                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void onemli_kisiler(ArrayList<Onemli_Kisiler> onemli_kisilers){
        firebaseFirestore.collection("onemlikisiler")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Onemli_Kisiler update = new Onemli_Kisiler(
                                        document.getId(),
                                        document.getData().get("children").toString(),
                                        document.getData().get("exp").toString(),
                                        document.getData().get("name").toString(),
                                        document.getData().get("sibs").toString(),
                                        document.getData().get("wife").toString(),
                                        document.getData().get("mother").toString(),
                                        document.getData().get("father").toString(),
                                        document.getData().get("image").toString());


                                onemli_kisilers.add(update);
                            }
                            onemli_kisilers.add(null);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext.getApplicationContext());
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(linearLayoutManager);

                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            adapter = new ListAdapter(mContext, null,onemli_kisilers,null,null, false);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setHasFixedSize(true);
                            adapter.notifyDataSetChanged();



                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void diger_yerler(ArrayList<Diger_Yerler> diger_yerlers){
        firebaseFirestore.collection("digeryerler")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Diger_Yerler> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Diger_Yerler update = new Diger_Yerler(
                                        document.getData().get("location").toString(),
                                        document.getId(),
                                        document.getData().get("exp").toString(),
                                        (ArrayList<String>) document.get("imageUrl"),
                                        document.getGeoPoint("location_id"),
                                         document.getData().get("name").toString(),
                                        document.getData().get("phone").toString(),
                                        (ArrayList<String>) document.get("times"),
                                        document.getData().get("videoId").toString());

                                list.add(update);
                            }

                            for(int i=0;i<list.size();i++){
                                if(i%3==0 && i!=0)
                                {
                                    diger_yerlers.add(null);
                                }
                                diger_yerlers.add(list.get(i));
                            }

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext.getApplicationContext());
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(linearLayoutManager);

                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            adapter = new ListAdapter(mContext, null,null,diger_yerlers,null, false);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setHasFixedSize(true);
                            adapter.notifyDataSetChanged();



                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void tarihiYerler(ArrayList<Tarihi_Yerler> tarihi_yerlers) {
        firebaseFirestore.collection("tarihiyerler")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Tarihi_Yerler> list = new ArrayList<>();
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

                                list.add(update);
                            }
                            for(int i=0;i<list.size();i++){
                                if(i%3==0 && i!=0)
                                {
                                    tarihi_yerlers.add(null);
                                }
                                tarihi_yerlers.add(list.get(i));
                            }

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext.getApplicationContext());
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(linearLayoutManager);

                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            adapter = new ListAdapter(mContext, tarihi_yerlers,null,null,null, false);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setHasFixedSize(true);
                            adapter.notifyDataSetChanged();


                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    /*
    public void getSearchTarihiYerler(ArrayList<Tarihi_Yerler> tarihi_yerlers){

        firebaseFirestore.collection("tarihiyerler").whereEqualTo("name", 1)
                .addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            System.err.println("Listen failed:" + e);
                            return;
                        }
                       //Tarihi_Yerler tarihi_yerlers = new ArrayList<Tarihi_Yerler>();

                        for (DocumentSnapshot doc : snapshots) {
                            Tarihi_Yerler user = doc.toObject(Tarihi_Yerler.class);
                            tarihi_yerlers.add(user);
                        }
                        updateListUsers(tarihi_yerlers);
                    }
                });
        }

        public void searchTarihiYerler(String recherche, ArrayList<Tarihi_Yerler> tarihi_yerlers){
            if (recherche.length() > 0)
                recherche = recherche.substring(0, 1).toUpperCase() + recherche.substring(1).toLowerCase();

            ArrayList<Tarihi_Yerler> results = new ArrayList<>();
            for(Tarihi_Yerler user : tarihi_yerlers){
                if(user.getName() != null && user.getName().contains(recherche)){
                    results.add(user);
                }
            }
            updateListUsers(results);
        }

    private void updateListUsers(ArrayList<Tarihi_Yerler> listUsers) {

        // Sort the list by date
        Collections.sort(listUsers, new Comparator<Tarihi_Yerler>() {
            @Override
            public int compare(Tarihi_Yerler o1, Tarihi_Yerler o2) {
                int res = -1;
                //if (o1.get() > (o2.getDate())) {
                  //  res = 1;
                //}
                return res;
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext.getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter = new ListAdapter(mContext, listUsers, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }

     */

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
                           // documentSnapshot.getData().get("image").toString(),
                            documentSnapshot.getData().get("name").toString(),
                            documentSnapshot.getData().get("phone").toString(),
                            (ArrayList<String>) documentSnapshot.get("times"),
                            documentSnapshot.getData().get("videoId").toString(),
                            documentSnapshot.getGeoPoint("location_id"));


                    tarihi_yerlers.add(update);

                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext.getApplicationContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    adapter = new ListAdapter(mContext, tarihi_yerlers,null,null,null, false);

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            });
        }
    }

    public void adTime(AdRequest adRequest, Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("ad", Context.MODE_PRIVATE);
        int click = preferences.getInt("click", 0);
        SharedPreferences.Editor editor = preferences.edit();
        System.out.println("click = "+ click);

        if (click < 10) {
            editor.putInt("click", 1+click);
            editor.apply();
        } else {
            editor.putInt("click", 0);
            /*
            RequestConfiguration requestConfiguration = new RequestConfiguration.Builder()
                    .setTestDeviceIds(Arrays.asList("EAD98D52B6934D07B9DAD189F4BACB64")).build();
            MobileAds.setRequestConfiguration(requestConfiguration);

             */
            MobileAds.initialize(activity);
            adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(activity,activity.getString(R.string.interstitial_ad_unit_id), adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            interstitialAd.show(activity);

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
                            Toast.makeText(activity, "a"+ loadAdError, Toast.LENGTH_SHORT).show();
                        }
                    });

            editor.apply();
        }
    }
}
