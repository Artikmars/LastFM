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
import com.artamonov.lastfm.model.topAlbums.Album;
import com.artamonov.lastfm.ui.AlbumDetailActivity;
import com.artamonov.lastfm.ui.ArtistsSearchActivity;
import com.artamonov.lastfm.utils.Formatter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {

    private final List<Album> albumList;
    private final Context context;
    // private static ItemClickListener listener;

    public AlbumsAdapter(List<Album> albumsList, Context context) {
        this.albumList = albumsList;
        this.context = context;
        //  listener = itemClickListener;
    }


    @NonNull
    @Override
    public AlbumsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.album_item, viewGroup, false);
        return new AlbumsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Album albumItem = albumList.get(position);
        viewHolder.albumName.setText(albumItem.getName());
        viewHolder.albumPlaycount.setText(Formatter.formatPlayCount(albumItem.getPlaycount()));

        com.artamonov.lastfm.model.topAlbums.Image imageItem = albumItem.getImage().get(2);
        String imageURL = imageItem.getText();
        Log.i(ArtistsSearchActivity.TAG, "imageURL : " + imageURL);
        if (!TextUtils.isEmpty(imageURL)) {
            Picasso.get()
                    .load(imageURL)
                    .into(viewHolder.albumThumbnail);
        } else {
            Log.i(ArtistsSearchActivity.TAG, "imageURL is Empty");
        }

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String albumItemName = albumItem.getName();
                String artistItemName = albumItem.getArtist().getName();
                Intent intent = new Intent(context, AlbumDetailActivity.class);
                intent.putExtra("artistName", artistItemName);
                intent.putExtra("albumName", albumItemName);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView albumName;
        private final TextView albumPlaycount;
        private final ImageView albumThumbnail;
        private final ImageView ivPlayCounter;
        private final LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            albumName = itemView.findViewById(R.id.album_name);
            albumPlaycount = itemView.findViewById(R.id.album_listeners);
            albumThumbnail = itemView.findViewById(R.id.album_thumbnail);
            ivPlayCounter = itemView.findViewById(R.id.iv_play_counter);
            linearLayout = itemView.findViewById(R.id.linear_layout_albums);
            // itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            //  listener.onItemClick(position);
        }
    }
}
