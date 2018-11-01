package com.artamonov.lastfm;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.artamonov.lastfm.adapter.ArtistsAdapter;
import com.artamonov.lastfm.model.artists.Artist;
import com.artamonov.lastfm.model.artists.Results;
import com.artamonov.lastfm.network.LastFMApiInterface;
import com.artamonov.lastfm.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ArtistsSearchActivity extends AppCompatActivity {

    public static final String TAG = "myLogs";

    private RecyclerView rvArtists;
    private Results artistItem;
    private List<Artist> artistItemList = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_menu);

        SearchManager searchManager = (SearchManager) ArtistsSearchActivity.this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(ArtistsSearchActivity.this.getComponentName()));
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String artistName) {
                hideKeyboard();
                System.out.println("onQueryTextSubmit");
                getArtists(artistName);
                progressDialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

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
        rvArtists = findViewById(R.id.rv_artists);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvArtists.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvArtists.addItemDecoration(itemDecoration);
    }

    private void getArtists(final String artistName) {
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        LastFMApiInterface service = retrofit.create(LastFMApiInterface.class);

        service.getArtists(artistName).enqueue(new Callback<com.artamonov.lastfm.model.artists.Response>() {
            @Override
            public void onResponse(Call<com.artamonov.lastfm.model.artists.Response> call, Response<com.artamonov.lastfm.model.artists.Response> response) {
                progressDialog.dismiss();
                artistItem = response.body().getResults();
                artistItemList = artistItem.getArtistmatches().getArtist();
                ArtistsAdapter artistsAdapter = new ArtistsAdapter(artistItemList, ArtistsSearchActivity.this);
                rvArtists.setAdapter(artistsAdapter);
                rvArtists.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<com.artamonov.lastfm.model.artists.Response> call, Throwable t) {
                System.out.println("onFailure");
                System.out.println(t.getMessage());
                System.out.println(t.getStackTrace());
                progressDialog.dismiss();
            }
        });
    }

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }
}
