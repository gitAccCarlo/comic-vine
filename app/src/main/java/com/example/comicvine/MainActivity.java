package com.example.comicvine;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton favMoviesFAButton;
    private ListView listView;
    private ArrayList<String> moviesIdsList;
    private ArrayList<String> moviesNamesList;
    private ArrayList<String> moviesPicsList;
    private ArrayList<String> moviesDeckList;
    private ArrayList<MovieDataModel> dataModels;
    private static MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviesIdsList = new ArrayList<>();
        moviesNamesList = new ArrayList<>();
        moviesPicsList = new ArrayList<>();
        moviesDeckList = new ArrayList<>();
        dataModels = new ArrayList<>();
        listView = findViewById(R.id.fullMoviesList);
        favMoviesFAButton = findViewById(R.id.floatingActionButton);
        final LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        loadingDialog.startLoadingDialog();
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String auxUrl = "https://comicvine.gamespot.com/api/movies/?api_key=abd2bd9158ea401d671579e918e7394cd55a1a87&format=json";
        final JsonObjectRequest moviesRequest = new JsonObjectRequest(Request.Method.GET,
                auxUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("moviesAPI", response.toString());
                        try {
                            for (int i = 0; i < response.getJSONArray("results").length(); i++) {
                                String auxId=response.getJSONArray("results").getJSONObject(i).get("id").toString();
                                String auxName=response.getJSONArray("results").getJSONObject(i).get("name").toString();
                                String auxPic=response.getJSONArray("results").getJSONObject(i).getJSONObject("image").getString("small_url");
                                String auxDeck=response.getJSONArray("results").getJSONObject(i).get("deck").toString();
                                moviesIdsList.add(auxId);
                                moviesNamesList.add(auxName);
                                moviesPicsList.add(auxPic);
                                moviesDeckList.add(auxDeck);

                                System.out.println("Movie"+ moviesIdsList.get(moviesIdsList.size()-1)+" "+moviesNamesList.get(moviesNamesList.size()-1)+" "+moviesDeckList.get(moviesDeckList.size()-1));
                                System.out.println("Linkkkk"+ moviesPicsList.get(moviesPicsList.size()-1));
                            }

                            if (!moviesIdsList.isEmpty()) {
                                //LIST IS FILLED
                                for (int i = 0; i < moviesIdsList.size(); i++) {
                                    dataModels.add(new MovieDataModel(convertToImage(moviesPicsList.get(i)), moviesNamesList.get(i), moviesDeckList.get(i)));
                                }
                                adapter = new MovieAdapter(dataModels, getApplicationContext());
                                listView.setAdapter(adapter);
                                loadingDialog.dismissDialog();
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                        if (response.has("error")) {
                            try {
                                Toast.makeText(MainActivity.this, response.getString("error"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TreeAPI", "Error en la invocación a la API " + error.getCause());
                Toast.makeText(MainActivity.this, "Se presentó un error, por favor intente más tarde", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(moviesRequest);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), MovieInfo.class);
                intent.putExtra("idMovie", moviesIdsList.get(i));
                intent.putExtra("behavior", 0);
                System.out.println("El id es "+moviesIdsList.get(i));
                startActivity(intent);
            }
        });

        favMoviesFAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FavouriteMovies.class);
                startActivity(intent);
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