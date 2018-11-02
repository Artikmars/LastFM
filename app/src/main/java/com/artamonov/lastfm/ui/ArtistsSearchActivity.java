package com.artamonov.lastfm.ui;

import android.app.Activity;
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
import android.view.Menu;
import android.view.MenuItem;

import com.artamonov.lastfm.R;
import com.artamonov.lastfm.adapter.ArtistsAdapter;
import com.artamonov.lastfm.contract.ArtistsContract;
import com.artamonov.lastfm.model.artists.Artist;
import com.artamonov.lastfm.model.artists.Results;
import com.artamonov.lastfm.presenter.ArtistsPresenter;

import java.util.ArrayList;
import java.util.List;

public class ArtistsSearchActivity extends AppCompatActivity implements ArtistsContract.ArtistsView {

    public static final String TAG = "myLogs";
    Activity activity = new Activity();
    private RecyclerView rvArtists;
    private Results artistItem;
    private List<Artist> artistItemList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private ArtistsPresenter artistsPresenter;

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
                artistsPresenter.hideKeyboard(activity);
                System.out.println("onQueryTextSubmit");
                artistsPresenter.getArtists(artistName);
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
        artistsPresenter = new ArtistsPresenter(this);
        progressDialog = new ProgressDialog(this);

        rvArtists = findViewById(R.id.rv_artists);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvArtists.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvArtists.addItemDecoration(itemDecoration);
    }

    @Override
    public void setArtistsAdapter(Results response) {
        artistItemList = response.getArtistmatches().getArtist();
        ArtistsAdapter artistsAdapter = new ArtistsAdapter(artistItemList, ArtistsSearchActivity.this);
        rvArtists.setAdapter(artistsAdapter);
        rvArtists.setHasFixedSize(true);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.setMessage("Loading...");
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    public void showFailureMessage(Throwable t) {
        System.out.println("onFailure");
        System.out.println(t.getMessage());
        System.out.println(t.getStackTrace());
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }
}
