package com.artamonov.lastfm.network;

import com.artamonov.lastfm.model.artists.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LastFMApiInterface {

    @GET("/2.0/?method=artist.search&api_key=YOUR_API_KEY&format=json")
    Call<Response> getArtists(@Query("artist") String artistName);

    @GET("/2.0/?method=artist.gettopalbums&api_key=YOUR_API_KEY&format=json")
    Call<com.artamonov.lastfm.model.topAlbums.Response> getTopAlbums(@Query("artist") String artistName);

    @GET("/2.0/?method=album.getinfo&api_key=YOUR_API_KEY&format=json")
    Call<com.artamonov.lastfm.model.albumDetail.Response> getAlbum(@Query("artist") String artistName,
                                                                   @Query("album") String albumName);
}
