package com.example.comicvine;

import android.widget.ImageView;

public class MovieDataModel {
    private ImageView mainImagePoster;
    private String movieName;
    private String movieDeck;

    public MovieDataModel(ImageView mainImagePoster, String movieName, String movieDeck) {
        this.mainImagePoster = mainImagePoster;
        this.movieName = movieName;
        this.movieDeck = movieDeck;
    }

    public ImageView getMainImagePoster() {
        return mainImagePoster;
    }

    public void setMainImagePoster(ImageView mainImagePoster) {
        this.mainImagePoster = mainImagePoster;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDeck() {
        return movieDeck;
    }

    public void setMovieDeck(String movieDeck) {
        this.movieDeck = movieDeck;
    }
}
