package com.example.aluthra.hw05;

import java.io.Serializable;

/**
 * Created by aluthra on 10/9/2017.
 */

public class MusicTrack implements Serializable{
    String name, artist, url, smallImageUrl, largeImageUrl;

    @Override
    public String toString() {
        return this.name.toString();
    }

    public MusicTrack(String name, String artist, String url, String smallImageUrl, String largeImageUrl) {
        this.name = name;
        this.artist = artist;
        this.url = url;
        this.smallImageUrl = smallImageUrl;
        this.largeImageUrl = largeImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }
}
