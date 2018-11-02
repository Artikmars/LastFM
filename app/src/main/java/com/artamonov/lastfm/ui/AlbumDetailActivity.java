package com.artamonov.lastfm.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.artamonov.lastfm.R;
import com.artamonov.lastfm.adapter.TracksAdapter;
import com.artamonov.lastfm.model.topAlbums.Album;
import com.artamonov.lastfm.network.LastFMApiInterface;
import com.artamonov.lastfm.network.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class AlbumDetailActivity extends AppCompatActivity {

    public static final String TAG = "myLogs";
    @BindView(R.id.tv_album_name)
    TextView tvAlbumName;
    @BindView(R.id.tv_artist_name)
    TextView tvArtistName;
    @BindView(R.id.tv_album_listeners_amount)
    TextView tvAlbumPlayCount;
    @BindView(R.id.iv_album_poster)
    ImageView ivAlbumThumbnail;
    @BindView(R.id.switch_favorite)
    Switch sFavorite;
    private RecyclerView rvArtists;
    private com.artamonov.lastfm.model.albumDetail.Album albumItem;
    private List<Album> albumsItemList = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_detail);
        ButterKnife.bind(this);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        rvArtists = findViewById(R.id.rv_tracks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvArtists.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvArtists.addItemDecoration(itemDecoration);

        Intent intent = getIntent();
        getAlbum(intent.getStringExtra("artistName"), intent.getStringExtra("albumName"));

    }

    private void getAlbum(String artistName, final String albumName) {
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        LastFMApiInterface service = retrofit.create(LastFMApiInterface.class);
        service.getAlbum(artistName, albumName).enqueue(new Callback<com.artamonov.lastfm.model.albumDetail.Response>() {
            @Override
            public void onResponse(Call<com.artamonov.lastfm.model.albumDetail.Response> call, retrofit2.Response<com.artamonov.lastfm.model.albumDetail.Response> response) {
                albumItem = response.body().getAlbum();
                progressDialog.dismiss();
                tvAlbumName.setText(albumItem.getName());
                tvArtistName.setText(albumItem.getArtist());
                tvAlbumPlayCount.setText(com.artamonov.lastfm.utils.Formatter.formatPlayCount(albumItem.getPlaycount()));

                com.artamonov.lastfm.model.albumDetail.Image imageItem = albumItem.getImage().get(2);
                String imageURL = imageItem.getText();
                Log.i(ArtistsSearchActivity.TAG, "imageURL : " + imageURL);
                if (!TextUtils.isEmpty(imageURL)) {
                    Picasso.get()
                            .load(imageURL)
                            .into(ivAlbumThumbnail);
                } else {
                    Log.i(ArtistsSearchActivity.TAG, "imageURL is Empty");
                }
                TracksAdapter tracksAdapter = new TracksAdapter(albumItem.getTracks().getTrack(), AlbumDetailActivity.this);
                rvArtists.setAdapter(tracksAdapter);
                rvArtists.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<com.artamonov.lastfm.model.albumDetail.Response> call, Throwable t) {
                System.out.println("onFailure");
                System.out.println(t.getMessage());
                System.out.println(t.getStackTrace());
                progressDialog.dismiss();
            }
        });
    }

}
