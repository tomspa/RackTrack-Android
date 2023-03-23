package com.example.racktrack.Exercise;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExerciseRepository {

    private RequestQueue queue;

    public ExerciseRepository(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }

    private URL createExerciseUrl() {
        Random rand = new Random();
        int randomInt = rand.nextInt(1001);

        try {
            return new URL("https://wger.de/api/v2/exercise/?language=2&limit=150&offset=" + randomInt);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getExercises(ExerciseListener listener) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                this.createExerciseUrl().toString(),
                null,
                response -> {
                    try {
                        ArrayList<Exercise> exercises = new ArrayList<>();
                        JSONArray result = response.getJSONArray("results");

                        for (int i = 0; i < result.length(); i++) {
                            JSONObject excercise = result.getJSONObject(i);
                            exercises.add(
                                new Exercise(
                                    excercise.getString("name"),
                                    excercise.getString("description").replaceAll("\\<[^>]*>","").substring(0, 100)
                                )
                            );
                        }

                        listener.success(exercises);
                    }
                    catch (JSONException e) {
                        // listener error
                    }
                },null
        );

        queue.add(request);
    }
}
