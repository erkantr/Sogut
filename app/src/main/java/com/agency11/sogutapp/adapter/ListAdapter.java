package com.agency11.sogutapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agency11.sogutapp.R;
import com.agency11.sogutapp.activity.DetailActivity;
import com.agency11.sogutapp.method.Translator;
import com.agency11.sogutapp.model.Diger_Bilgiler;
import com.agency11.sogutapp.model.Diger_Yerler;
import com.agency11.sogutapp.model.Onemli_Kisiler;
import com.agency11.sogutapp.model.Tarihi_Yerler;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.firebase.firestore.GeoPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private Activity mContext;
    private ArrayList<Tarihi_Yerler> yerler;
    private ArrayList<Onemli_Kisiler> onemli_kisilers;
    private ArrayList<Diger_Yerler> diger_yerlers;
    private ArrayList<Diger_Bilgiler> diger_bilgilers;
    boolean time;
    private View view;
    String list;
    ShimmerFrameLayout shimmerFrameLayout;
    FirestoreRecyclerOptions<Tarihi_Yerler> tarihi_yerlerFirestoreRecyclerOptions;

    private int type_ad = 0;
    private int type_list = 1;

    public ListAdapter(Activity mContext, ArrayList<Tarihi_Yerler> yerler,ArrayList<Onemli_Kisiler> onemli_kisilers
            ,ArrayList<Diger_Yerler> diger_yerlers,ArrayList<Diger_Bilgiler> diger_bilgilers,boolean time){
        this.mContext = mContext;
        this.yerler = yerler;
        this.time = time;
        this.diger_bilgilers = diger_bilgilers;
        this.diger_yerlers = diger_yerlers;
        this.onemli_kisilers = onemli_kisilers;
        //this.searchList = new ArrayList<>(yerler);
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);

        Intent intent1 = mContext.getIntent();
        list = intent1.getStringExtra("list");

        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        int size;
        Intent intent1 = mContext.getIntent();
        list = intent1.getStringExtra("list");
        switch (list){
            case "kaydedilenler":
                size = yerler.size();
                break;
            case "tarihiyerler":
                size = yerler.size();
                break;
            case "onemlikisiler":
                size = onemli_kisilers.size();
                break;
            case "digeryerler":
                size = diger_yerlers.size();
                break;
            case "digerbilgiler":
                size = diger_bilgilers.size();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + list);
        }
        return size;
    }

    @Override
    public void onBindViewHolder(@NonNull  ListAdapter.ViewHolder holder, int position) {

        /*
        RequestConfiguration requestConfiguration = new RequestConfiguration.Builder()
                .setTestDeviceIds(Arrays.asList("EAD98D52B6934D07B9DAD189F4BACB64")).build();
        MobileAds.setRequestConfiguration(requestConfiguration);

         */

        Translator translator = new Translator();

        switch (list) {
            case "kaydedilenler":
                holder.linearLayout.setVisibility(View.GONE);
                holder.adView.setVisibility(View.VISIBLE);
                Tarihi_Yerler tarihi_yerler = yerler.get(position);
                if (tarihi_yerler == null){
                    AdRequest adRequest = new AdRequest.Builder().build();
                    if (holder.adView != null){
                        holder.adView.loadAd(adRequest);
                    }
                } else {
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    holder.adView.setVisibility(View.GONE);
                holder.time.setVisibility(View.VISIBLE);
                holder.baslik.setText(yerler.get(position).getName());

                ArrayList<String> times = tarihi_yerler.getTimes();

                ArrayList<String> images = (ArrayList<String>) tarihi_yerler.getImageUrl();
                Glide.with(mContext).load(images.get(0)).into(holder.image);

                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                Date d = new Date();
                String dayOfTheWeek = sdf.format(d);


                for (int i = 0; i < times.size(); i++) {
                    String time = times.get(i).toLowerCase();
                    if (time.matches("her zaman açık")) {
                        holder.time.setBackgroundResource(R.drawable.ic_ellipse_1);
                    } else if (time.matches("kapalı") && time.matches(dayOfTheWeek)) {
                        holder.time.setBackgroundResource(R.drawable.ic_ellipse_2);
                    } else if (time.matches(dayOfTheWeek)) {
                        holder.time.setBackgroundResource(R.drawable.ic_ellipse_1);
                    } else {
                        holder.time.setBackgroundResource(R.drawable.ic_ellipse_1);
                    }
                }

                if (tarihi_yerler.getExp().length() > 47) {
                    holder.aciklama.setText(tarihi_yerler.getExp().substring(0, 47) + "...");
                } else {
                    holder.aciklama.setText(tarihi_yerler.getExp() + "...");
                }

                holder.itemView.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    //mContext.startActivity(new Intent(mContext,DetayActivity.class));
                    intent.putExtra("name", tarihi_yerler.getName());
                    intent.putExtra("id", tarihi_yerler.getId());
                    intent.putExtra("exp", tarihi_yerler.getExp());
                    intent.putExtra("phone", tarihi_yerler.getPhone());
                    intent.putExtra("videoId", tarihi_yerler.getVideoId());
                    intent.putStringArrayListExtra("imageUrl", images);
                    intent.putStringArrayListExtra("location", tarihi_yerler.getLocation());
                    intent.putStringArrayListExtra("times", tarihi_yerler.getTimes());
                    intent.putExtra("list",list);
                    mContext.startActivity(intent);
                });

                }
                break;
            case "onemlikisiler":
                Onemli_Kisiler onemli_kisiler = onemli_kisilers.get(position);
                holder.linearLayout.setVisibility(View.GONE);
                holder.adView.setVisibility(View.VISIBLE);
                if (onemli_kisiler == null){
                    AdRequest adRequest = new AdRequest.Builder().build();
                    if (holder.adView != null){
                        holder.adView.loadAd(adRequest);
                    }
                } else {
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    holder.adView.setVisibility(View.GONE);
                holder.time.setVisibility(View.GONE);

                holder.baslik.setText(onemli_kisilers.get(position).getName());

                if (onemli_kisiler.getExp().length() > 47) {
                    holder.aciklama.setText(onemli_kisiler.getExp().substring(0, 47) + "...");
                } else {
                    holder.aciklama.setText(onemli_kisiler.getExp() + "...");
                }
                Glide.with(mContext).load(onemli_kisiler.getImage()).into(holder.image);

                holder.itemView.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("children",onemli_kisiler.getChildren());
                    intent.putExtra("exp",onemli_kisiler.getExp());
                    intent.putExtra("sibs",onemli_kisiler.getSibs());
                    intent.putExtra("id",onemli_kisiler.getId());
                    intent.putExtra("image",onemli_kisiler.getImage());
                    intent.putExtra("name",onemli_kisiler.getName());
                    intent.putExtra("list",list);
                    mContext.startActivity(intent);
                });
                }
                break;
            case "tarihiyerler":
                holder.linearLayout.setVisibility(View.GONE);
                holder.adView.setVisibility(View.VISIBLE);
                Tarihi_Yerler tarihi_yerler1 = yerler.get(position);
                if (tarihi_yerler1 == null){
                    AdRequest adRequest = new AdRequest.Builder().build();
                    if (holder.adView != null){
                        holder.adView.loadAd(adRequest);
                    }
                } else {
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    holder.adView.setVisibility(View.GONE);
                holder.time.setVisibility(View.VISIBLE);
                holder.baslik.setText(yerler.get(position).getName());

                ArrayList<String> times1 = tarihi_yerler1.getTimes();

                ArrayList<String> images1 = (ArrayList<String>) tarihi_yerler1.getImageUrl();
                Glide.with(mContext).load(images1.get(0)).into(holder.image);

                SimpleDateFormat sdf1 = new SimpleDateFormat("EEEE");
                String currentTime1 = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                Date d1 = new Date();
                String dayOfTheWeek1 = sdf1.format(d1);


                for (int i = 0; i < times1.size(); i++) {
                    String time = times1.get(i).toLowerCase();
                    if (time.matches("her zaman açık")) {
                        holder.time.setBackgroundResource(R.drawable.ic_ellipse_1);
                    } else if (time.matches("kapalı") && time.matches(dayOfTheWeek1)) {
                        holder.time.setBackgroundResource(R.drawable.ic_ellipse_2);
                    } else if (time.matches(dayOfTheWeek1)) {
                        holder.time.setBackgroundResource(R.drawable.ic_ellipse_1);
                    } else {
                        holder.time.setBackgroundResource(R.drawable.ic_ellipse_1);
                    }
                }

                if (tarihi_yerler1.getExp().length() > 47) {
                    holder.aciklama.setText(tarihi_yerler1.getExp().substring(0, 47) + "...");
                } else {
                    holder.aciklama.setText(tarihi_yerler1.getExp() + "...");
                }

                holder.itemView.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    //mContext.startActivity(new Intent(mContext,DetayActivity.class));
                    intent.putExtra("name", tarihi_yerler1.getName());
                    intent.putExtra("id", tarihi_yerler1.getId());
                    intent.putExtra("exp", tarihi_yerler1.getExp());
                    intent.putExtra("phone", tarihi_yerler1.getPhone());
                    intent.putExtra("videoId", tarihi_yerler1.getVideoId());
                    intent.putStringArrayListExtra("imageUrl", images1);
                    intent.putStringArrayListExtra("location", tarihi_yerler1.getLocation());
                    intent.putStringArrayListExtra("times", tarihi_yerler1.getTimes());
                    intent.putExtra("list",list);
                    mContext.startActivity(intent);
                });
                }
                break;
            case "digeryerler":
                holder.linearLayout.setVisibility(View.GONE);
                holder.adView.setVisibility(View.VISIBLE);
                Diger_Yerler diger_yerler = diger_yerlers.get(position);
                if (diger_yerler == null){
                    AdRequest adRequest = new AdRequest.Builder().build();
                    if (holder.adView != null){
                        holder.adView.loadAd(adRequest);
                    }
                } else {
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    holder.adView.setVisibility(View.GONE);
                holder.time.setVisibility(View.VISIBLE);
                holder.baslik.setText(diger_yerlers.get(position).getName());

                ArrayList<String> times4 = diger_yerler.getTimes();

                ArrayList<String> images4 = (ArrayList<String>) diger_yerler.getImageUrl();
                Glide.with(mContext).load(images4.get(0)).into(holder.image);

                SimpleDateFormat sdf4 = new SimpleDateFormat("EEEE");
                Date d4 = new Date();
                String dayOfTheWeek4 = sdf4.format(d4);


                for (int i = 0; i < times4.size(); i++) {
                    String time = times4.get(i).toLowerCase();
                    if (time.matches("her zaman açık")) {
                        holder.time.setBackgroundResource(R.drawable.ic_ellipse_1);
                    } else if (time.matches("kapalı") && time.matches(dayOfTheWeek4)) {
                        holder.time.setBackgroundResource(R.drawable.ic_ellipse_2);
                    } else if (time.matches(dayOfTheWeek4)) {
                        holder.time.setBackgroundResource(R.drawable.ic_ellipse_1);
                    } else {
                        holder.time.setBackgroundResource(R.drawable.ic_ellipse_1);
                    }
                }

                if (diger_yerler.getExp().length() > 47) {
                    holder.aciklama.setText(diger_yerler.getExp().substring(0, 47) + "...");
                } else {
                    holder.aciklama.setText(diger_yerler.getExp() + "...");
                }

                holder.itemView.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    //mContext.startActivity(new Intent(mContext,DetayActivity.class));
                    intent.putExtra("name", diger_yerler.getName());
                    intent.putExtra("id", diger_yerler.getId());
                    intent.putExtra("exp", diger_yerler.getExp());
                    intent.putExtra("phone", diger_yerler.getPhone());
                    intent.putExtra("videoId", diger_yerler.getVideoId());
                    intent.putStringArrayListExtra("imageUrl", images4);
                    intent.putExtra("list",list);
                    //intent.putStringArrayListExtra("location", diger_yerler.getLocation());
                    intent.putStringArrayListExtra("times", diger_yerler.getTimes());
                    mContext.startActivity(intent);
                });
                }
                break;
            case "digerbilgiler":
                holder.linearLayout.setVisibility(View.GONE);
                holder.adView.setVisibility(View.VISIBLE);
                Diger_Bilgiler digerBilgiler = diger_bilgilers.get(position);
                if (digerBilgiler == null){
                    AdRequest adRequest = new AdRequest.Builder().build();
                    if (holder.adView != null){
                        holder.adView.loadAd(adRequest);
                    }
                } else {
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    holder.adView.setVisibility(View.GONE);
                    holder.time.setVisibility(View.GONE);
                    holder.baslik.setText(diger_bilgilers.get(position).getName());

                    if (digerBilgiler.getExp().length() > 47) {
                        holder.aciklama.setText(digerBilgiler.getExp().substring(0, 47) + "...");
                    } else {
                        holder.aciklama.setText(digerBilgiler.getExp() + "...");
                    }

                    Glide.with(mContext).load(digerBilgiler.getImage()).into(holder.image);

                    holder.itemView.setOnClickListener(view -> {
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("image", digerBilgiler.getImage());
                        intent.putExtra("exp", digerBilgiler.getExp());
                        intent.putExtra("name", digerBilgiler.getName());
                        intent.putExtra("id", digerBilgiler.getId());
                        intent.putExtra("list", list);
                        mContext.startActivity(intent);
                    });
                }
                break;
        }
        translator.translate(holder.baslik,holder.baslik.getText().toString(),null);
        translator.translateDetails(holder.aciklama,holder.aciklama.getText().toString(),null);
    }
    /*
    @Override
    public Filter getFilter() {
        return filter;
    }
     */


    /*
    @Override
    protected void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position, @NonNull Tarihi_Yerler model) {
        final Tarihi_Yerler tarihi_yerler = yerler.get(position);
        holder.baslik.setText(tarihi_yerler.getName());
        holder.itemView.setOnClickListener(view -> {
            Toast.makeText(mContext, tarihi_yerler.getName(), Toast.LENGTH_SHORT).show();
        });
    }
     */

    /*
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Tarihi_Yerler> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(searchList);
            } else {
                for (Tarihi_Yerler tarihi_yerler : searchList) {
                    if (tarihi_yerler.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(tarihi_yerler);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }
        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            yerler.clear();
            yerler.addAll((Collection<? extends Tarihi_Yerler>) filterResults.values);
            notifyDataSetChanged();
        }
    };
     */

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView baslik;
        ImageView image;
        ImageView time;
        TextView aciklama;
        LinearLayout linearLayout;
        AdView adView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            baslik = itemView.findViewById(R.id.baslik);
            image = itemView.findViewById(R.id.image);
            time = itemView.findViewById(R.id.time);
            aciklama = itemView.findViewById(R.id.aciklama);
            adView = itemView.findViewById(R.id.adView);
            linearLayout = itemView.findViewById(R.id.cardview);
        }
    }

}