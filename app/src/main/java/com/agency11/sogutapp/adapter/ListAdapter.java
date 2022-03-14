package com.agency11.sogutapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agency11.sogutapp.R;
import com.agency11.sogutapp.activity.DetailActivity;
import com.agency11.sogutapp.model.Tarihi_Yerler;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.GeoPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Tarihi_Yerler> yerler;
    private ArrayList<Tarihi_Yerler> searchList;
    boolean time;
    private View view;
    ShimmerFrameLayout shimmerFrameLayout;
    FirestoreRecyclerOptions<Tarihi_Yerler> tarihi_yerlerFirestoreRecyclerOptions;


    public ListAdapter(Context mContext, ArrayList<Tarihi_Yerler> yerler, boolean time){
        this.mContext = mContext;
        this.yerler = yerler;
        this.time = time;
        this.searchList = new ArrayList<>(yerler);
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return yerler.size();
    }


    @Override
    public void onBindViewHolder(@NonNull  ListAdapter.ViewHolder holder, int position) {
        final Tarihi_Yerler tarihi_yerler = yerler.get(position);
        holder.baslik.setText(yerler.get(position).getName());

        ArrayList<String> times = tarihi_yerler.getTimes();

        ArrayList<String> images = (ArrayList<String>) tarihi_yerler.getImageUrl();
        Glide.with(mContext).load(images.get(0)).into(holder.image);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        System.out.println(dayOfTheWeek);



        for (int i=0; i < times.size(); i++){
            String time = times.get(i).toLowerCase();
            if (time.matches("her zaman açık")){
                holder.time.setBackgroundResource(R.drawable.ic_ellipse_1);
            } else if (time.matches("kapalı") && time.matches(dayOfTheWeek)){
                holder.time.setBackgroundResource(R.drawable.ic_ellipse_2);
            } else if (time.matches(dayOfTheWeek) ){
                holder.time.setBackgroundResource(R.drawable.ic_ellipse_1);
            } else {
                holder.time.setBackgroundResource(R.drawable.ic_ellipse_1);
            }
        }

        if (tarihi_yerler.getExp().length() > 47){
            holder.aciklama.setText(tarihi_yerler.getExp().substring(0,47) +"...");
        } else {
            holder.aciklama.setText(tarihi_yerler.getExp() +"...");
        }


        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, DetailActivity.class);
            //mContext.startActivity(new Intent(mContext,DetayActivity.class));
            intent.putExtra("name", tarihi_yerler.getName());
            intent.putExtra("id",tarihi_yerler.getId());
            intent.putExtra("exp", tarihi_yerler.getExp());
            intent.putExtra("phone",tarihi_yerler.getPhone());
            intent.putExtra("videoId",tarihi_yerler.getVideoId());
            intent.putStringArrayListExtra("imageUrl",images);
            intent.putStringArrayListExtra("location",tarihi_yerler.getLocation());
            intent.putStringArrayListExtra("times",tarihi_yerler.getTimes());
            mContext.startActivity(intent);
        });
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            baslik = itemView.findViewById(R.id.baslik);
            image = itemView.findViewById(R.id.image);
            time = itemView.findViewById(R.id.time);
            aciklama = itemView.findViewById(R.id.aciklama);
        }
    }

}
