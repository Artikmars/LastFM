package com.artamonov.lastfm.network;

import com.artamonov.lastfm.model.artists.Response;
import com.artamonov.lastfm.model.artists.Results;
import com.artamonov.lastfm.presenter.ArtistsPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class RetrofitRunner {

    public static void getArtists(final String artistName, final ArtistsPresenter artistsPresenter) {

        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        LastFMApiInterface service = retrofit.create(LastFMApiInterface.class);
        service.getArtists(artistName).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Results artistList = response.body().getResults();
                artistsPresenter.setArtistsList(artistList);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                artistsPresenter.setErrorType(t);
            }
        });

    }
}
