package com.example.comicvine;

public class MovieDBModel {

    private int id;
    private String movieName;
    private String movieDescription;
    private String movieRating;

    public MovieDBModel() {
    }

    public MovieDBModel(int id, String movieName, String movieDescription, String movieRating) {
        this.id = id;
        this.movieName = movieName;
        this.movieDescription = movieDescription;
        this.movieRating = movieRating;
    }

    @Override
    public String toString() {
        return "MovieDBModel{" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", movieDescription='" + movieDescription + '\'' +
                ", movieStudio='" + movieRating + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }
}
