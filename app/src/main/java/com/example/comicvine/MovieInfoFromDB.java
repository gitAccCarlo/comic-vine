package com.example.comicvine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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

public class MovieInfoFromDB extends AppCompatActivity {

    private int movieId=1;
    private String name;
    private String description;
    private String rating;
    private TextView txMovieName;
    private TextView txMovieDescription;
    private TextView txMovieRating;
    private Button btRemove;
    private ImageView imPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info_from_d_b);
        Intent intent = getIntent();
        imPoster = findViewById(R.id.imageViewMovieDetailPosterDBNOT);
        txMovieName = findViewById(R.id.textViewMovieNameDB);
        txMovieDescription = findViewById(R.id.textViewMovieDescriptionDB);
        txMovieRating = findViewById(R.id.textViewMovieRatingDB);
        btRemove = findViewById(R.id.buttonRemoveMovie);
        movieId = Integer.parseInt(intent.getStringExtra("idMovie"));
        name = intent.getStringExtra("nameMovie");
        description = intent.getStringExtra("descriptionMovie");
        rating = intent.getStringExtra("ratingMovie");

        txMovieName.setText(name);
        txMovieDescription.setText(description);
        txMovieRating.setText(rating);

        RequestQueue queue = Volley.newRequestQueue(MovieInfoFromDB.this);
        String auxUrl2 = "https://comicvine.gamespot.com/api/movies/?api_key=abd2bd9158ea401d671579e918e7394cd55a1a87&format=json&filter=id:"+ movieId;
        final JsonObjectRequest movieInfoRequest = new JsonObjectRequest(Request.Method.GET,
                auxUrl2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("moviesAPI", response.toString());
                        try {
                            String auxPic=response.getJSONArray("results").getJSONObject(0).getJSONObject("image").getString("small_url");
                            BitmapDrawable drawable = (BitmapDrawable) convertToImage(auxPic).getDrawable();
                            Bitmap bitmap = drawable.getBitmap();
                            imPoster.setImageBitmap(bitmap);
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                        if (response.has("error")) {
                            try {
                                Toast.makeText(MovieInfoFromDB.this, response.getString("error"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MovieInfoFromDB.this, "Cambios guardados", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TreeAPI", "Error en la invocación a la API " + error.getCause());
                Toast.makeText(MovieInfoFromDB.this, "Se presentó un error, por favor intente más tarde", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(movieInfoRequest);


        btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MovieInfoFromDB.this);
                boolean success = dataBaseHelper.deleteOne(String.valueOf(movieId));
                Toast.makeText(MovieInfoFromDB.this, "Successfully removed from local DB",Toast.LENGTH_SHORT).show();
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