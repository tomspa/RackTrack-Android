package com.example.racktrack.Exercise;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
    private Context context;
    private int offset;

    public ExerciseRepository(Context context) {
        this.queue = Volley.newRequestQueue(context);
        this.context = context;
    }

    private URL createExerciseUrl() {
        try {
            return new URL("https://wger.de/api/v2/exercise/?language=2&limit=150&offset=" + offset);
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
                            String description = excercise.getString("description").replaceAll("\\<[^>]*>","");

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
                        Toast.makeText(context,"Error parsing JSON",Toast.LENGTH_LONG).show();
                    }
                }, error -> {
                    Toast.makeText(context,"Error getting response",Toast.LENGTH_LONG).show();
                }
        );

        queue.add(request);
    }
}
