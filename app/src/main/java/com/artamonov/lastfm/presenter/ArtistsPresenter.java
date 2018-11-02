package com.artamonov.lastfm.presenter;

import android.app.Activity;

import com.artamonov.lastfm.contract.ArtistsContract;
import com.artamonov.lastfm.model.artists.Results;
import com.artamonov.lastfm.network.RetrofitRunner;
import com.artamonov.lastfm.utils.KeyboardUtils;

public class ArtistsPresenter implements ArtistsContract.ArtistsPresenter {

    private final ArtistsContract.ArtistsView view;

    public ArtistsPresenter(ArtistsContract.ArtistsView view) {
        this.view = view;
    }

    public void getArtists(String artistName) {
        view.showProgressDialog();
        RetrofitRunner.getArtists(artistName, this);
    }

    public void setArtistsList(Results artistsList) {
        view.dismissProgressDialog();
        view.setArtistsAdapter(artistsList);

    }

    public void setErrorType(Throwable t) {
        view.showFailureMessage(t);
        view.dismissProgressDialog();

    }

    public void hideKeyboard(Activity activity) {
        KeyboardUtils.hideKeyboard(activity);
    }
}

