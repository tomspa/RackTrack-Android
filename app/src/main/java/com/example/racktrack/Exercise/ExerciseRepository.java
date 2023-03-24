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

public class ExerciseRepository {

    private final RequestQueue queue;
    private int offset;

    public ExerciseRepository(Context context) {
        this.queue = Volley.newRequestQueue(context);
        this.offset = 100;
    }

    public URL createExerciseUrl() {
        try {
            return new URL("https://wger.de/api/v2/exercise/?language=2&limit=150&offset=100");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    // creates a new url where the offset is 1 less, to show the draw refresh functional
    public URL createRefreshUrl(int limit) {
        try {
            if (offset >= 1) {
                offset--;
            }
            return new URL("https://wger.de/api/v2/exercise/?language=2&limit=" + limit + "&offset=" + offset);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getExercises(ExerciseListener listener, URL url) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url.toString(),
                null,
                response -> {
                    try {
                        ArrayList<Exercise> exercises = new ArrayList<>();
                        JSONArray result = response.getJSONArray("results");

                        for (int i = 0; i < result.length(); i++) {
                            JSONObject excercise = result.getJSONObject(i);
                            String description = excercise.getString("description").replaceAll("<[^>]*>","");

                            if (description.length() > 100) {
                                description = description.substring(0, 100);
                            }
                            exercises.add(
                                new Exercise(excercise.getString("name"), description)
                            );
                        }

                        listener.success(exercises);
                    }
                    catch (JSONException e) {
                        listener.failed("JSON error");
                    }
                }, error -> listener.failed("API Error (overloaded?)")
        );

        queue.add(request);
    }
}
