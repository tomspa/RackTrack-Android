package com.example.racktrack.Exercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.racktrack.R;

import java.util.ArrayList;
import java.util.List;

public class ExerciseListActivity extends AppCompatActivity implements ExerciseListener {

    MutableLiveData<List<Exercise>> exercises = new MutableLiveData<>();
    ExerciseListAdapter exerciseListAdapter;
    ExerciseRepository exerciseRepository;
    ProgressBar progressBar;
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

        this.progressBar = findViewById(R.id.progressBar);
        this.swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        RecyclerView recyclerView = findViewById(R.id.exercise_recycler_view);
        this.exerciseListAdapter = new ExerciseListAdapter(new ExerciseListAdapter.ExerciseDiff());
        recyclerView.setAdapter(exerciseListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.exercises.observe(this, exerciseListAdapter::submitList);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            exerciseRepository.getExercises(this, exerciseRepository.createRefreshUrl(exercises.getValue().size() + 1));
        });
    }

    @Override
    public void success(ArrayList<Exercise> exercises) {
        this.progressBar.setVisibility(View.GONE);
        this.exercises.setValue(exercises);
    }
}