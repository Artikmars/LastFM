package com.artamonov.lastfm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artamonov.lastfm.R;
import com.artamonov.lastfm.ArtistsSearchActivity;
import com.artamonov.lastfm.TopAlbumsActivity;
import com.artamonov.lastfm.model.artists.Artist;
import com.artamonov.lastfm.model.artists.Image;
import com.artamonov.lastfm.utils.Formatter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ViewHolder> {

    private final List<Artist> artistsList;
    private final Context context;
   // private static ItemClickListener listener;

    public ArtistsAdapter(List<Artist> artistsList, Context context) {
        this.artistsList = artistsList;
        this.context = context;
      //  listener = itemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.artist_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final Artist artistItem = artistsList.get(position);
        viewHolder.artistName.setText(artistItem.getName());
        viewHolder.artistListeners.setText(Formatter.formatPlayCount(artistItem.getListeners()));

        final Image imageItem = artistItem.getImage().get(2);
        String imageURL = imageItem.getText();
        Log.i(ArtistsSearchActivity.TAG, "imageURL : " + imageURL);
       if (!TextUtils.isEmpty(imageURL)) {
            Picasso.get()
                    .load(imageURL)
                   // .placeholder(R.drawable.placeholder)
                   // .error(R.drawable.placeholder_error)
                    .into(viewHolder.artistThumbnail);
        } else {
                Log.i(ArtistsSearchActivity.TAG, "imageURL is Empty");
        }

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String artistName = artistItem.getName();
                Intent intent = new Intent(context, TopAlbumsActivity.class);
                intent.putExtra("artistName", artistName);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return artistsList.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView artistName;
        private final TextView artistListeners;
        private final ImageView artistThumbnail;
        private final LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            artistName = itemView.findViewById(R.id.artist_name);
            artistListeners = itemView.findViewById(R.id.artist_listeners);
            artistThumbnail = itemView.findViewById(R.id.artist_thumbnail);
            linearLayout = itemView.findViewById(R.id.linear_layout_artist);
           // itemView.setOnClickListener(this);
        }
    }
}
