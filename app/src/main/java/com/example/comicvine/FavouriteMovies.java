package com.example.comicvine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMovies extends AppCompatActivity {

    private ListView favListView;
    private ArrayList<MovieDataModel> dataModels;
    private static MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);
        dataModels = new ArrayList<>();
        favListView = findViewById(R.id.favoriteMoviesList);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(FavouriteMovies.this);
        List<MovieModel> favouriteMovies = dataBaseHelper.getAllFavourites();
        Toast.makeText(FavouriteMovies.this,favouriteMovies.toString(),Toast.LENGTH_LONG).show();

        /*if (!favouriteMovies.isEmpty()) {
            //LIST IS FILLED
            for (int i = 0; i < favouriteMovies.size(); i++) {
                dataModels.add(new MovieDataModel(convertToImage(moviesPicsList.get(i)), moviesNamesList.get(i), moviesDeckList.get(i)));
            }
            adapter = new MovieAdapter(dataModels, getApplicationContext());
            favListView.setAdapter(adapter);
        }*/
    }
}