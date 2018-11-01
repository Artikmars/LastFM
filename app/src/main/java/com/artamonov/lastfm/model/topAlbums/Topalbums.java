
package com.artamonov.lastfm.model.topAlbums;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Topalbums {

    @SerializedName("album")
    @Expose
    private List<Album> album = new ArrayList<Album>();
    @SerializedName("@attr")
    @Expose
    private Attr attr;

    public List<Album> getAlbum() {
        return album;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

}
