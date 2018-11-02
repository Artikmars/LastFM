package com.artamonov.lastfm.contract;

import android.app.Activity;

import com.artamonov.lastfm.model.artists.Results;

public interface ArtistsContract {

    interface ArtistsPresenter {

        void hideKeyboard(Activity activity);

        //  void getVideoList(List<Video> videoList);
    }

    interface ArtistsView {

        void showProgressDialog();

        void dismissProgressDialog();

        void setArtistsAdapter(Results response);

        void showFailureMessage(Throwable t);
    }

}

