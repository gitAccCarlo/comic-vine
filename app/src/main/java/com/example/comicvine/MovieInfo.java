package com.example.comicvine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MovieInfo extends AppCompatActivity {

    private int movieId=1;
    private String name;
    private String description;
    private String rating;
    private TextView txMovieName;
    private TextView txMovieDescription;
    private TextView txMovieRating;
    private ImageView imPoster;
    private Button btnFavMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        Intent intent = getIntent();
        imPoster = findViewById(R.id.imageViewMovieDetailPoster);
        txMovieName = findViewById(R.id.textViewMovieName);
        txMovieDescription = findViewById(R.id.textViewMovieDescription);
        txMovieRating = findViewById(R.id.textViewMovieRating);
        btnFavMovie = findViewById(R.id.buttonAddFavorite);
        movieId = Integer.parseInt(intent.getStringExtra("idMovie"));

        RequestQueue queue = Volley.newRequestQueue(MovieInfo.this);
        String auxUrl2 = "https://comicvine.gamespot.com/api/movies/?api_key=abd2bd9158ea401d671579e918e7394cd55a1a87&format=json&filter=id:"+ movieId;
        final JsonObjectRequest movieInfoRequest = new JsonObjectRequest(Request.Method.GET,
                auxUrl2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("moviesAPI", response.toString());
                        try {
                            System.out.println(response.getString("results"));

                            name=response.getJSONArray("results").getJSONObject(0).get("name").toString();
                            description=response.getJSONArray("results").getJSONObject(0).get("description").toString();
                            rating=response.getJSONArray("results").getJSONObject(0).get("rating").toString();
                            String auxPic=response.getJSONArray("results").getJSONObject(0).getJSONObject("image").getString("small_url");

                            txMovieName.setText(name);
                            txMovieDescription.setText(description);
                            txMovieRating.setText(rating);

                            BitmapDrawable drawable = (BitmapDrawable) convertToImage(auxPic).getDrawable();
                            Bitmap bitmap = drawable.getBitmap();

                            imPoster.setImageBitmap(bitmap);
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                        if (response.has("error")) {
                            try {
                                Toast.makeText(MovieInfo.this, response.getString("error"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MovieInfo.this, "Cambios guardados", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TreeAPI", "Error en la invocación a la API " + error.getCause());
                Toast.makeText(MovieInfo.this, "Se presentó un error, por favor intente más tarde", Toast.LENGTH_SHORT).show();
            }
        }) {    //this is the part, that adds the header to the request

        };
        queue.add(movieInfoRequest);



        btnFavMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieModel movieModel;
                try{
                    movieModel = new MovieModel(movieId, String.valueOf(txMovieName.getText()), String.valueOf(txMovieDescription.getText()), String.valueOf(txMovieRating.getText()));
                    Toast.makeText(MovieInfo.this, "Movie added to favourites",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(MovieInfo.this, "Failed to add movie",Toast.LENGTH_SHORT).show();
                    movieModel = new MovieModel(-1,"error","error","error");
                }
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MovieInfo.this);
                boolean success = dataBaseHelper.addOne(movieModel);
                Toast.makeText(MovieInfo.this, "Successfully added to local DB",Toast.LENGTH_SHORT).show();
            }
        });

    }


    private ImageView convertToImage(String urlIcon) throws IOException {
        ImageView imView=new ImageView(getBaseContext());
        URL url = new URL(urlIcon);
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        imView.setImageBitmap(bmp);
        return imView;
    }

}