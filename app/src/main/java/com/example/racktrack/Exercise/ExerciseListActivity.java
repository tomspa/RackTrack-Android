package com.example.racktrack.Exercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.racktrack.R;

import java.util.ArrayList;
import java.util.List;

public class ExerciseListActivity extends AppCompatActivity implements ExerciseListener {

    MutableLiveData<List<Exercise>> exercises = new MutableLiveData<>();
    ExerciseListAdapter exerciseListAdapter;
    ExerciseRepository exerciseRepository;
    View progressBarContainer;
    SwipeRefreshLayout swipeRefreshLayout;

    public ExerciseListActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        this.exerciseRepository = new ExerciseRepository(this);
        exerciseRepository.getExercises(this, exerciseRepository.createExerciseUrl());

        this.progressBarContainer = findViewById(R.id.progressbarContainer);
        this.swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        RecyclerView recyclerView = findViewById(R.id.exercise_recycler_view);
        this.exerciseListAdapter = new ExerciseListAdapter(new ExerciseListAdapter.ExerciseDiff());
        recyclerView.setAdapter(exerciseListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.exercises.observe(this, exerciseListAdapter::submitList);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);

            if (isNetworkAvailable()) {
                this.progressBarContainer.setVisibility(View.VISIBLE);
                exerciseRepository.getExercises(this, exerciseRepository.createRefreshUrl(exercises.getValue().size() + 1));
            }
            else { this.failed("No Internet"); }
        });
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
    }

    @Override
    public void success(ArrayList<Exercise> exercises) {
        this.progressBarContainer.setVisibility(View.GONE);
        this.exercises.setValue(exercises);
    }

    @Override
    public void failed(String error) {
        Toast.makeText(this, error,Toast.LENGTH_LONG).show();
    }
}