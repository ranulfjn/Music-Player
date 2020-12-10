package com.isi.songify;

import android.graphics.Bitmap;

public class CustomArrayList {

    int id;
    Bitmap image;
    String songTitle;
    String Artist;

    public CustomArrayList(int id, Bitmap image, String songTitle, String artist) {
        this.id = id;
        this.image = image;
        this.songTitle = songTitle;
        Artist = artist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }
}
