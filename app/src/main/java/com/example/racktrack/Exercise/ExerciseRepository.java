package com.example.racktrack.Exercise;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExerciseRepository {

    private RequestQueue queue;

    public ExerciseRepository(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }

    private URL createExerciseUrl() {
        try {
            return new URL("https://wger.de/api/v2/exercise/?limit=30&offset=0");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getExercises() {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                this.createExerciseUrl().toString(),
                null,
                response -> {
                    List<Exercise> exercises = new ArrayList<Exercise>();

                    JSONArray result = response.getJSONArray("results");
                }

        );
    }
}
