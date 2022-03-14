package com.agency11.sogutapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.agency11.sogutapp.R;
import com.agency11.sogutapp.activity.DetailActivity;
import com.agency11.sogutapp.activity.MainActivity;
import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    Context mContext;
    ArrayList<String> list;
    View view;

    public VideoAdapter(Context mContext, ArrayList<String> list){
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(mContext).inflate(R.layout.video_item,parent,false);
        return new VideoAdapter.ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder holder, int position) {

        ((DetailActivity) mContext).addLifeCycleCallBack(holder.youTubePlayerView);

        holder.youTubePlayerView.addYouTubePlayerListener(new  AbstractYouTubePlayerListener(){
            @Override
            public  void  onReady ( @NonNull YouTubePlayer youTubePlayer ) {
                String videoId =  list.get(position);
                youTubePlayer.loadVideo(videoId, 0 );
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        YouTubePlayerView youTubePlayerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);
        }
    }

    public interface AddLifecycleCallbackListener {
        void addLifeCycleCallBack(YouTubePlayerView youTubePlayerView);
    }
}
