package com.sgp.beats.models;

public class Songs {
    private String album_pic;
    private String artist;
    private String url;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum_pic() {
        return album_pic;
    }

    public void setAlbum_pic(String album_pic) {
        this.album_pic = album_pic;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
