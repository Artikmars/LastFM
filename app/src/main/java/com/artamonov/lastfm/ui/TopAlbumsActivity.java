package com.artamonov.lastfm.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.artamonov.lastfm.R;
import com.artamonov.lastfm.adapter.AlbumsAdapter;
import com.artamonov.lastfm.model.topAlbums.Album;
import com.artamonov.lastfm.model.topAlbums.Topalbums;
import com.artamonov.lastfm.network.LastFMApiInterface;
import com.artamonov.lastfm.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class TopAlbumsActivity extends AppCompatActivity  {

    public static final String TAG = "myLogs";

    private RecyclerView rvArtists;
    private Topalbums albumItem;
    private List<Album> albumsItemList = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        rvArtists = findViewById(R.id.rv_artists);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvArtists.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvArtists.addItemDecoration(itemDecoration);

        Intent intent = getIntent();
        getTopAlbums(intent.getStringExtra("artistName"));
    }

    private void getTopAlbums(final String artistName) {
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        LastFMApiInterface service = retrofit.create(LastFMApiInterface.class);

        service.getTopAlbums(artistName).enqueue(new Callback<com.artamonov.lastfm.model.topAlbums.Response>() {
            @Override
            public void onResponse(Call<com.artamonov.lastfm.model.topAlbums.Response> call, retrofit2.Response<com.artamonov.lastfm.model.topAlbums.Response> response) {
                progressDialog.dismiss();
                albumItem = response.body().getTopalbums();
                albumsItemList = albumItem.getAlbum();
                AlbumsAdapter albumsAdapter = new AlbumsAdapter(albumsItemList, TopAlbumsActivity.this);
                rvArtists.setAdapter(albumsAdapter);
                rvArtists.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<com.artamonov.lastfm.model.topAlbums.Response> call, Throwable t) {
                System.out.println("onFailure");
                System.out.println(t.getMessage());
                System.out.println(t.getStackTrace());
                progressDialog.dismiss();
            }
        });
    }
}
