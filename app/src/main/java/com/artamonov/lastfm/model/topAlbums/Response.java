
package com.artamonov.lastfm.model.topAlbums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("topalbums")
    @Expose
    private Topalbums topalbums;

    public Topalbums getTopalbums() {
        return topalbums;
    }

    public void setTopalbums(Topalbums topalbums) {
        this.topalbums = topalbums;
    }

}
