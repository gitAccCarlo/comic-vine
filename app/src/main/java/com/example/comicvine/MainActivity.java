package com.example.comicvine;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> moviesIdsList;
    private ArrayList<String> moviesNamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviesIdsList = new ArrayList<>();
        moviesNamesList = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String auxUrl = "https://comicvine.gamespot.com/api/movies/?api_key=abd2bd9158ea401d671579e918e7394cd55a1a87&format=json";
        final JsonObjectRequest moviesRequest = new JsonObjectRequest(Request.Method.GET,
                auxUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("moviesAPI", response.toString());
                        try {
                            System.out.println(response.getString("results"));
                            System.out.println("tamaño "+response.getJSONArray("results").length());

                            System.out.println("La primera es "+response.getJSONArray("results").getJSONObject(0).get("name"));

                            for (int i = 0; i < response.getJSONArray("results").length(); i++) {
                                String auxId=response.getJSONArray("results").getJSONObject(i).get("id").toString();
                                String auxName=response.getJSONArray("results").getJSONObject(i).get("name").toString();
                                moviesIdsList.add(auxId);
                                moviesNamesList.add(auxName);
                                System.out.println("Movie "+moviesIdsList.get(moviesIdsList.size()-1)+" "+moviesNamesList.get(moviesNamesList.size()-1));
                            }

                            /*
                            if (!idsArboles.isEmpty()) {
                                //SE LLENA LA LISTA
                                for (int i = 0; i < idsArboles.size(); i++) {
                                    dataModels.add(new ResumenArbolDataModel("Número de árbol: " + idsArboles.get(i), tiposArboles.get(i), etapasArboles.get(i)));
                                }
                                adapter = new ResumenArbolesAdapter(dataModels, getApplicationContext());
                                listView.setAdapter(adapter);
                            }*/


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (response.has("error")) {
                            try {
                                Toast.makeText(MainActivity.this, response.getString("error"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Cambios guardados", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TreeAPI", "Error en la invocación a la API " + error.getCause());
                Toast.makeText(MainActivity.this, "Se presentó un error, por favor intente más tarde", Toast.LENGTH_SHORT).show();
            }
        }) {    //this is the part, that adds the header to the request

        };
        queue.add(moviesRequest);

        Intent intent=new Intent(MainActivity.this,MovieInfo.class);
        startActivity(intent);










    }




}