package com.example.comicvine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMovies extends AppCompatActivity {

    private ListView favListView;
    private ArrayList<MovieDataModel> dataModels;
    private ArrayList<Integer> movieId;
    private ArrayList<String> movieName;
    private ArrayList<String> movieDescription;
    private ArrayList<String> movieRating;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);
        dataModels = new ArrayList<>();
        movieId = new ArrayList<>();
        movieName = new ArrayList<>();
        movieDescription = new ArrayList<>();
        movieRating = new ArrayList<>();
        favListView = findViewById(R.id.favoriteMoviesList);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(FavouriteMovies.this);
        List<MovieDBModel> favouriteMovies = dataBaseHelper.getAllFavourites();
        //Toast.makeText(FavouriteMovies.this,favouriteMovies.toString(),Toast.LENGTH_LONG).show();

        if (!favouriteMovies.isEmpty()) {
            for (int i = 0; i < favouriteMovies.size(); i++) {
                movieId.add(favouriteMovies.get(i).getId());
                movieName.add(favouriteMovies.get(i).getMovieName());
                movieDescription.add(favouriteMovies.get(i).getMovieDescription());
                movieRating.add(favouriteMovies.get(i).getMovieRating());
            }
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,movieName);
            favListView.setAdapter(adapter);
        }else{
            Toast.makeText(FavouriteMovies.this,"Add a favourite movie first",Toast.LENGTH_LONG).show();
        }

        favListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FavouriteMovies.this,MovieInfoFromDB.class);
                intent.putExtra("idMovie",String.valueOf(movieId.get(i)));
                intent.putExtra("nameMovie",String.valueOf(movieName.get(i)));
                intent.putExtra("descriptionMovie",String.valueOf(movieDescription.get(i)));
                intent.putExtra("ratingMovie",String.valueOf(movieRating.get(i)));

                startActivity(intent);
            }
        });
    }
}