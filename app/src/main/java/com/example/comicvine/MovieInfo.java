package com.example.comicvine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieInfo extends AppCompatActivity {

    private int movieId =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);


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
                            System.out.println("tamaño especifico "+response.getJSONArray("results").length());

                            System.out.println("La primera especifica "+response.getJSONArray("results").getJSONObject(0).get("name"));

                            /*for (int i = 0; i < response.length(); i++) {
                                JSONObject movieObject = response.getJSONObject(i);
                                if (movieObject.getString("farm").equals(config.getFincaActual())) {
                                    String id = movieObject.getString("id");
                                    idsArboles.add(id);
                                }
                            }
                            if (!idsArboles.isEmpty()) {
                                //SE LLENA LA LISTA
                                for (int i = 0; i < idsArboles.size(); i++) {
                                    dataModels.add(new ResumenArbolDataModel("Número de árbol: " + idsArboles.get(i), tiposArboles.get(i), etapasArboles.get(i)));
                                }
                                adapter = new ResumenArbolesAdapter(dataModels, getApplicationContext());
                                listView.setAdapter(adapter);
                            }
                            */

                        } catch (JSONException e) {
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




    }
}