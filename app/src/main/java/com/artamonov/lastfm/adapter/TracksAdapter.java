package com.artamonov.lastfm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artamonov.lastfm.R;
import com.artamonov.lastfm.model.albumDetail.Track;

import java.util.List;

import static com.artamonov.lastfm.AlbumDetailActivity.TAG;

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.ViewHolder> {

    private final List<Track> trackList;
    private final Context context;

    public TracksAdapter(List<Track> albumsList, Context context) {
        this.trackList = albumsList;
        this.context = context;
    }


    @NonNull
    @Override
    public TracksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.track_item, viewGroup, false);
        return new TracksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TracksAdapter.ViewHolder viewHolder, int position) {
        Track trackItem = trackList.get(position);
        Log.i(TAG, "Duration in sec:" + trackItem.getDuration());
        viewHolder.trackName.setText(trackItem.getName());
        viewHolder.trackDuration.setText(secondsToMinutes(trackItem.getDuration()));
        Log.i(TAG, "Duration in min:" + secondsToMinutes(trackItem.getDuration()));
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    private String secondsToMinutes(String seconds) {

        if (seconds.equals("0")) {
            return "0:00";
        }
        Integer minutes = Integer.parseInt(seconds) / 60;
        Integer rest = Integer.parseInt(seconds) % 60;
        if (rest == 0) {
            return minutes + ":00";
        }

        if (rest < 10) {
            return minutes + ":0" + rest;
        }
        return minutes + ":" + rest;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView trackName;
        private final TextView trackDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trackName = itemView.findViewById(R.id.track_name);
            trackDuration = itemView.findViewById(R.id.track_duration);
        }

    }
}
